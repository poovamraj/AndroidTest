package com.example.androidtest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.request.DefaultClient
import com.auth0.android.result.Credentials
import com.example.androidtest.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {


    private val account: Auth0 by lazy {
        // -- REPLACE this credentials with your own Auth0 app credentials!
        val account = Auth0(
            "40VLgGZDLPWKndnY6SjRN2wvO0EWp151",
            "poovamraj.eu.auth0.com"
        )
        // Only enable network traffic logging on production environments!
        account.networkingClient = DefaultClient(enableLogging = true)
        account
    }

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            WebAuthProvider.login(account)
                .withScheme("demo")
                .start(requireContext(), object : Callback<Credentials, AuthenticationException> {
                    override fun onFailure(error: AuthenticationException) {
                        Log.d(this.javaClass.name, error.toString())
                    }

                    override fun onSuccess(result: Credentials) {
                        Log.d(this.javaClass.name, result.toString())
                    }

                })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}