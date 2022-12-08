package com.core.util

object ContactsDBConstants {
    /* DB NAME */
    const val DB = "EPOD_NATIVE_DB"
    const val PHONE_NUMBER = "phoneNumber"

    /*  Table Name  */
    const val TABLE_CONTACTS = "contact"
    const val TABLE_CALL_LOGS = "callLogs"
    const val CALL_LOG_TABLE = "callLogsEntityTest"

    //   Contact Table Contacts  entity names
    const val CONTACTS_ID = "id"
    const val CONTACTS_NAME = "name"
    const val CONTACTS_NUMBER = "number"
    const val CONTACTS_TYPE = "type"
    const val CONTACTS_DATE = "date"
    const val CONTACTS_DURATION = "duration"
    const val CONTACTS_CALLDAYTIME = "callDayTime"
    const val CONTACTS_COUNT = "count"
    const val CARRIER_REFERENCE = "carrier_reference"
    const val POSTCODE = "postcode"
    const val CONTACT_HOME = "contact_home"
    const val CONTACT_WORK = "contact_work"
    const val DROP_NUMBER = "drop_number"
    const val RECIPIENT = "recipient"
    const val ORDER_NUMBER = "order_number"

//    OR  contact_work  =  7696016726  OR  contact_home  = 7696016726

    //   Contact Table Contacts  entity names
    const val DAY_AND_YEAR = "dayandyear"
    const val TIME_PATTERN = "h:mm a"
    const val DATE_DAY_MONTH_PATTERN = "dd MMMM"
    const val CALL_DETAILS = "CALL_DETAILS"
    const val DATE_PATTERN = "dd MMMM yyyy"


    /*
 Call Disconnected Reason
 * */
    const val CALL_REASON_LOCAL = "LOCAL"
    const val CALL_REASON_LINE_BUSY = "BUSY"
    const val CALL_REASON_NORMAL = "NORMAL"
    const val CALL_REASON_UNKNOWN = "UNKNOWN"
    const val CALL_REASON_INCOMING_MISSED = "INCOMING_MISSED"


    /*
    UpdateCallStatusManger Constants
    * */
    const val CALL_DISCONNECTED_REASON_STATE = "CALL_DISCONNECTED_REASON_STATE"
    const val CALL_TIMESTAMP = "CALL_TIMESTAMP"
    const val CALL_CREATION_TIME = "CALL_CREATION_TIME"
    const val CURRENT_TIME = "current_time"


    const val Mobile = "Mobile"
    const val Home = "Home"
    const val Work = "Work"


    /*
    * Call State for handling the call
    * */
    const val STATE_NEW = "STATE_NEW"
    const val STATE_CONNECTING = "STATE_CONNECTING"
    const val STATE_RINGING = "STATE_RINGING"
    const val STATE_ACTIVE = "STATE_ACTIVE"
    const val STATE_DISCONNECTED = "CALL_DISCONNECTED"
    const val STATE_HOLDING = "STATE_HOLDING"
    const val STATE_DIALING = "STATE_DIALING"
    const val STATE_DISCONNECTING = "STATE_DISCONNECTING"
    const val REJECT_REASON_DECLINED = "REJECT_REASON_DECLINED"
    const val CALL_DISCONNECTED = "CALL_DISCONNECTED"

    /*
    * Call State for showing the user type of the call
    * */
    const val CALLING = "Calling…"
    const val DIALING = "Dialing…"
    const val CLL_ENDED = "Call Ended"
    const val CALL_ENDING = "Call Ending"
    const val CALL_MISSED = "Missed Call"
    const val ONGOING = "Ongoing Call"


}