package com.bcanon.nbacloneapp.teams.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bcanon.nbacloneapp.databinding.FragmentTeamsBinding
import com.bcanon.nbacloneapp.teams.domain.model.Teams
import com.bcanon.nbacloneapp.teams.presentation.components.TeamsListAdapter
import com.bcanon.nbacloneapp.teams.presentation.viewmodel.TeamsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamsFragment : Fragment() {

    //Fields private
    private val viewModel by viewModels<TeamsViewModel>()

    private var _binding: FragmentTeamsBinding? = null
    private val binding: FragmentTeamsBinding get() = _binding!!

    private val recyclerAdapter: TeamsListAdapter by lazy {
        TeamsListAdapter(
            listener = this::onListenerItem
        )
    }
    //End fields private

    //Methods private
    private fun onListenerItem(item: Teams) {
        val action = TeamsFragmentDirections.actionTeamsFragmentToTeamDetailFragment(
            teamId = item.id
        )
        findNavController().navigate(action)
    }

    private fun onBack() {
        binding.ivArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observableViewModel() {
        with(viewModel) {
            getTeams()
            state.observe(viewLifecycleOwner) { state ->
                state?.let {
                    print(state.error)
                    binding.swTeams.isRefreshing = state.isLoading
                    recyclerAdapter.submitList(state.teams)
                }
            }
        }
    }

    private fun onInitRecyclerView() {
        binding.apply {
            rvTeams.adapter = recyclerAdapter
            rvTeams.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    fabUpHeader.isVisible = dy < START_POSITION
                }
            })
        }
    }

    private fun onSwipeRefresh() {
        binding.swTeams.setOnRefreshListener {
            viewModel.getTeams()
        }
    }

    private fun onUpHeader() {
        with(binding) {
            fabUpHeader.setOnClickListener {
                rvTeams.smoothScrollToPosition(START_POSITION)
            }
        }
    }

    //End methods private

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTeamsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observableViewModel()
        onInitRecyclerView()
        onSwipeRefresh()
        onBack()
        onUpHeader()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val START_POSITION = 0
    }
}