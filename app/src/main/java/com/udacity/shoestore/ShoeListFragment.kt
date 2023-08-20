package com.udacity.shoestore

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.udacity.shoestore.ShoeDetailFragment.Companion.DETAIL_RESULTS_KEY
import com.udacity.shoestore.ShoeDetailFragment.Companion.REQUEST_ADD_DETAIL_KEY
import com.udacity.shoestore.ShoeDetailFragment.Companion.REQUEST_UPDATE_DETAIL_KEY
import com.udacity.shoestore.databinding.FragmentShoeListBinding
import com.udacity.shoestore.datasource.AccountDataSourceImp
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.models.ShoeDetail
import com.udacity.shoestore.viewmodel.ShoeListViewModel
import com.udacity.shoestore.viewmodel.ShoeListViewModelFactory


class ShoeListFragment : Fragment(), MenuProvider {
    private lateinit var binding: FragmentShoeListBinding

    private lateinit var viewModel: ShoeListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_list, container, false)

        // Add menu items without overriding methods in the Activity
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val viewModelFactory = ShoeListViewModelFactory(AccountDataSourceImp(activity?.baseContext))
        viewModel = ViewModelProvider(this, viewModelFactory)[ShoeListViewModel::class.java]

        observeViewModel()
        initEvents()

        setShoeList()

        return binding.root
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.shoe_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.logout -> {
                viewModel.logout()
                actionForLogout()
                true
            }
            else -> false
        }
    }

    private fun setShoeList() {
        if (viewModel.shoeList.value == null) {
            // Init shoe list (Dummy data)
            viewModel.setShoeList()
        }
    }

    private fun observeViewModel() {
        viewModel.shoeList.observe(viewLifecycleOwner) { showShoeList(it) }
    }

    private fun initEvents() {
        binding.addShoeBtn.setOnClickListener {
            actionForShopDetail()
        }

        // Result after update item in Shoe Detail Screen
        setFragmentResultListener(REQUEST_UPDATE_DETAIL_KEY) { key, bundle ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getSerializable(DETAIL_RESULTS_KEY, ShoeDetail::class.java)?.let {
                    updateShoeList(it)
                }
            } else {
                (bundle.getSerializable(DETAIL_RESULTS_KEY) as ShoeDetail).let {
                    updateShoeList(it)
                }
            }
        }

        // Result after add item in Shoe Detail Screen
        setFragmentResultListener(REQUEST_ADD_DETAIL_KEY) { key, bundle ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getSerializable(DETAIL_RESULTS_KEY, ShoeDetail::class.java)?.shoe?.let {
                    addShoeList(it)
                }
            } else {
                (bundle.getSerializable(DETAIL_RESULTS_KEY) as ShoeDetail).shoe?.let {
                    addShoeList(it)
                }
            }
        }
    }

    private fun updateShoeList(shoeDetail: ShoeDetail) {
        shoeDetail?.let {
            viewModel.updateShoeList(it.index, it.shoe)
        }
    }

    private fun addShoeList(shoe: Shoe) {
        viewModel.addShoeList(shoe)
    }

    private fun showShoeList(list: List<Shoe>?) {
        list?.forEachIndexed { index, shoe ->
            val child: View = layoutInflater.inflate(R.layout.fragment_shoe_list_item, null)
            child.findViewById<TextView>(R.id.shoe_item_name).text = shoe.name
            child.findViewById<TextView>(R.id.shoe_item_size).text =
                activity?.getString(R.string.shoe_list_item_size, shoe.size)
            child.findViewById<TextView>(R.id.shoe_item_company).text =
                activity?.getString(R.string.shoe_list_item_company, shoe.company)
            child.setOnClickListener {
                actionForShopDetail(index, shoe)
            }

            binding.shoeListContainer.addView(child)
        }
    }

    private fun actionForLogout() {
        val action = ShoeListFragmentDirections.actionShoeListScreenToLoginScreen()
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun actionForShopDetail(index: Int? = null, shoe: Shoe? = null) {
        val action = ShoeListFragmentDirections.actionShoeListScreenToShoeDetailScreen()
        action.shoeDetail = if (index != null && shoe != null) ShoeDetail(index, shoe) else null
        Navigation.findNavController(requireView()).navigate(action)
    }
}