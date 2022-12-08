package com.bjs.pdanative.presentation.ui.runsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bjs.pdanative.R
import com.bjs.pdanative.databinding.FragmentOrderListBinding
import com.bjs.pdanative.presentation.ui.base.BaseFragment

/**
 * Created by deepak on {10/08/21}
 */
class OrderListFragment : BaseFragment() {
    private var _binding: FragmentOrderListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataList = ArrayList<OrderListDummyData>()
        dataList.add(OrderListDummyData(OrdersListAdapter.VIEW_TYPE_ONE, "1. Hi! I am in View 1"))
       /* dataList.add(OrderListDummyData(OrdersListAdapter.VIEW_TYPE_ONE, "2. Hi! I am in View 2"))
        dataList.add(OrderListDummyData(OrdersListAdapter.VIEW_TYPE_ONE, "3. Hi! I am in View 3"))
        dataList.add(OrderListDummyData(OrdersListAdapter.VIEW_TYPE_ONE, "4. Hi! I am in View 4"))
        dataList.add(OrderListDummyData(OrdersListAdapter.VIEW_TYPE_ONE, "5. Hi! I am in View 5"))
        dataList.add(OrderListDummyData(OrdersListAdapter.VIEW_TYPE_TWO, "6. Hi! I am in View 6"))
        dataList.add(OrderListDummyData(OrdersListAdapter.VIEW_TYPE_ONE, "7. Hi! I am in View 7"))
        dataList.add(OrderListDummyData(OrdersListAdapter.VIEW_TYPE_ONE, "8. Hi! I am in View 8"))
        dataList.add(OrderListDummyData(OrdersListAdapter.VIEW_TYPE_ONE, "9. Hi! I am in View 9"))
        dataList.add(OrderListDummyData(OrdersListAdapter.VIEW_TYPE_ONE, "4. Hi! I am in View 4"))
        dataList.add(OrderListDummyData(OrdersListAdapter.VIEW_TYPE_ONE, "5. Hi! I am in View 5"))
        dataList.add(OrderListDummyData(OrdersListAdapter.VIEW_TYPE_TWO, "6. Hi! I am in View 6"))
        dataList.add(OrderListDummyData(OrdersListAdapter.VIEW_TYPE_ONE, "7. Hi! I am in View 7"))
        dataList.add(OrderListDummyData(OrdersListAdapter.VIEW_TYPE_ONE, "8. Hi! I am in View 8"))
        dataList.add(OrderListDummyData(OrdersListAdapter.VIEW_TYPE_ONE, "9. Hi! I am in View 9"))
        dataList.add(OrderListDummyData(OrdersListAdapter.VIEW_TYPE_ONE, "4. Hi! I am in View 4"))
        dataList.add(OrderListDummyData(OrdersListAdapter.VIEW_TYPE_ONE, "5. Hi! I am in View 5"))
        dataList.add(OrderListDummyData(OrdersListAdapter.VIEW_TYPE_TWO, "6. Hi! I am in View 6"))
        dataList.add(OrderListDummyData(OrdersListAdapter.VIEW_TYPE_ONE, "7. Hi! I am in View 7"))
        dataList.add(OrderListDummyData(OrdersListAdapter.VIEW_TYPE_ONE, "8. Hi! I am in View 8"))
        dataList.add(OrderListDummyData(OrdersListAdapter.VIEW_TYPE_ONE, "9. Hi! I am in View 9"))
   */     val adapter = OrdersListAdapter(requireContext(), dataList) {
            findNavController().navigate(R.id.action_orderListFragment_to_orderDetailFragment)
        }
        binding.rvOrders.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOrders.adapter = adapter
    }


}