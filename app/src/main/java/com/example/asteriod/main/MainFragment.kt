package com.example.asteriod.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.asteriod.R
import com.example.asteriod.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    val adapter = AsteroidAdapter(AsteroidAdapter.AsteroidClickListener { asteroid ->
        viewModel.setAstroidToDetailsScreen(asteroid)
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        viewModel =
            ViewModelProvider(this, MainViewModel.Factory(app = requireActivity().application)).get(
                MainViewModel::class.java
            )
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        binding.asteroidRecycler.adapter = adapter
        // binding.asteroidRecycler.setNestedScrollingEnabled(false);
        binding.statusLoadingWheel.visibility = View.VISIBLE
        viewModel.allAstroid.observe(viewLifecycleOwner, Observer { astroidsList ->
            binding.statusLoadingWheel.visibility = View.GONE
            adapter.submitList(astroidsList)
        })
        viewModel.haveAstroidToCheck.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.goToDetailsScreenDone()
            }

        })
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_week_asteroids -> {
                viewModel.last7WeekAstroid.observe(viewLifecycleOwner, Observer { astroidsList ->
                    adapter.submitList(astroidsList)
                })
            }
            R.id.show_today_asteroids -> {
                viewModel.todayAstroid.observe(viewLifecycleOwner, Observer { astroidsList ->
                    adapter.submitList(astroidsList)
                })
            }
            R.id.show_saved_asteroids -> {
                viewModel.allAstroid.observe(viewLifecycleOwner, Observer { astroidsList ->
                    adapter.submitList(astroidsList)
                })
            }
        }
        return true
    }
}
