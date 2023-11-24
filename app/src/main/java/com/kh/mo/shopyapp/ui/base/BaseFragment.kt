package com.kh.mo.shopyapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kh.mo.shopyapp.local.LocalSourceImp
import com.kh.mo.shopyapp.remote.RemoteSourceImp
import com.kh.mo.shopyapp.repo.RepoImp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseFragment<DB : ViewDataBinding, VM : ViewModel> : Fragment() {
    lateinit var viewModel: VM
    protected abstract val layoutIdFragment: Int
    protected lateinit var binding: DB
    protected abstract fun getViewModelClass(): Class<VM>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intiViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, layoutIdFragment,
            container, false
        )
        return binding.root
    }

    private fun intiViewModel() {
        val showProductsViewModelFactory =
            BaseViewModelFactory(
                RepoImp.getRepoImpInstance(
                    RemoteSourceImp.getRemoteSourceImpInstance(),
                    LocalSourceImp.getLocalSourceImpInstance(requireContext())

                )
            )
        viewModel = ViewModelProvider(
            this,
            showProductsViewModelFactory
        )[getViewModelClass()]
    }

    fun <T> collectLatestFlowOnLifecycle(
        flow: Flow<T>,
        collect: suspend (T) -> Unit
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest(collect)
            }
        }
    }
}