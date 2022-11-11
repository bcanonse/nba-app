package com.bcanon.nbacloneapp.teams.presentation.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bcanon.nbacloneapp.core.BaseListOnClickViewHolder
import com.bcanon.nbacloneapp.core.DiffCallback
import com.bcanon.nbacloneapp.databinding.ListItemTeamsBinding
import com.bcanon.nbacloneapp.teams.domain.model.Teams

class TeamsListAdapter(
    private val listener: (Teams) -> Unit
) : ListAdapter<Teams, BaseListOnClickViewHolder<*>>(
    DiffCallback<Teams>()
) {

    inner class TeamsViewHolder(
        private var binding: ListItemTeamsBinding
    ) : BaseListOnClickViewHolder<Teams>(binding.root) {

        override fun bind(item: Teams, listener: (Teams) -> Unit) =
            with(binding) {
                tvTeamName.text = item.name
                tvConferenceTeam.text = item.conference

                root.setOnClickListener {
                    listener(item)
                }
            }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseListOnClickViewHolder<*> {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TeamsViewHolder(
            ListItemTeamsBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseListOnClickViewHolder<*>, position: Int) {
        with(holder) {
            when (this) {
                is TeamsViewHolder -> bind(getItem(position)!!, listener)
            }
        }
    }
}