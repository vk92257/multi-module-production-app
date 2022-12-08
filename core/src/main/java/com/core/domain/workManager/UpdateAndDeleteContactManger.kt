package com.core.domain.workManager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.core.data.local.callLogs.getCallDetails
import com.core.data.local.contacts.entity.ContactEntity
import com.core.domain.repository.CallLogRepository
import com.core.domain.repository.ContactRepository
import com.core.util.RouteContacts
import com.core.util.Util.replaceExtra
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import org.json.JSONArray
import org.json.JSONObject

/**
 * @Author: Vivek
 * @Date: 06/01/22
 */


@HiltWorker
class UpdateAndDeleteContactManger @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val callLogRepository: CallLogRepository,
    private val contactRepository: ContactRepository,
) : CoroutineWorker(context, workerParameters) {


    override suspend fun doWork(): Result {
        return try {
            delay(5000)
            try {

                val routeObject: String = RouteContacts
                if (routeObject.isNotEmpty()) {

                    callLogRepository.deleteCallLogList()
                    callLogRepository.deleteContactHistory()
                    contactRepository.deleteAllContacts()
                    /*val routeObject = sharedPref.getString(Constants.ROUTE, "")*/
                    // update call status in db
                    val products = ArrayList<ContactEntity>()

                    val orderObject = JSONObject(routeObject)
                    val firsOrderObject = orderObject.getJSONObject("ret")
                    val orders: JSONArray = firsOrderObject.getJSONArray("orders")

                    for (i in 0 until orders.length()) {
                        val entity = ContactEntity()
                        val singleOrder = orders.getJSONObject(i)
                        entity.isOrder = true
                        entity.carrierReference = singleOrder.getString("carrier_reference")
                        entity.postcode = singleOrder.getString("postcode")
                        entity.dropNumber =  /*Integer.parseInt(*/
                            singleOrder.getInt("drop_number") /*)*/
                        if (singleOrder.getString("contact_home") != "N/A" && !singleOrder.getString(
                                "contact_home"
                            ).isNullOrBlank() && singleOrder.getString("contact_home").length < 20
                        ) {
                            val number = replaceExtra(
                                singleOrder.getString("contact_home")
                            )?.trim()?.takeLast(10)
                            if (number != null) {
                                if (number.isNotBlank()) {
                                    if (number[0] == '0' && number.drop(1).isNotEmpty()) {
                                        entity.contactHome = number.drop(1)
                                    } else {
                                        entity.contactHome = number
                                    }
                                }
                            }

                        }
                        if (singleOrder.getString("contact_work") != "N/A" && !singleOrder.getString(
                                "contact_work"
                            ).isNullOrBlank() && singleOrder.getString("contact_work").length < 20
                        ) {
                            val number = replaceExtra(
                                singleOrder.getString("contact_work")
                            )?.trim()?.takeLast(10)

                            if (number?.isNotBlank() == true) {
                                if (number[0] == '0' && number.drop(1).isNotEmpty()) {
                                    entity.contactWork = number.drop(1)
                                } else {
                                    entity.contactWork = number
                                }
                            }
                        }
                        if (singleOrder.getString("contact_mobile") != "N/A" && !singleOrder.getString(
                                "contact_mobile"
                            ).isNullOrBlank() && singleOrder.getString("contact_mobile").length < 20
                        ) {
                            val number = replaceExtra(
                                singleOrder.getString("contact_mobile")
                            )?.trim()?.takeLast(10)
                            if (number != null) {
                                if (number.isNotBlank()) {
                                    if (number[0] == '0' && number.drop(1).isNotEmpty()) {
                                        entity.name = number.drop(1)
                                    } else {
                                        entity.number = number
                                    }
                                }
                            }
                        }
                        workerParameters.inputData
                        entity.name = singleOrder.getString("recipient")
                        entity.orderNumber = singleOrder.getString("order_number")
                        products.add(entity)
                    }
                  contactRepository.insertContacts(products)
                        contactRepository.getAllContact().collect { value: List<ContactEntity> ->
                          getCallDetails(
                              context, value, callLogRepository
                          )
                      }
                    Result.success()
                } else {
                    Result.failure()
                }

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Result.failure()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return Result.failure()
        }
    }
}