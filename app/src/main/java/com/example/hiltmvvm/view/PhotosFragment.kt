package com.example.hiltmvvm.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hiltmvvm.adapter.PhotoAdapter
import com.example.hiltmvvm.core.constant.StringConstants
import com.example.hiltmvvm.core.model.Resource
import com.example.hiltmvvm.databinding.FragmentPhotosBinding
import com.example.hiltmvvm.viewmodel.PhotosViewModel

class PhotosFragment : Fragment() {
    private var _binding: FragmentPhotosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var photoAdapter: PhotoAdapter
    private val viewModel: PhotosViewModel by lazy {
        ViewModelProvider(requireActivity())[PhotosViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getData()
        observeData()
    }

    private fun observeData() = viewModel.photoList.observe(viewLifecycleOwner) { resource ->
        when (resource) {
            is Resource.Success -> {
                photoAdapter.photoList = resource.data

                binding.photosRecyclerView.visibility = View.VISIBLE
                binding.photosProgressBar.visibility = View.GONE
                binding.photosErrorTextView.visibility = View.GONE
            }

            is Resource.Loading -> {
                binding.photosProgressBar.visibility = View.VISIBLE
                binding.photosRecyclerView.visibility = View.GONE
                binding.photosErrorTextView.visibility = View.GONE
            }

            is Resource.Error -> {
                binding.photosErrorTextView.text = StringConstants.ERROR_MSG
                binding.photosErrorTextView.visibility = View.VISIBLE
                binding.photosProgressBar.visibility = View.GONE
                binding.photosRecyclerView.visibility = View.GONE
            }

            is Resource.Empty -> {
                binding.photosErrorTextView.text = StringConstants.EMPTY_MSG
                binding.photosErrorTextView.visibility = View.VISIBLE
                binding.photosProgressBar.visibility = View.GONE
                binding.photosRecyclerView.visibility = View.GONE
            }
        }
    }

    private fun getData() {
        viewModel.getData()
    }

    private fun setupRecyclerView() {
        photoAdapter = PhotoAdapter()
        binding.photosRecyclerView.apply {
            adapter = photoAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearList()
        _binding = null
    }
}