package com.tt.mp3editor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tt.mp3editor.R
import com.tt.mp3editor.`class`.AudioModel
import kotlinx.android.synthetic.main.single_row.*
import kotlinx.android.synthetic.main.single_row.view.*

class MP3Adapter (private val items: ArrayList<AudioModel>, private val context: Context):
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_row,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = items[position].title
    }
}

class ViewHolder (view: View):RecyclerView.ViewHolder(view){
    val title = view.row_title!!
}