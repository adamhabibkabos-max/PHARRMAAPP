package com.pharmaconnect.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pharmaconnect.PharmaConnectApp
import com.pharmaconnect.databinding.FragmentDashboardBinding
import com.pharmaconnect.viewmodel.DashboardViewModel
import com.pharmaconnect.viewmodel.ViewModelFactory

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by activityViewModels {
        ViewModelFactory((requireActivity().application as PharmaConnectApp).repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.totalMedicines.observe(viewLifecycleOwner) { binding.totalMedicines.text = it.toString() }
        viewModel.lowStockCount.observe(viewLifecycleOwner) { binding.lowStock.text = it.toString() }
        viewModel.expiringSoonCount.observe(viewLifecycleOwner) { binding.expiringSoon.text = it.toString() }
        viewModel.totalOrders.observe(viewLifecycleOwner) { binding.totalOrders.text = it.toString() }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
