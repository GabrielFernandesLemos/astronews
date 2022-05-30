package br.com.chicorialabs.astranovos.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.chicorialabs.astranovos.core.State
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.databinding.HomeFragmentBinding
import br.com.chicorialabs.astranovos.presentation.adapter.PostListAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val binding: HomeFragmentBinding by lazy {
        HomeFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initBinding()

        initSnackbar()
        initRecyclerView()
        return binding.root
    }

    private fun initSnackbar() {
        viewModel.snackbar.observe(viewLifecycleOwner) {
            it?.let { errorMessage ->
                Snackbar.make(
                    binding.root, errorMessage,
                    Snackbar.LENGTH_LONG
                ).show()
                viewModel.onSnackBarShown()
            }
        }
    }

    private fun initRecyclerView() {

        val adapter = PostListAdapter()
        binding.homeRv.adapter = adapter

        viewModel.listPost.observe(viewLifecycleOwner) {
            when(it) {
                State.Loading -> {
                    viewModel.showProgressBar() //mostra a ProgressBar
                }
                is State.Error -> {
                    viewModel.hideProgressBar() //oculta a ProgressBar
                }
                is State.Success -> {
                    viewModel.hideProgressBar() // oculta a ProgressBar
                    adapter.submitList(it.result) //submete a lista vinculada ao State
                }
            }
        }


    }

    private fun initBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

}