package com.example.moneymanager.TransactionAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanager.DB.TransactionEntity
import com.example.moneymanager.R

class TransactionAdapter: ListAdapter<TransactionEntity, TransactionAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_transaction, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val transactionType: TextView = itemView.findViewById(R.id.transaction_type)
        private val transactionCategory: TextView = itemView.findViewById(R.id.transaction_category)
        private val transactionMethod: TextView = itemView.findViewById(R.id.transaction_method)
        private val transactionAmount: TextView = itemView.findViewById(R.id.transaction_amount)


        fun bind(transactionEntity: TransactionEntity) {
            transactionType.text = itemView.resources.getString(R.string.type_transaction, transactionEntity.transactionType)
            transactionCategory.text = transactionEntity.transactionCategory
            transactionMethod.text = transactionEntity.wallet
            transactionAmount.text = itemView.resources.getString(R.string.amount_transaction, transactionEntity.amount.toString())

        }
    }

    private class DiffCallBack: DiffUtil.ItemCallback<TransactionEntity>() {

        override fun areItemsTheSame(oldItem: TransactionEntity, newItem: TransactionEntity): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: TransactionEntity, newItem: TransactionEntity): Boolean = oldItem == newItem
    }

}

