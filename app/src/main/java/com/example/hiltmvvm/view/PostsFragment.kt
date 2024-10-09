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
import com.example.hiltmvvm.adapter.PostAdapter
import com.example.hiltmvvm.core.constant.StringConstants
import com.example.hiltmvvm.core.model.Resource
import com.example.hiltmvvm.databinding.FragmentPostsBinding
import com.example.hiltmvvm.viewmodel.PostsViewModel

class PostsFragment : Fragment() {
    private var _binding: FragmentPostsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: PostsViewModel by lazy {
        ViewModelProvider(requireActivity())[PostsViewModel::class.java]
    }
    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeData()
        getData()
        navigateToDetails(view)
        searchEditTextListener()
    }

    private fun searchEditTextListener() = binding.searchEditText.addTextChangedListener { text ->
        viewModel.searchText(text.toString())
    }

    private fun navigateToDetails(view: View) = postAdapter.setOnClickItem {
        val action = PostsFragmentDirections.actionPostsFragmentToPostDetailsFragment(it)
        Navigation.findNavController(view).navigate(action)
    }


    private fun observeData() = viewModel.postList.observe(viewLifecycleOwner) { resource ->
        when (resource) {
            is Resource.Success -> {
                postAdapter.postList = resource.data

                binding.postRecyclerView.visibility = View.VISIBLE
                binding.errorTextView.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
            }

            is Resource.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.errorTextView.visibility = View.GONE
                binding.postRecyclerView.visibility = View.GONE
            }

            is Resource.Error -> {
                binding.errorTextView.text = StringConstants.ERROR_MSG
                binding.errorTextView.visibility = View.VISIBLE
                binding.postRecyclerView.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
            }

            is Resource.Empty -> {
                binding.errorTextView.text = StringConstants.EMPTY_MSG
                binding.errorTextView.visibility = View.VISIBLE
                binding.postRecyclerView.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearList()
        _binding = null
    }

    private fun setupRecyclerView() {
        postAdapter = PostAdapter()
        binding.postRecyclerView.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun getData() {
        viewModel.getData()
    }
}