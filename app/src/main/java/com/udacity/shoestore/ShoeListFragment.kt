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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.udacity.shoestore.databinding.FragmentShoeListBinding
import com.udacity.shoestore.datasource.AccountDataSourceImp
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.models.ShoeDetail
import com.udacity.shoestore.viewmodel.ShoeListViewModel
import com.udacity.shoestore.viewmodel.ShoeListViewModelFactory


class ShoeListFragment : Fragment(), MenuProvider {
    private lateinit var binding: FragmentShoeListBinding

    val viewModel: ShoeListViewModel by activityViewModels { ShoeListViewModelFactory(AccountDataSourceImp(activity?.baseContext)) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_list, container, false)

        // Add menu items without overriding methods in the Activity
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
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
                actionForShopDetail(index)
            }

            binding.shoeListContainer.addView(child)
        }
    }

    private fun actionForLogout() {
        val action = ShoeListFragmentDirections.actionShoeListScreenToLoginScreen()
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun actionForShopDetail(index: Int = -1) {
        viewModel.updateSelectedShoeIndex(index)
        val action = ShoeListFragmentDirections.actionShoeListScreenToShoeDetailScreen()
        Navigation.findNavController(requireView()).navigate(action)
    }
}