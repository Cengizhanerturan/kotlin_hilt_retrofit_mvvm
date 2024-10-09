package com.example.hiltmvvm.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hiltmvvm.adapter.UserAdapter
import com.example.hiltmvvm.core.constant.StringConstants.Companion.EMPTY_MSG
import com.example.hiltmvvm.core.constant.StringConstants.Companion.ERROR_MSG
import com.example.hiltmvvm.core.model.Resource
import com.example.hiltmvvm.databinding.FragmentUsersBinding
import com.example.hiltmvvm.viewmodel.UserViewModel


class UsersFragment : Fragment() {
    private var _binding: FragmentUsersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(requireActivity())[UserViewModel::class.java]
    }

    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getData()
        observeData()
        navigateToUserDetails(view)
        searchEditTextListener()
    }

    private fun searchEditTextListener() = binding.searchEditText.addTextChangedListener { text ->
        viewModel.searchText(text.toString())
    }


    private fun navigateToUserDetails(view: View) {
        userAdapter.setOnClick {
            val action = UsersFragmentDirections.actionUsersFragmentToUserDetailsFragment(it)
            Navigation.findNavController(view).navigate(action)
        }
    }

    private fun observeData() = viewModel.userList.observe(viewLifecycleOwner) { resource ->
        when (resource) {
            is Resource.Success -> {
                userAdapter.userList = resource.data

                binding.usersRecylerView.visibility = View.VISIBLE
                binding.usersProgressBar.visibility = View.GONE
                binding.userInfoTextView.visibility = View.GONE
            }

            is Resource.Loading -> {
                binding.usersProgressBar.visibility = View.VISIBLE
                binding.usersRecylerView.visibility = View.GONE
                binding.userInfoTextView.visibility = View.GONE
            }

            is Resource.Error -> {
                binding.userInfoTextView.text = ERROR_MSG
                binding.userInfoTextView.visibility = View.VISIBLE
                binding.usersProgressBar.visibility = View.GONE
                binding.usersRecylerView.visibility = View.GONE
            }

            is Resource.Empty -> {
                binding.userInfoTextView.text = EMPTY_MSG
                binding.userInfoTextView.visibility = View.VISIBLE
                binding.usersProgressBar.visibility = View.GONE
                binding.usersRecylerView.visibility = View.GONE
            }
        }
    }

    private fun getData() {
        viewModel.getData()
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter()
        binding.usersRecylerView.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearList()
        _binding = null
    }
}