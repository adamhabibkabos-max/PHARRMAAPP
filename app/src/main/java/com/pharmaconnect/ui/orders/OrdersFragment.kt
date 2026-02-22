package com.pharmaconnect.ui.orders

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pharmaconnect.PharmaConnectApp
import com.pharmaconnect.databinding.FragmentOrdersBinding
import com.pharmaconnect.viewmodel.OrdersViewModel
import com.pharmaconnect.viewmodel.ViewModelFactory
import java.util.Calendar

class OrdersFragment : Fragment() {
    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrdersViewModel by activityViewModels {
        ViewModelFactory((requireActivity().application as PharmaConnectApp).repository)
    }

    private val adapter = OrderAdapter()
    private var startDate = 0L
    private var endDate = System.currentTimeMillis()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerOrders.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerOrders.adapter = adapter

        viewModel.orders.observe(viewLifecycleOwner) { adapter.submitList(it) }

        binding.buttonAddOrder.setOnClickListener { viewModel.addDemoOrder("Pending", 120.0) }
        binding.buttonFilterRange.setOnClickListener { pickStartDate() }
    }

    private fun pickStartDate() {
        val cal = Calendar.getInstance()
        DatePickerDialog(requireContext(), { _, y, m, d ->
            cal.set(y, m, d, 0, 0, 0)
            startDate = cal.timeInMillis
            pickEndDate()
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun pickEndDate() {
        val cal = Calendar.getInstance()
        DatePickerDialog(requireContext(), { _, y, m, d ->
            cal.set(y, m, d, 23, 59, 59)
            endDate = cal.timeInMillis
            viewModel.filterByRange(startDate, endDate).observe(viewLifecycleOwner) { adapter.submitList(it) }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
