package com.troy.playground.piccollage

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.troy.playground.R
import com.troy.playground.databinding.FragmentPiccollageBinding
import com.troy.playground.piccollage.viewmodel.PicCollageViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Named

class PicCollageFragment : DaggerFragment() {

    @field:[Inject Named("piccollage")]
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var binding: FragmentPiccollageBinding? = null
    private var viewModel : PicCollageViewModel? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get<PicCollageViewModel>(PicCollageViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_piccollage, container, false)
        binding?.viewModel = viewModel
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.tvHelloWorld?.setText("Hi PicCollage")
    }
}