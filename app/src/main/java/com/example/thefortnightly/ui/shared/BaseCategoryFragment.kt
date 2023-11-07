package com.example.thefortnightly.ui.shared

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding


abstract class BaseCategoryFragment<VBinding : ViewBinding, VModel : ViewModel>(
    private val bindingInflater: (layoutInflater: LayoutInflater) -> VBinding
) : Fragment() {

    private var _binding: VBinding? = null
    val binding get() = _binding!!

    protected abstract val viewModel: VModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupVM()
    }

    open fun setupViews() {}
    open fun setupVM() {}
}