package com.udacity.shoestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.udacity.shoestore.databinding.FragmentWelcomeBinding
import com.udacity.shoestore.models.User

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)

        val args = WelcomeFragmentArgs.fromBundle(requireArguments())
        user = args.user as User

        binding.welcomeTextView.text = getString(R.string.welcome_user_label, user.email)
        binding.welcomeBtn.setOnClickListener {
            val action = WelcomeFragmentDirections.actionWelcomeScreenToInstructionsScreen(user)
            Navigation.findNavController(requireView()).navigate(action)
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}