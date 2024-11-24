package com.example.musicapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class playlistadapter(val context:Context,val playlist:ArrayList<playlistmodel>) : RecyclerView.Adapter<playlistadapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pl: TextView =itemView.findViewById(R.id.pltitle)
        val im: ImageView =itemView.findViewById(R.id.imgpl)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View= LayoutInflater.from(context).inflate(R.layout.playlistblueprint,parent,false)
        return  ViewHolder(view);
    }

    override fun getItemCount(): Int {
        return playlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p=playlist[position]
        holder.pl.text=p.title
        holder.im.setImageResource(p.image)
        holder.itemView.setOnClickListener {
            Intent(context,songslist::class.java).also {
                startActivity(context,it,null)
            }
        }

    }


}