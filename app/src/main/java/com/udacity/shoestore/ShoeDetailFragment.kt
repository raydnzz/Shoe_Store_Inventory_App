package com.udacity.shoestore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.models.ShoeDetail

class ShoeDetailFragment : Fragment() {
    private lateinit var binding: FragmentShoeDetailBinding

    companion object {
        val REQUEST_UPDATE_DETAIL_KEY = "REQUEST_UPDATE_DETAIL_KEY"
        val REQUEST_ADD_DETAIL_KEY = "REQUEST_ADD_DETAIL_KEY"
        val DETAIL_RESULTS_KEY = "DETAIL_RESULTS_KEY"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_detail, container, false)

        val args = ShoeDetailFragmentArgs.fromBundle(requireArguments())
        if (args.shoeDetail != null ) {
            binding.shoeDetail = args.shoeDetail as ShoeDetail
        } else {
            binding.shoeDetail = ShoeDetail(-1, Shoe())
        }

        binding.saveButton.setOnClickListener {
            actionForSave()
        }

        return binding.root
    }

    private fun actionForSave() {
        if (binding.shoeDetail?.index == -1) {
            setFragmentResult(REQUEST_ADD_DETAIL_KEY, bundleOf(DETAIL_RESULTS_KEY to binding.shoeDetail))
        } else {
            setFragmentResult(REQUEST_UPDATE_DETAIL_KEY, bundleOf(DETAIL_RESULTS_KEY to binding.shoeDetail))
        }
        findNavController().popBackStack(R.id.shoe_list_screen,false, true)
    }

}