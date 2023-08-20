package com.udacity.shoestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.udacity.shoestore.databinding.FragmentInstructionsBinding
import com.udacity.shoestore.models.User

class InstructionsFragment : Fragment() {

    private lateinit var binding: FragmentInstructionsBinding
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_instructions, container, false)

        val args = InstructionsFragmentArgs.fromBundle(requireArguments())
        user = args.user as User

        binding.emailTextView.text = getString(R.string.instructions_email_label, user.email)
        binding.listBtn.setOnClickListener {
            actionForListBtn()
        }

        return binding.root
    }

    private fun actionForListBtn() {
        val action = InstructionsFragmentDirections.actionInstructionsScreenToShoeListScreen()
        Navigation.findNavController(requireView()).navigate(action)
    }
}