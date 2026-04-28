package com.example.spendwise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter(
    private var items: List<TransactionItem>,
    private val currencySymbol: String,
    private val onDelete: (TransactionItem) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvIcon     = view.findViewById<TextView>(R.id.tv_icon)
        val tvCategory = view.findViewById<TextView>(R.id.tv_category)
        val tvDesc     = view.findViewById<TextView>(R.id.tv_description)
        val tvAmount   = view.findViewById<TextView>(R.id.tv_amount)
        val tvDate     = view.findViewById<TextView>(R.id.tv_date)
        val ivReceipt  = view.findViewById<ImageView?>(R.id.iv_receipt_photo)
        val tvHasPhoto = view.findViewById<TextView?>(R.id.tv_has_photo)
        val btnDelete  = view.findViewById<View>(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

        holder.tvIcon.text     = item.icon
        holder.tvCategory.text = item.category
        holder.tvDesc.text     = item.description.ifBlank { item.category }
        holder.tvDate.text     = sdf.format(Date(item.date))
        holder.tvAmount.text   = "${if (item.isExpense) "-" else "+"}$currencySymbol%.2f".format(item.amount)
        holder.tvAmount.setTextColor(
            if (item.isExpense) 0xFFE57373.toInt() else 0xFF81C784.toInt()
        )

        // Receipt photo display - Show it if it exists by default
        holder.ivReceipt?.let { iv ->
            if (item.photoUrl.isNotEmpty()) {
                holder.tvHasPhoto?.visibility = View.VISIBLE
                iv.visibility = View.VISIBLE
                Glide.with(holder.itemView.context)
                    .load(item.photoUrl)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(iv)
                
                // Allow toggling visibility by clicking the icon
                holder.tvHasPhoto?.setOnClickListener {
                    iv.visibility = if (iv.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                }
                // Also allow clicking the image to collapse it
                iv.setOnClickListener {
                    iv.visibility = View.GONE
                }
            } else {
                holder.tvHasPhoto?.visibility = View.GONE
                iv.visibility = View.GONE
            }
        }

        holder.btnDelete.setOnClickListener {
            onDelete(item)
        }

        holder.itemView.setOnLongClickListener {
            onDelete(item)
            true
        }
    }

    fun updateItems(newItems: List<TransactionItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
