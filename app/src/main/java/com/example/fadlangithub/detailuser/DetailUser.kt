package com.example.fadlangithub.detailuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.fadlangithub.R
import com.example.fadlangithub.SectionsPagerAdapter
import com.example.fadlangithub.data.Favorite
import com.example.fadlangithub.databinding.ActivityDetailUserBinding
import com.example.fadlangithub.favorite.FavoriteActivity
import com.example.fadlangithub.setting.SettingActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUser : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val query = intent.getStringExtra("Login")
        val id = intent.getIntExtra("Id", 0)
        val avatar = intent.getStringExtra("Avatar")

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        detailViewModel.detailResponseUser(query.toString())

        detailViewModel.isLoading.observe(this) {
            loadingShow(it)
        }

        detailViewModel.detailuser.observe(this) { responseDetail ->
            if (responseDetail != null) {
                binding.login.text = responseDetail.login
                binding.nama.text = responseDetail.name
                binding.following.text = "${responseDetail.following} Following"
                binding.follower.text = "${responseDetail.followers} Followers"
                Glide.with(this).load(responseDetail.avatarUrl).into(binding.image)
            }

        }


        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = detailViewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.toggleFavorite.isChecked = true
                        _isChecked = true
                    } else {
                        binding.toggleFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toggleFavorite.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                if (query != null) {
                    if (avatar != null) {
                        detailViewModel.addToFavorite(query, id,avatar)
                    }
                }
            } else {
                detailViewModel.removeFromFavorite(id)
            }
            binding.toggleFavorite.isChecked = _isChecked
        }


        var sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = query ?: ""
        val viewPager: ViewPager2 = binding.viewpager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tab
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Followers"
                1 -> "Following"
                else -> ""
            }
        }.attach()
    }

    private fun loadingShow(it: Boolean?) {
        if (it == true) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


    //bagian menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        menu?.removeItem(R.id.favorite_menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.seeting_menu ->{
                Intent(this, SettingActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


}