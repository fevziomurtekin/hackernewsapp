package com.fevziomurtekin.hackernewsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.util.Util
import com.fevziomurtekin.hackernewsapp.R
import com.fevziomurtekin.hackernewsapp.data.domain.ItemModel
import com.fevziomurtekin.hackernewsapp.util.Ext
import org.jetbrains.anko.find


class NewsAdapter:
    RecyclerView.Adapter<NewsAdapter.Viewholder>{

    interface OnItemClickListener{
        fun onItemClick(item:ItemModel)
    }

    private var context:Context
    private var items:MutableList<ItemModel>
    private var onItemClick : OnItemClickListener?

    constructor(context: Context,
                items:MutableList<ItemModel>,
                onItemClick:OnItemClickListener?){
        this.context = context
        this.items=items
        this.onItemClick=onItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.Viewholder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false)
        return NewsAdapter.Viewholder(itemView, onItemClick!!)


    }

    override fun getItemCount(): Int {
        if(items.isNullOrEmpty())
            return 0
        else
            return items.size
    }

    override fun onBindViewHolder(holder: NewsAdapter.Viewholder, position: Int) {

        /**
         * (MutableList<ItemModel>)
         * data of Items bind to recyclerview.
         */

        holder.bind(items.get(position),onItemClick!!,position)

    }

    class Viewholder(itemView: View,onItemClick: OnItemClickListener)
        :RecyclerView.ViewHolder(itemView){

        var txt_order: TextView
        var txt_title: TextView
        var txt_by: TextView
        var txt_details: TextView
        var btn_news: LinearLayout

        init {
            txt_order = itemView.findViewById(R.id.txt_order)
            txt_title = itemView.findViewById(R.id.txt_order)
            txt_by = itemView.findViewById(R.id.txt_order)
            txt_details = itemView.findViewById(R.id.txt_order)
            btn_news = itemView.findViewById(R.id.btn_news)

        }

        fun bind(item: ItemModel,listener: OnItemClickListener,pos:Int){
            txt_order.text = "$pos ."

            if(item.title.isNullOrEmpty() && item.text.isNullOrEmpty())
                txt_title.text = ""
            else if(!item.title.isNullOrEmpty() && item.text.isNullOrEmpty())
                txt_title.text = item.title.toString()
            else
                txt_title.text = item.text.toString()

            txt_by.text = item.url

            txt_details.text = "${item.score} points by ${item.by} ${Ext.getHours(item.time)} "

            btn_news.setOnClickListener( object :View.OnClickListener{
                override fun onClick(v: View?) {
                    listener.onItemClick(item)
                }

            })


        }

    }


}