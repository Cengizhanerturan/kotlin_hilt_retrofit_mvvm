package com.example.hiltmvvm.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hiltmvvm.adapter.UserDetailsAdapter
import com.example.hiltmvvm.core.constant.StringConstants
import com.example.hiltmvvm.core.model.Resource
import com.example.hiltmvvm.core.model.Users
import com.example.hiltmvvm.databinding.FragmentUserDetailsBinding
import com.example.hiltmvvm.viewmodel.UserDetailsViewModel

class UserDetailsFragment : Fragment() {
    private var _binding: FragmentUserDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var userDetailsAdapter: UserDetailsAdapter
    private var user: Users? = null
    private val viewModel: UserDetailsViewModel by lazy {
        ViewModelProvider(requireActivity())[UserDetailsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUser()
        setupRecyclerView()
        getData()
        observeData()
        navigateToPostDetails(view)
    }

    private fun navigateToPostDetails(view: View) = userDetailsAdapter.setOnClickItem {
        val action =
            UserDetailsFragmentDirections.actionUserDetailsFragmentToPostDetailsFragment(it)
        Navigation.findNavController(view).navigate(action)
    }


    private fun setupRecyclerView() {
        userDetailsAdapter = UserDetailsAdapter()
        binding.userDetailsRecyclerView.apply {
            adapter = userDetailsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeData() = viewModel.postList.observe(viewLifecycleOwner) { resource ->
        when (resource) {
            is Resource.Success -> {
                userDetailsAdapter.postList = resource.data

                binding.userDetailsRecyclerView.visibility = View.VISIBLE
                binding.userDetailsProgressBar.visibility = View.GONE
                binding.userDetailsErrorTextView.visibility = View.GONE
            }

            is Resource.Loading -> {
                binding.userDetailsProgressBar.visibility = View.VISIBLE
                binding.userDetailsRecyclerView.visibility = View.GONE
                binding.userDetailsErrorTextView.visibility = View.GONE
            }

            is Resource.Error -> {
                binding.userDetailsErrorTextView.text = StringConstants.ERROR_MSG
                binding.userDetailsErrorTextView.visibility = View.VISIBLE
                binding.userDetailsProgressBar.visibility = View.GONE
                binding.userDetailsRecyclerView.visibility = View.GONE
            }

            is Resource.Empty -> {
                binding.userDetailsErrorTextView.text = StringConstants.EMPTY_MSG
                binding.userDetailsErrorTextView.visibility = View.VISIBLE
                binding.userDetailsProgressBar.visibility = View.GONE
                binding.userDetailsRecyclerView.visibility = View.GONE
            }
        }
    }

    private fun getData() {
        user?.let {
            viewModel.getData(it.id)
        }
    }

    private fun setUser() {
        arguments?.let { bundle ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getSerializable("user", Users::class.java)?.let { bundleUser ->
                    user = bundleUser
                }
            } else {
                bundle.getSerializable("user")?.let { bundleUser ->
                    user = bundleUser as Users
                }
            }
        }
        binding.user = user
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearList()
        _binding = null
    }
}