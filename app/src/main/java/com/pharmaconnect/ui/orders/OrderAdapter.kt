package com.pharmaconnect.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pharmaconnect.databinding.ItemOrderBinding
import com.pharmaconnect.model.Order
import com.pharmaconnect.utils.DateUtils

class OrderAdapter : ListAdapter<Order, OrderAdapter.OrderVH>(Diff) {
    object Diff : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderVH {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderVH(binding)
    }

    override fun onBindViewHolder(holder: OrderVH, position: Int) = holder.bind(getItem(position))

    class OrderVH(private val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            binding.orderDate.text = DateUtils.formatDate(order.date)
            binding.orderStatus.text = order.status
            binding.orderTotal.text = "$${order.totalAmount}"
        }
    }
}
