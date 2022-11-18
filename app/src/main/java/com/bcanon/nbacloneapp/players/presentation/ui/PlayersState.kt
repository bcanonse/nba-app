package com.bcanon.nbacloneapp.players.presentation.ui

import com.bcanon.nbacloneapp.players.presentation.viewmodel.PlayersViewModel.Companion.DEFAULT_QUERY

data class PlayersState(
    val query: String = DEFAULT_QUERY,
    val lastQueryScrolled: String = DEFAULT_QUERY,
    val hasNotScrolledForCurrentSearch: Boolean = false
)

sealed class UiAction {
    data class Search(val query: String) : UiAction()
    data class Scroll(val currentQuery: String) : UiAction()
}
