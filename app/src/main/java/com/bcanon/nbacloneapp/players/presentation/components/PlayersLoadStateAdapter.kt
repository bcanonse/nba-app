package com.bcanon.nbacloneapp.players.presentation.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bcanon.nbacloneapp.databinding.LoadStateItemBinding

class PlayersLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<PlayersLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(private val binding: LoadStateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            with(binding) {
                if (loadState is LoadState.Error) {
                    tvLoadStateErrorMsg.text = loadState.error.localizedMessage
                }
                btnLoadStateRetry.isVisible = loadState is LoadState.Error
                tvLoadStateErrorMsg.isVisible = loadState is LoadState.Error
                pbLoadStatePbar.isVisible = loadState is LoadState.Loading


                btnLoadStateRetry.setOnClickListener { retry() }
            }
        }
    }


    override fun onBindViewHolder(
        holder: PlayersLoadStateAdapter.LoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PlayersLoadStateAdapter.LoadStateViewHolder {
        return LoadStateViewHolder(
            LoadStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}