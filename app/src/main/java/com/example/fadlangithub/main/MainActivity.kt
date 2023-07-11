package com.example.fadlangithub.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fadlangithub.R
import com.example.fadlangithub.detailuser.UserAdapter
import com.example.fadlangithub.databinding.ActivityMainBinding
import com.example.fadlangithub.favorite.FavoriteActivity
import com.example.fadlangithub.setting.SettingActivity
import com.example.fadlangithub.setting.SettingPreferences

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel> {
        MainViewModel.Factory(SettingPreferences(this))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mainViewModel.listUser.observe(this){
            val adapter = it?.let { it1 -> UserAdapter(it1) }
            binding.recycleView.adapter = adapter
            binding.recycleView.setHasFixedSize(true)
        }

        mainViewModel.getTheme().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }


        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }


        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    mainViewModel.searchUser(query)
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        val layoutManager = LinearLayoutManager(this)
        binding.recycleView.layoutManager = layoutManager
        binding.apply {
            recycleView.layoutManager = layoutManager
            recycleView.setHasFixedSize(true)

        }
        val itemDecoration = DividerItemDecoration(this,layoutManager.orientation)
        binding.recycleView.addItemDecoration(itemDecoration)

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    //bagian option menu

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite_menu ->{
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.seeting_menu ->{
                Intent(this, SettingActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}