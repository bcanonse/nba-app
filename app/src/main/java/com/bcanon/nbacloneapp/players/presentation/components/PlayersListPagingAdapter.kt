package com.bcanon.nbacloneapp.players.presentation.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bcanon.nbacloneapp.databinding.ListItemPlayersBinding
import com.bcanon.nbacloneapp.players.domain.model.Players

class PlayersListPagingAdapter(
    private val listener: (Players) -> Unit
) : PagingDataAdapter<Players, PlayersListPagingAdapter.PlayersViewHolder>(UIMODEL_COMPARATOR) {

    inner class PlayersViewHolder(
        private var binding: ListItemPlayersBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Players, listener: (Players) -> Unit) {
            with(binding) {
                tvPlayerName.text = item.fullName
                tvPlayerPosition.text = item.position

                root.setOnClickListener {
                    listener(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlayersViewHolder {
        return PlayersViewHolder(
            ListItemPlayersBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PlayersViewHolder, position: Int) {
        getItem(position)?.let {
            with(holder) {
                bind(it, listener)
            }
        }
    }

    companion object {
        private val UIMODEL_COMPARATOR by lazy {
            object : DiffUtil.ItemCallback<Players>() {
                override fun areItemsTheSame(oldItem: Players, newItem: Players): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Players, newItem: Players): Boolean =
                    oldItem == newItem
            }
        }
    }
}
