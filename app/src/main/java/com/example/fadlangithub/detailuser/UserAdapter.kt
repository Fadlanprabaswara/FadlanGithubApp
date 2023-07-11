package com.example.fadlangithub.detailuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fadlangithub.User
import com.example.fadlangithub.databinding.ItemUserBinding

class  UserAdapter( private var listUser: List<User>)  : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(listUser[position]) {
                binding.tvItemName.text = login
                Glide.with(holder.itemView.context)
                    .load(avatarUrl)
                    .into(binding.imgItemPhoto)
                itemView.setOnClickListener{
                    val intent = Intent(holder.itemView.context, DetailUser::class.java)
                    intent.putExtra("Login",listUser[holder.adapterPosition].login)
                    intent.putExtra("Id",listUser[holder.adapterPosition].id)
                    intent.putExtra("Avatar",listUser[holder.adapterPosition].avatarUrl)
                    itemView.context.startActivity(intent)
                    Toast.makeText(
                        holder.itemView.context,
                        "kamu memilih " + listUser[holder.adapterPosition].login,
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }
        }

    }

    override fun getItemCount() = listUser.size
    fun setUsers(it: List<User>) {
        listUser = it
        notifyDataSetChanged()

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}