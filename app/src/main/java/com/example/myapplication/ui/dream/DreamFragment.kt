package com.example.myapplication.ui.dream

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_place.*

class DreamFragment : Fragment() {
    val viewModel by lazy { ViewModelProvider(this).get(DreamViewModel::class.java) }
    private lateinit var adapter: DreamAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is MainActivity &&viewModel.isDreamSaved()) {
            val dream = viewModel.getSavedPlace()
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("id", dream.id)
            }
            startActivity(intent)
            activity?.finish()
            return
        }

        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = DreamAdapter(this, viewModel.dreamList)
        recyclerView.adapter = adapter
        searchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {
                viewModel.searchDream(content)
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.dreamList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.liveData.observe(this, Observer{ result ->
            val dream = result.getOrNull()
            if (dream != null) {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.dreamList.clear()
                viewModel.dreamList.addAll(dream)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到此类型", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
}
