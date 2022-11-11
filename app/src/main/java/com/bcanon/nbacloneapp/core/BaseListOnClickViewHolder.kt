package com.bcanon.nbacloneapp.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListOnClickViewHolder<T>(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T, listener: (T) -> Unit)
}