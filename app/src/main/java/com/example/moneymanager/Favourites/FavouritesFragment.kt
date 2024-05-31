package com.example.moneymanager.Favourites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneymanager.databinding.FragmentFavouritesBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouritesFragment : Fragment() {
    private lateinit var binding: FragmentFavouritesBinding
    private val viewModel: FavouritesViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavouritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = adapterSetup(viewModel, requireContext())

        lifecycleScope.launch {
            setupFavouriteAdapter(adapter)
        }

        binding.backArrow.setOnClickListener {
            findNavController().navigate(FavouritesFragmentDirections.actionFavouritesFragmentToTransactionFragment())
        }

        adapter.setOnClickListener(object : FavouritesAdapter.OnClickListener {
            override fun onItemClick(itemId: Int) {
                findNavController().navigate(FavouritesFragmentDirections.actionFavouritesFragmentToDetailsFragment(itemId))
            }
        })
    }

    private fun setupFavouriteAdapter(adapter: FavouritesAdapter) {
        viewModel.favList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            if (it.isEmpty())
                binding.txtNoDataInfo.visibility = View.VISIBLE
            else
                binding.txtNoDataInfo.visibility = View.INVISIBLE
        }
    }

    private fun adapterSetup(viewModel: FavouritesViewModel, context: Context): FavouritesAdapter {
        val adapter = FavouritesAdapter(viewModel, context)
        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
        return adapter
    }
}