package com.bcanon.nbacloneapp.players.presentation.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bcanon.nbacloneapp.databinding.FragmentPlayersBinding
import com.bcanon.nbacloneapp.players.data.paging.RemotePresentationState
import com.bcanon.nbacloneapp.players.data.paging.asRemotePresentationState
import com.bcanon.nbacloneapp.players.domain.model.Players
import com.bcanon.nbacloneapp.players.presentation.components.PlayersListPagingAdapter
import com.bcanon.nbacloneapp.players.presentation.components.PlayersLoadStateAdapter
import com.bcanon.nbacloneapp.players.presentation.viewmodel.PlayersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayersFragment : Fragment() {
    //Fields private
    private val viewModel by viewModels<PlayersViewModel>()

    private var _binding: FragmentPlayersBinding? = null
    private val binding: FragmentPlayersBinding
        get() = _binding!!

    private val recyclerAdapter: PlayersListPagingAdapter by lazy {
        PlayersListPagingAdapter(
            listener = this::onListenerItem
        )
    }

    private val loadStateAdapter: PlayersLoadStateAdapter by lazy {
        PlayersLoadStateAdapter {
            recyclerAdapter.retry()
        }
    }

    //End fields private

    //Methods private
    private fun onListenerItem(item: Players) {

    }

    private fun FragmentPlayersBinding.bindState(
        uiState: StateFlow<PlayersState>,
        pagingData: Flow<PagingData<Players>>,
        uiActions: (UiAction) -> Unit
    ) {
        rvPlayers.adapter = recyclerAdapter.withLoadStateHeaderAndFooter(
            header = loadStateAdapter,
            footer = PlayersLoadStateAdapter { recyclerAdapter.retry() }
        )
        bindSearch(
            uiState = uiState,
            onQueryChanged = uiActions
        )
        bindList(
            uiState = uiState,
            pagingData = pagingData,
            onScrollChanged = uiActions
        )
    }

    private fun FragmentPlayersBinding.bindSearch(
        uiState: StateFlow<PlayersState>,
        onQueryChanged: (UiAction.Search) -> Unit
    ) {
        txtISearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput(onQueryChanged)
                true
            } else {
                false
            }
        }
        txtISearch.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput(onQueryChanged)
                true
            } else {
                false
            }
        }

        lifecycleScope.launch {
            uiState
                .map { it.query }
                .distinctUntilChanged()
                .collect(txtISearch::setText)
        }
    }

    private fun FragmentPlayersBinding.updateRepoListFromInput(onQueryChanged: (UiAction.Search) -> Unit) {
        txtISearch.text.trim().let {
            if (it.isNotEmpty()) {
                rvPlayers.scrollToPosition(0)
                onQueryChanged(UiAction.Search(query = it.toString()))
            }
        }
    }

    private fun FragmentPlayersBinding.bindList(
        uiState: StateFlow<PlayersState>,
        pagingData: Flow<PagingData<Players>>,
        onScrollChanged: (UiAction.Scroll) -> Unit
    ) {
//        retryButton.setOnClickListener { recyclerAdapter.retry() }
        fabUpHeader.setOnClickListener {
            rvPlayers.smoothScrollToPosition(START_POSITION)
        }

        ivArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }

        swPlayers.setOnRefreshListener {
            recyclerAdapter.refresh()
        }

        rvPlayers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                fabUpHeader.isVisible = dy < START_POSITION

                if (dy != 0) onScrollChanged(UiAction.Scroll(currentQuery = uiState.value.query))
            }
        })
        val notLoading = recyclerAdapter.loadStateFlow
            .asRemotePresentationState()
            .map { it == RemotePresentationState.PRESENTED }

        val hasNotScrolledForCurrentSearch = uiState
            .map { it.hasNotScrolledForCurrentSearch }
            .distinctUntilChanged()

        val shouldScrollToTop = combine(
            notLoading,
            hasNotScrolledForCurrentSearch,
            Boolean::and
        )
            .distinctUntilChanged()

        lifecycleScope.launch {
            pagingData.collectLatest(recyclerAdapter::submitData)
        }

        lifecycleScope.launch {
            shouldScrollToTop.collect { shouldScroll ->
                if (shouldScroll) rvPlayers.scrollToPosition(0)
            }
        }

        lifecycleScope.launch {
            recyclerAdapter.loadStateFlow.collect { loadState ->
                // Show a retry header if there was an error refreshing, and items were previously
                // cached OR default to the default prepend state
                loadStateAdapter.loadState = loadState.mediator
                    ?.refresh
                    ?.takeIf { it is LoadState.Error && recyclerAdapter.itemCount > 0 }
                    ?: loadState.prepend

                /*val isListEmpty =
                    loadState.refresh is LoadState.NotLoading && recyclerAdapter.itemCount == 0*/
                // show empty list
//                emptyList.isVisible = isListEmpty
                // Only show the list if refresh succeeds, either from the the local db or the remote.
                rvPlayers.isVisible =
                    loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh.
                swPlayers.isRefreshing = loadState.mediator?.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
                /*retryButton.isVisible =
                    loadState.mediator?.refresh is LoadState.Error && recyclerAdapter.itemCount == 0*/
                // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        this@PlayersFragment.context,
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    //End methods private

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)

        binding.rvPlayers.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        binding.bindState(
            uiState = viewModel.state,
            pagingData = viewModel.pagingDataFlow,
            uiActions = viewModel.accept
        )


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val START_POSITION = 0
    }
}