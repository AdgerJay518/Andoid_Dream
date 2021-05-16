package com.example.myapplication.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.logic.model.DetailResponse
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.now.*

class DetailActivity : AppCompatActivity() {
    val viewModel by lazy { ViewModelProvider(this).get(DetailViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        if (viewModel.DreamId.isEmpty()) {
            viewModel.DreamId = intent.getStringExtra("id") ?: ""
        }
        viewModel.liveData.observe(this, Observer { result ->
            val detail = result.getOrNull()
            if (detail != null) {
                showDetailInfo(detail)
            } else {
                Toast.makeText(this, "错误的解梦ID", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
            swipeRefresh.isRefreshing = false
        })
        viewModel.refreshDetail(viewModel.DreamId)

        navBtn.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {}
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {}
            override fun onDrawerClosed(drawerView: View) {
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(drawerView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        })
    }
    private fun showDetailInfo(detail: DetailResponse.Detail){
        Dream_title.text=detail.title
        val str = StringBuilder()
        for(i in 0 until detail.list.size){
            str.append(detail.list[i])
        }
        Dream_detail.text=str
        detailLayout.visibility = View.VISIBLE
    }
    fun refreshDetail() {
        viewModel.refreshDetail(viewModel.DreamId)
        swipeRefresh.isRefreshing = true
    }
}
