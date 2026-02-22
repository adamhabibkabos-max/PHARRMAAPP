package com.pharmaconnect.ui.reports

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pharmaconnect.PharmaConnectApp
import com.pharmaconnect.databinding.FragmentReportsBinding
import com.pharmaconnect.viewmodel.ReportsViewModel
import com.pharmaconnect.viewmodel.ViewModelFactory
import java.util.Calendar

class ReportsFragment : Fragment() {
    private var _binding: FragmentReportsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportsViewModel by activityViewModels {
        ViewModelFactory((requireActivity().application as PharmaConnectApp).repository)
    }

    private var start: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentReportsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonDateRange.setOnClickListener { pickStart() }

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            binding.completedCount.text = state.completedCount.toString()
            binding.failedCount.text = state.failedCount.toString()
            binding.totalRevenue.text = "$${state.revenue}"
        }
    }

    private fun pickStart() {
        val cal = Calendar.getInstance()
        DatePickerDialog(requireContext(), { _, y, m, d ->
            cal.set(y, m, d, 0, 0, 0)
            start = cal.timeInMillis
            pickEnd()
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun pickEnd() {
        val cal = Calendar.getInstance()
        DatePickerDialog(requireContext(), { _, y, m, d ->
            cal.set(y, m, d, 23, 59, 59)
            viewModel.updateRange(start, cal.timeInMillis)
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
