package com.example.fadlangithub.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fadlangithub.User
import com.example.fadlangithub.detailuser.UserAdapter
import com.example.fadlangithub.data.Favorite
import com.example.fadlangithub.databinding.ActivityFavoriteBinding
import com.example.fadlangithub.detailuser.DetailUser

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listUser = listOf<User>()
        adapter = UserAdapter(listUser)
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(this@FavoriteActivity, DetailUser::class.java)
                intent.putExtra("Login", data.login)
                intent.putExtra("Id", data.id)
                intent.putExtra("Avatar", data.avatarUrl)
                startActivity(intent)
            }
        })

        binding.apply {
            recycleView.setHasFixedSize(true)
            recycleView.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            recycleView.adapter = adapter
        }

        showFavoriteUsers()


        viewModel.getFavoriteUser()?.observe(this, {
            if (it != null) {
                val list = mapList(it)
                adapter.setUsers(list)
            }
        })

    }

    private fun mapList(users: List<Favorite>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users) {
            val userMapped = User(
                user.login,
                user.id,
                user.avatarUrl
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }

    private fun showFavoriteUsers() {
        viewModel.getFavoriteUser()?.observe(this, {
            if (it != null) {
                val list = mapList(it)
                adapter.setUsers(list)
            }
        })
    }


}