package com.example.myapplication.ui.dream

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.logic.model.Dream
import com.example.myapplication.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DreamAdapter(private val fragment: DreamFragment, private val dreamList: List<Dream>) :
    RecyclerView.Adapter<DreamAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dTitle: TextView = view.findViewById(R.id.dream_title)
        val dDes: TextView = view.findViewById(R.id.dream_des)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item,
            parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener{
            val position = holder.adapterPosition
            val dream = dreamList[position]
            val activity = fragment.activity
            if(activity is DetailActivity){
                activity.drawerLayout.closeDrawers()
                activity.viewModel.DreamId=dream.id
                activity.refreshDetail()
            }
            else{
                val intent = Intent(parent.context, DetailActivity::class.java).apply {
                    putExtra("id", dream.id)
                }
                fragment.startActivity(intent)
                fragment.activity?.finish()
            }
            fragment.viewModel.saveDream(dream)

        }
        return holder
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dream = dreamList[position]
        holder.dTitle.text = dream.title
        holder.dDes.text=dream.des
    }
    override fun getItemCount() = dreamList.size
}
