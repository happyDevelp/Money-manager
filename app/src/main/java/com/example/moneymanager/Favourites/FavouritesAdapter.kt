package com.example.moneymanager.Favourites

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanager.DB.TransactionEntity
import com.example.moneymanager.R
import com.example.moneymanager.databinding.ItemFavouriteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class FavouritesAdapter(private val viewModel: FavouritesViewModel, private val context: Context) :
    ListAdapter<TransactionEntity, FavouritesAdapter.FavouritesViewHolder>(DiffCallBack()) {

    private var listener: OnClickListener? = null

    // Function for setting the interface variable
    fun setOnClickListener(listener: OnClickListener) {
        this.listener = listener
    }

    // Declaring the interface in adapter or we can declare it in separate file
    interface OnClickListener {
        fun onItemClick(itemId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder =
        FavouritesViewHolder(
            ItemFavouriteBinding.inflate(LayoutInflater.from(parent.context), parent, false), viewModel, context
        )

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.itemView.setOnClickListener { listener?.onItemClick(currentItem.id) }

        holder.bind(currentItem)
    }

    class FavouritesViewHolder(private val binding: ItemFavouriteBinding, private val viewModel: FavouritesViewModel,
        private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transactionEntity: TransactionEntity) {
            binding.transactionAmount.text = itemView.resources.getString(
                R.string.amount_transaction,
                transactionEntity.amount.toString()
            )
            binding.transactionMethod.text = transactionEntity.wallet
            if (transactionEntity.transactionType == itemView.resources.getString(R.string.income_adding))
                binding.transactionType.text = itemView.resources.getString(R.string.type_transaction_income, transactionEntity.transactionType)
            else
                binding.transactionType.text = itemView.resources.getString(R.string.type_transaction_spend, transactionEntity.transactionType)

            binding.transactionCategory.text = transactionEntity.transactionCategory
            binding.txtDateOfTransaction.text =
                transactionEntity.dateOfTransaction.substringBefore(" ")

            val dbDate = transactionEntity.dateOfTransaction
            val format = SimpleDateFormat(itemView.resources.getString(R.string.date_pattern))
            val date: Date? = format.parse(dbDate)


            val calendar = Calendar.getInstance()
            calendar.time = date
            val weekDay = WeekDay.fromCalendarDay(calendar.get(Calendar.DAY_OF_WEEK))
            binding.txtWeekDay.text = weekDay?.displayDate ?: "Invalid date"


            binding.imvFavourite.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.changeFavState(false, transactionEntity.id)
                }
                Toast.makeText(context, "Deleted from favourite", Toast.LENGTH_SHORT).show()
            }
        }
    }


    class DiffCallBack : DiffUtil.ItemCallback<TransactionEntity>() {
        override fun areItemsTheSame(oldItem: TransactionEntity, newItem: TransactionEntity): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: TransactionEntity, newItem: TransactionEntity): Boolean =
            oldItem.id == newItem.id
    }
}

enum class WeekDay(val displayDate: String) {
    SUNDAY("Sun"),
    MONDAY("Mon"),
    TUESDAY("Tue"),
    WEDNESDAY("Wed"),
    THURSDAY("Thu"),
    FRIDAY("Fri"),
    SATURDAY("Sat");

    companion object {
        fun fromCalendarDay(day: Int): WeekDay? {
            return when (day) {
                Calendar.SUNDAY -> SUNDAY
                Calendar.MONDAY -> MONDAY
                Calendar.TUESDAY -> TUESDAY
                Calendar.WEDNESDAY -> WEDNESDAY
                Calendar.THURSDAY -> THURSDAY
                Calendar.FRIDAY -> FRIDAY
                Calendar.SATURDAY -> SATURDAY
                else -> null
            }
        }
    }


}


