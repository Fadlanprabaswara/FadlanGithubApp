package com.example.fadlangithub.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fadlangithub.User
import com.example.fadlangithub.databinding.ItemUserBinding

class Fragmentadapter(private val userList: List<User>) : RecyclerView.Adapter<Fragmentadapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(userList[position]){
                holder.binding.tvItemName.text = login
                Glide.with(itemView.context)
                    .load(avatarUrl)
                    .into(binding.imgItemPhoto)
            }
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}