package com.pharmaconnect.ui.inventory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pharmaconnect.databinding.ItemMedicineBinding
import com.pharmaconnect.model.Medicine
import com.pharmaconnect.utils.DateUtils

class MedicineAdapter(private val onClick: (Medicine) -> Unit) : ListAdapter<Medicine, MedicineAdapter.MedicineVH>(Diff) {

    object Diff : DiffUtil.ItemCallback<Medicine>() {
        override fun areItemsTheSame(oldItem: Medicine, newItem: Medicine): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Medicine, newItem: Medicine): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineVH {
        val binding = ItemMedicineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicineVH(binding)
    }

    override fun onBindViewHolder(holder: MedicineVH, position: Int) = holder.bind(getItem(position))

    inner class MedicineVH(private val binding: ItemMedicineBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Medicine) {
            binding.name.text = item.name
            binding.strength.text = item.strength
            binding.quantity.text = "Qty: ${item.quantity}"
            binding.expiry.text = "Exp: ${DateUtils.formatDate(item.expiryDate)}"
            binding.price.text = "$${item.price}"
            binding.root.setOnClickListener { onClick(item) }
        }
    }
}
