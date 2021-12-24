package com.h.alamassi.library.adapter

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.h.alamassi.library.R
import com.h.alamassi.library.databinding.ItemBookBinding
import com.h.alamassi.library.datasource.DatabaseHelper
import com.h.alamassi.library.fragment.BookDescriptionFragment
import com.h.alamassi.library.model.Book

class FavouritesAdapter (var activity: AppCompatActivity, var data: ArrayList<Book>) :
    RecyclerView.Adapter<FavouritesAdapter.MyViewHolder>() {
    class MyViewHolder(var binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemBookBinding.inflate(activity.layoutInflater, parent, false)
        return MyViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentBook = data[position]

        holder.binding.root.setOnClickListener {
            val bundle = Bundle()
            bundle.getLong("book_id", currentBook.id ?: -1)
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BookDescriptionFragment()).commit()
        }

        holder.binding.ivImageBook.setImageURI(Uri.parse(currentBook.image))
        holder.binding.tvBookName.text = currentBook.name
        holder.binding.tvAuthor.text = currentBook.author
        holder.binding.tvCategory.text = currentBook.categoryId.toString()
        holder.binding.tvShelf.text = currentBook.shelf

    }
}