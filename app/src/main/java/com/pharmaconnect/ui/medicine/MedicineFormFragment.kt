package com.pharmaconnect.ui.medicine

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.pharmaconnect.PharmaConnectApp
import com.pharmaconnect.databinding.FragmentMedicineFormBinding
import com.pharmaconnect.model.Medicine
import com.pharmaconnect.ui.scanner.BarcodeScannerActivity
import com.pharmaconnect.viewmodel.MedicineFormViewModel
import com.pharmaconnect.viewmodel.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Locale

class MedicineFormFragment : Fragment() {
    private var _binding: FragmentMedicineFormBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MedicineFormViewModel by activityViewModels {
        ViewModelFactory((requireActivity().application as PharmaConnectApp).repository)
    }

    private var currentId: Long = 0L

    private val scannerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val code = result.data?.getStringExtra("barcode")
            binding.inputName.setText(code)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMedicineFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        currentId = arguments?.getLong("medicineId", 0L) ?: 0L
        if (currentId != 0L) viewModel.loadMedicine(currentId)

        viewModel.medicine.observe(viewLifecycleOwner) { medicine ->
            medicine ?: return@observe
            binding.inputName.setText(medicine.name)
            binding.inputBrand.setText(medicine.brand)
            binding.inputStrength.setText(medicine.strength)
            binding.inputQuantity.setText(medicine.quantity.toString())
            binding.inputPrice.setText(medicine.price.toString())
            binding.inputExpiry.setText(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(medicine.expiryDate))
            binding.inputCategory.setText(medicine.category.orEmpty())
        }

        binding.buttonScanBarcode.setOnClickListener {
            scannerLauncher.launch(Intent(requireContext(), BarcodeScannerActivity::class.java))
        }

        binding.buttonSave.setOnClickListener {
            val medicine = parseInput() ?: return@setOnClickListener
            viewModel.saveMedicine(medicine)
        }

        viewModel.saveSuccess.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Saved successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }

    private fun parseInput(): Medicine? {
        val name = binding.inputName.text.toString().trim()
        val brand = binding.inputBrand.text.toString().trim()
        val strength = binding.inputStrength.text.toString().trim()
        val quantity = binding.inputQuantity.text.toString().toIntOrNull()
        val price = binding.inputPrice.text.toString().toDoubleOrNull()
        val expiryRaw = binding.inputExpiry.text.toString().trim()
        val category = binding.inputCategory.text.toString().trim().ifBlank { null }

        if (name.isBlank() || brand.isBlank() || strength.isBlank() || quantity == null || price == null || expiryRaw.isBlank()) {
            Toast.makeText(requireContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show()
            return null
        }

        val expiryDate = runCatching {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(expiryRaw)?.time
        }.getOrNull() ?: run {
            Toast.makeText(requireContext(), "Expiry date must be yyyy-MM-dd", Toast.LENGTH_SHORT).show()
            return null
        }

        return Medicine(currentId, name, brand, strength, quantity, price, expiryDate, category)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
