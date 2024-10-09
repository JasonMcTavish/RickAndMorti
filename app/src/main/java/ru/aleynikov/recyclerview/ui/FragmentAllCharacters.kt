package ru.aleynikov.recyclerview.ui

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.aleynikov.recyclerview.R
import ru.aleynikov.recyclerview.api.CharacterDto
import ru.aleynikov.recyclerview.databinding.FragmentAllCharactersBinding

@AndroidEntryPoint
class FragmentAllCharacters : Fragment() {
    private var _binding: FragmentAllCharactersBinding? = null
    private val binding get() = _binding!!

    private lateinit var menuHost: MenuHost

    private val viewModel: AllCharactersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllCharactersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFilterMenu()

        setRecyclerAdapter()

        getCharactersList()

        stateLoadingListener()

        binding.swipeRefresh.setOnRefreshListener { viewModel.pagedAdapter.refresh() }
        binding.rvBtnRefresh.setOnClickListener { viewModel.pagedAdapter.retry() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setRecyclerAdapter() {
        viewModel.pagedAdapter = RnMAdapter { onItemClick(it) }
        binding.charactersList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val tryAgainAction = { viewModel.pagedAdapter.retry() }
        binding.charactersList.adapter =
            viewModel.pagedAdapter.withLoadStateFooter(LoadStateAdapterRV(tryAgainAction))
        (binding.charactersList.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations =
            false
    }


    private fun onItemClick(character: CharacterDto) {
        val action =
            FragmentAllCharactersDirections.actionFragmentAllCharactersToFragmentSingleCharacter(
                character
            )
        findNavController().navigate(action)
    }

    private fun getCharactersList() {
        viewModel.pagedCharacters.onEach {
            viewModel.pagedAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun stateLoadingListener() {
        viewModel.pagedAdapter.loadStateFlow.onEach { state ->
            val currentState = state.refresh
            binding.swipeRefresh.isRefreshing = currentState == LoadState.Loading
            when (currentState) {
                is LoadState.Error -> {
                    binding.charactersList.visibility = View.GONE
                    binding.loadState.visibility = View.VISIBLE
                }

                else -> {
                    binding.charactersList.visibility = View.VISIBLE
                    binding.loadState.visibility = View.GONE
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setFilterMenu() {
        menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_filter, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.action_filter -> {
                        val fm = childFragmentManager
                        val filterDialog = DialogFilter()
                        filterDialog.show(fm, DialogFilter.SHOW_FILTER_DIALOG)
                    }

                    R.id.action_clear_filter -> {
                        viewModel.pagedAdapter.refresh()
                        viewModel.setFilterParams(
                            status = "", gender = ""
                        )
                    }
                }
                return true
            }
        }, viewLifecycleOwner)
    }
}