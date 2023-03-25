package com.example.todo.commons.recycleradapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<ITEM, HOLDER: BaseViewHolder<ITEM>>(context: Context) :
    RecyclerView.Adapter<HOLDER>() {

    private var context: Context? = context
    private val itemCountLimit = Int.MAX_VALUE
    private var items: ArrayList<ITEM> = ArrayList()
    private var layoutInflater: LayoutInflater? = null
    private var recyclerView: RecyclerView? = null

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    @Throws(Exception::class)
    abstract fun createItemViewHolder(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup,
        i: Int
    ): HOLDER

    override fun onBindViewHolder(holder: HOLDER, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            holder.updateWithPayload(payloads)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HOLDER {
        var createItemViewHolder: HOLDER? = null
        try {
            createItemViewHolder = createItemViewHolder(layoutInflater!!, parent, viewType)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return createItemViewHolder!!
    }

    override fun onBindViewHolder(holder: HOLDER, position: Int) {
        val itemAt: ITEM? = getItemAt(position)
        if (itemAt != null) {
            holder.setItem(itemAt)
        }else{
            throw IllegalStateException("Item with $position is not found in provided data source")

        }
    }

    override fun getItemCount(): Int {
        val size = items.size
        val i = itemCountLimit
        return size.coerceAtMost(i)
    }

    fun getItemAt(i: Int): ITEM? {
        return if (isPositionInBounds(i)) {
            items[i]
        } else null
    }
    private fun isPositionInBounds(i: Int): Boolean {
        return i in 0..itemCount
    }

    fun addItemAt(i: Int, item: ITEM?) {
        if (item != null) {
            items.add(i, item)
            notifyItemInserted(i)
        }
    }

    fun addItem(item: ITEM?) {
        if (item != null) {
            items.add(item)
            notifyItemInserted(itemCount - 1)
        }
    }

    fun addItems(list: List<ITEM>?) {
        if (list != null && list.isNotEmpty()) {
            val itemCount = itemCount
            items.addAll(list)

            notifyItemRangeInserted(itemCount, list.size)
        }
    }

    fun addItemsAt(i: Int, list: List<ITEM>?) {
        if (list != null && list.isNotEmpty()) {
            items.addAll(i, list)
            notifyItemRangeInserted(i, list.size)
        }
    }

    fun setItems(list: List<ITEM>?) {
         items.clear()
        if (list != null) {
            items.addAll(list)
        }
        notifyDataSetChanged()
    }

    fun hasNoPage(): Boolean {
        return itemCount < 1
    }

    fun applyAdapterUpdate(adapterUpdate: AdapterUpdate<ITEM>?) {
        if (adapterUpdate == null) {
            setItems(null)
        } else if (adapterUpdate.page < 1) {
            setItems(adapterUpdate.items)
        } else {
            addItems(adapterUpdate.items)
        }
    }
    fun removeItemAt(i: Int) {
        if (i in 0 until itemCount) {
            items.removeAt(i)
            notifyItemRemoved(i)
            if (isItemCountLimitInEffect()) {
                notifyItemInserted(itemCount)
            }
        }
    }

    fun removeItemsAt(hashSet: HashSet<Int>) {
        val items = hashSet.toMutableList()
        items.sort()
        items.forEachIndexed { index, item ->
            val value = item - index
            if (value >= 0 && value < items.size) {
                items.removeAt(value)
                notifyItemRemoved(value)
                if (isItemCountLimitInEffect()) {
                    notifyItemInserted(itemCount)
                }
            }
        }
    }

    fun removeAll() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun getItems(): List<ITEM> {
        return items
    }
    fun isItemCountLimitInEffect(): Boolean {
        return hasItemCountLimit() && items.size > itemCountLimit
    }

    fun hasItemCountLimit(): Boolean {
        return itemCountLimit != Int.MAX_VALUE
    }

}