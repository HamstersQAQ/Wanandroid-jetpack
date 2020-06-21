package com.yppcat.wanandroid.view.chapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yppcat.wanandroid.R
import com.yppcat.wanandroid.network.data.ArticleList
import com.yppcat.wanandroid.network.data.Chapter

class ChapterAdapter(private val context: Context, private val dataList: List<Any?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var viewHolder: RecyclerView.ViewHolder
    private var onClickListener: OnClickListener? = null


    inner class ChapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chapterText: TextView = itemView.findViewById(R.id.text_chapter)
    }

    inner class ArticleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText : TextView = itemView.findViewById(R.id.title)
        val authorText : TextView = itemView.findViewById(R.id.author)
    }

    fun setOnClickListener(clickListener: OnClickListener) {
        this.onClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (dataList[0]) {
            is Chapter.Data -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_chapter, null)
                viewHolder = ChapterHolder(view)
            }
            is ArticleList.Data.Data -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_article,null)
                viewHolder = ArticleHolder(view)
            }
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (dataList[0]) {
            is Chapter.Data -> {
                val holder = holder as ChapterHolder
                val data = dataList[position] as Chapter.Data
                holder.chapterText.text = data.name
            }
            is ArticleList.Data.Data ->{
                val holder = holder as ArticleHolder
                val data = dataList[position] as ArticleList.Data.Data
                holder.titleText.text = data.title
                holder.authorText.text = data.author
            }
        }
        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position)
        }
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }

}