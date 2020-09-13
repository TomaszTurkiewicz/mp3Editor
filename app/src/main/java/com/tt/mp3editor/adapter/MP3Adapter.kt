package com.tt.mp3editor.adapter

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tt.mp3editor.R
import com.tt.mp3editor.`class`.AudioModel
import kotlinx.android.synthetic.main.single_row.*
import kotlinx.android.synthetic.main.single_row.view.*

class MP3Adapter (private val items: ArrayList<AudioModel>, private val context: Context, private val onClickListener: (View,AudioModel)->Unit):
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_row,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = items[position].title
        val audioModel = items[position]
        holder.option.setOnClickListener { v->
            onClickListener.invoke(v,audioModel)
        }

    }
}



class ViewHolder (view: View):RecyclerView.ViewHolder(view){
    val title = view.row_title!!
    val option = view.row_options_button!!
}