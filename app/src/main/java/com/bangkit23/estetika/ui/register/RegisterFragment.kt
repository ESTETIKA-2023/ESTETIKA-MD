package com.bangkit23.estetika.ui.register

import androidx.navigation.fragment.findNavController
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.bangkit23.estetika.R
import com.bangkit23.estetika.data.model.UserRegister
import com.bangkit23.estetika.databinding.FragmentRegisterBinding
import com.bangkit23.estetika.util.animateVisibility
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModels()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private var registerJob: Job = Job()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setActions() {
        binding.apply {
            btnLogin.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_registerFragment_to_loginFragment)
            )

            signupButton.setOnClickListener {
                handleRegistration()
            }
        }
    }

    private fun handleRegistration() {
        val email = binding.edRegisterEmail.text.toString().trim()
        val name = binding.edRegisterName.text.toString()
        val password = binding.edRegisterPassword.text.toString()

        setLoadingState(true)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                if (registerJob.isActive) registerJob.cancel()
                val dataRegister = UserRegister(password, email, password, name)
                registerJob = launch {
                    viewModel.userRegister(dataRegister).collect { result ->
                        result.onSuccess {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.registration_success),
                                Toast.LENGTH_SHORT
                            ).show()

                            // Automatically navigate user back to the login page
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        }
                        result.onFailure {
                            Snackbar.make(
                                binding.root,
                                getString(R.string.registration_error_message),
                                Snackbar.LENGTH_SHORT
                            ).show()
                            setLoadingState(false)
                        }
                    }
                }
            }
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.apply {
            edRegisterEmail.isEnabled = !isLoading
            edRegisterPassword.isEnabled = !isLoading
            edRegisterName.isEnabled = !isLoading
            signupButton.isEnabled = !isLoading

            // Animate views alpha
            if (isLoading) {
                viewLoading.animateVisibility(true)
            } else {
                viewLoading.animateVisibility(false)
            }
        }
    }
}