package com.udacity.shoestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding
import com.udacity.shoestore.datasource.AccountDataSourceImp
import com.udacity.shoestore.viewmodel.ShoeListViewModel
import com.udacity.shoestore.viewmodel.ShoeListViewModelFactory

class ShoeDetailFragment : Fragment() {
    private lateinit var binding: FragmentShoeDetailBinding
    val viewModel: ShoeListViewModel by activityViewModels { ShoeListViewModelFactory(AccountDataSourceImp(activity?.baseContext)) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_detail, container, false)

        binding.shoeDetail = viewModel.getSelectedShoe()

        binding.saveButton.setOnClickListener {
            actionForSave()
        }

        return binding.root
    }

    private fun actionForSave() {
        binding.shoeDetail?.let {
            if (viewModel.getSelectedShoeIndex() == -1) {
                viewModel.addShoeList(it)
            } else {
                viewModel.updateShoeList(viewModel.getSelectedShoeIndex(), it)
            }
        }
        findNavController().popBackStack(R.id.shoe_list_screen,false, true)
    }

}