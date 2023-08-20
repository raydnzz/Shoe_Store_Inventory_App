package com.udacity.shoestore

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.udacity.shoestore.databinding.FragmentLoginBinding
import com.udacity.shoestore.datasource.AccountDataSource
import com.udacity.shoestore.datasource.AccountDataSourceImp
import com.udacity.shoestore.models.User

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var accountDataSource: AccountDataSource

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        accountDataSource = AccountDataSourceImp(activity?.baseContext)

        binding.loginBtn.setOnClickListener {
            if (checkAccount()) {
                saveAccountLogged()
                actionForLoginBtn()
            }
        }

        binding.registBtn.setOnClickListener {
            if (validAccount()) {
                saveAccountLogged()
                actionForRegistBtn()
            }
        }
        return binding.root
    }

    private fun checkAccount(): Boolean {
        if (binding.emailEditText.text.toString() != "admin" || binding.passwordEditText.text.toString() != "admin") {
            binding.errorLabel.text = context?.getString(R.string.login_error) ?: ""
            return false
        }
        return true
    }

    private fun validAccount(): Boolean {
        if (binding.emailEditText.text.toString().isEmpty() || binding.passwordEditText.text.toString().isEmpty()) {
            binding.errorLabel.text = context?.getString(R.string.login_invalid) ?: ""
            return false
        }
        return true
    }

    private fun actionForLoginBtn() {
        val user =
            User(binding.emailEditText.text.toString(), binding.passwordEditText.text.toString())

        val action = LoginFragmentDirections.actionLoginScreenToWelcomeScreen(user)
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun actionForRegistBtn() {
        val user =
            User(binding.emailEditText.text.toString(), binding.passwordEditText.text.toString())
        val action = LoginFragmentDirections.actionLoginScreenToWelcomeScreen(user)
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun saveAccountLogged() {
        accountDataSource.saveAccount(binding.emailEditText.text.toString())
    }
}