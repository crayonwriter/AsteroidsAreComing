package com.crayonwriter.asteroidsarecoming.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.crayonwriter.asteroidsarecoming.R
import com.crayonwriter.asteroidsarecoming.database.AsteroidDatabase
import com.crayonwriter.asteroidsarecoming.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)

        setHasOptionsMenu(true)

        //Reference to the application the fragment is attached to, to pass into the viewmodelfactory provider
        val application = requireNotNull(this.activity).application

        //Reference to the data source via a reference to the dao
        val dataSource = AsteroidDatabase.getDatabase(application).asteroidDatabaseDao

        //create an instance of the viewmodelfactory
        val viewModelFactory = MainViewModelFactory(dataSource, application)

        //Now that we have a factory, we can ask the viewmodelfactory provider for a mainViewModel
        val mainViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(MainViewModel::class.java)

        //Setup databinding, including setting the variable in the layout
        // which we access through the binding object to the viewModel
        binding.mainViewModel = mainViewModel

        //Connect the adapter to the recyclerview. Make a new adapter and assign the adapter
        //on the recyclerview. Tells the recyclerview to use the adapter to display things on the screen.
        val adapter = AsteroidAdapter(AsteroidListener {
            //Pass the call to click via the viewmodel
                asteroid -> mainViewModel.onAsteroidClicked(asteroid)
        })

        binding.asteroidRecycler.adapter = adapter

        //Observe livedata for the navigation to the detail screen
        mainViewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {asteroid ->
            asteroid?.let {
                this.findNavController().navigate(
                    MainFragmentDirections.actionShowDetail(asteroid)
                )
                mainViewModel.onNavigateToDetailCompleted()
            }
        })


        mainViewModel.asteroids.observe(viewLifecycleOwner, Observer {
            it?.let {
                //This uses diffUtil
                adapter.submitList(it)
            }
        })

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}