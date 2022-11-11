package com.bcanon.nbacloneapp.teams_detail.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bcanon.nbacloneapp.R
import com.bcanon.nbacloneapp.core.utils.url
import com.bcanon.nbacloneapp.databinding.FragmentTeamDetailBinding
import com.bcanon.nbacloneapp.teams_detail.presentation.viewmodel.TeamViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamDetailFragment : Fragment() {

    //Fields private
    private val args: TeamDetailFragmentArgs by navArgs()

    private var _binding: FragmentTeamDetailBinding? = null
    private val binding: FragmentTeamDetailBinding
        get() = _binding!!

    private val viewModel by viewModels<TeamViewModel>()

    //End fields private

    //Methods private
    private fun observableViewModel() {
        with(viewModel) {
            getTeam(args.teamId)
            state.observe(viewLifecycleOwner) { state ->
                state?.let {
                    binding.team = state.data
                    resources.getStringArray(R.array.logo_url_teams).getOrNull(args.teamId - 1)
                        ?.let {
                            binding.ivLogoTeam.url(it)
                        }
                    binding.swTeams.isRefreshing = state.isLoading
                }
            }
        }
    }

    private fun onSwipeRefresh() {
        binding.swTeams.setOnRefreshListener {
            viewModel.getTeam(args.teamId)
        }
    }

    private fun onBack() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTeamDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observableViewModel()
        onSwipeRefresh()
        onBack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}