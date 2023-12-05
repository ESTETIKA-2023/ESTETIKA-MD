package com.bangkit23.estetika.ui.destination

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit23.estetika.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BatikDestinationFragment : Fragment() {

    companion object {
        fun newInstance() = BatikDestinationFragment()
    }

    private lateinit var viewModel: BatikDestinationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_batik_destination, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BatikDestinationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}