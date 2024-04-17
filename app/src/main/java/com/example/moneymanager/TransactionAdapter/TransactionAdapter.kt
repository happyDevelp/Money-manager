package com.example.moneymanager.TransactionAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanager.DB.TransactionEntity
import com.example.moneymanager.R
import com.example.moneymanager.databinding.ItemTransactionBinding

class TransactionAdapter: ListAdapter<TransactionEntity, TransactionAdapter.TransactionViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder =
        TransactionViewHolder(ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val currentItem = getItem(position)

        holder.bind(currentItem)
    }

    class TransactionViewHolder(private val binding: ItemTransactionBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(transactionEntity: TransactionEntity) {
            binding.transactionAmount.text = itemView.resources.getString(R.string.amount_transaction, transactionEntity.amount.toString())
            binding.transactionMethod.text = transactionEntity.wallet
            binding.transactionType.text = itemView.resources.getString(R.string.type_transaction, transactionEntity.transactionType)
            binding.transactionCategory.text = transactionEntity.transactionCategory
        }
    }

    private class DiffCallBack: DiffUtil.ItemCallback<TransactionEntity>() {

        override fun areItemsTheSame(oldItem: TransactionEntity, newItem: TransactionEntity): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: TransactionEntity, newItem: TransactionEntity): Boolean = oldItem.id == newItem.id
    }

}

