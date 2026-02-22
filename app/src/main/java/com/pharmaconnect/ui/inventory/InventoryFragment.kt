package com.pharmaconnect.ui.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pharmaconnect.PharmaConnectApp
import com.pharmaconnect.R
import com.pharmaconnect.databinding.FragmentInventoryBinding
import com.pharmaconnect.viewmodel.InventoryViewModel
import com.pharmaconnect.viewmodel.ViewModelFactory

class InventoryFragment : Fragment(), MenuProvider {
    private var _binding: FragmentInventoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InventoryViewModel by activityViewModels {
        ViewModelFactory((requireActivity().application as PharmaConnectApp).repository)
    }

    private val adapter = MedicineAdapter {
        findNavController().navigate(R.id.action_inventoryFragment_to_medicineFormFragment, Bundle().apply { putLong("medicineId", it.id) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentInventoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
        binding.recyclerMedicines.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerMedicines.adapter = adapter

        binding.searchInput.editText?.addTextChangedListener {
            viewModel.search(it?.toString().orEmpty())
        }

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_inventoryFragment_to_medicineFormFragment, Bundle().apply { putLong("medicineId", 0L) })
        }

        viewModel.medicines.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_inventory_filter, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.filter_all -> viewModel.medicines.observe(viewLifecycleOwner) { adapter.submitList(it) }
            R.id.filter_low_stock -> viewModel.lowStockOnly().observe(viewLifecycleOwner) { adapter.submitList(it) }
            R.id.filter_expired -> viewModel.expiredOnly().observe(viewLifecycleOwner) { adapter.submitList(it) }
            R.id.filter_category_antibiotic -> viewModel.filteredByCategory("Antibiotic").observe(viewLifecycleOwner) { adapter.submitList(it) }
        }
        return true
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
