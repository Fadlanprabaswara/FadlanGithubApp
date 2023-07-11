package com.example.fadlangithub.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fadlangithub.User
import com.example.fadlangithub.databinding.FragmentFollowBinding

/**
 * A simple [Fragment] subclass.
 * Use the [FollowFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private val followViewModel by viewModels<FollowViewModel>()

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var position = arguments?.getInt(ARG_POSITION)
        var username = arguments?.getString(ARG_USERNAME)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }
        if (position == 1){
            username?.let { followViewModel.getfollower(it) }
        } else {
            username?.let { followViewModel.getfollowing(it) }
        }
        followViewModel.follower.observe(viewLifecycleOwner){
            if (it != null) {
                show(it)
            }
        }
        followViewModel.following.observe(viewLifecycleOwner){
            if (it != null){
                show(it)
            }
        }
        followViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
    }

    private fun show (userlist : List<User>){
        val adapter = Fragmentadapter(userlist)
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.recycleVieww.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}