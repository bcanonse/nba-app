package com.bcanon.nbacloneapp.core

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseListOnClickViewHolder<T>(
    itemView: ViewBinding
) : RecyclerView.ViewHolder(itemView.root) {
    abstract fun bind(item: T, listener: (T) -> Unit)
}