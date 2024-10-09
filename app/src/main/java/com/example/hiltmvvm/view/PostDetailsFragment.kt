package com.example.hiltmvvm.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hiltmvvm.adapter.CommentAdapter
import com.example.hiltmvvm.core.constant.StringConstants
import com.example.hiltmvvm.core.model.Posts
import com.example.hiltmvvm.core.model.Resource
import com.example.hiltmvvm.databinding.FragmentPostDetailsBinding
import com.example.hiltmvvm.viewmodel.PostDetailsViewModel

class PostDetailsFragment : Fragment() {
    private var _binding: FragmentPostDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: PostDetailsViewModel by lazy {
        ViewModelProvider(requireActivity())[PostDetailsViewModel::class.java]
    }
    private lateinit var commentAdapter: CommentAdapter
    private var post: Posts? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPost()
        setupRecyclerView()
        observeData()
        getData()
    }

    private fun observeData() = viewModel.commentList.observe(viewLifecycleOwner) { resource ->
        when (resource) {
            is Resource.Success -> {
                commentAdapter.commentList = resource.data

                binding.commentsRecyclerView.visibility = View.VISIBLE
                binding.commentProgressBar.visibility = View.GONE
                binding.commentErrorTextView.visibility = View.GONE
            }

            is Resource.Loading -> {
                binding.commentProgressBar.visibility = View.VISIBLE
                binding.commentsRecyclerView.visibility = View.GONE
                binding.commentErrorTextView.visibility = View.GONE
            }

            is Resource.Error -> {
                binding.commentErrorTextView.text = StringConstants.ERROR_MSG
                binding.commentErrorTextView.visibility = View.VISIBLE
                binding.commentProgressBar.visibility = View.GONE
                binding.commentsRecyclerView.visibility = View.GONE
            }

            is Resource.Empty -> {
                binding.commentErrorTextView.text = StringConstants.EMPTY_MSG
                binding.commentErrorTextView.visibility = View.VISIBLE
                binding.commentProgressBar.visibility = View.GONE
                binding.commentsRecyclerView.visibility = View.GONE
            }
        }
    }

    private fun setPost() {
        arguments?.let { bundle ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getSerializable("post", Posts::class.java)?.let { bundlePost ->
                    post = bundlePost
                }
            } else {
                bundle.getSerializable("post")?.let { bundlePost ->
                    post = bundlePost as Posts
                }
            }
        }
        binding.post = post
    }

    private fun getData() {
        post?.let {
            viewModel.getData(it.id)
        }
    }

    private fun setupRecyclerView() {
        commentAdapter = CommentAdapter()
        binding.commentsRecyclerView.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearList()
        _binding = null
    }
}