package com.h.alamassi.library.adapter

import android.app.Activity
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.h.alamassi.library.R
import com.h.alamassi.library.databinding.FragmentBookDescriptionBinding
import com.h.alamassi.library.databinding.ItemBookBinding
import com.h.alamassi.library.datasource.DatabaseHelper
import com.h.alamassi.library.fragment.BookDescriptionFragment
import com.h.alamassi.library.model.Book

class BookDescriptionAdapter( var activity: AppCompatActivity, var data: ArrayList<Book>) :
    RecyclerView.Adapter<BookDescriptionAdapter.MyViewHolder>() {
    class MyViewHolder(var binding: FragmentBookDescriptionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = FragmentBookDescriptionBinding.inflate(activity.layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentBook = data[position]
        val bundle = Bundle()
        bundle.getLong("book_id", currentBook.id!!)
        holder.binding.ivImageBook.setImageURI(Uri.parse(currentBook.image))
        holder.binding.tvBookName.text = currentBook.name
        holder.binding.tvAuthor.text = currentBook.author
        holder.binding.tvCategory.text = currentBook.categoryId.toString()
        holder.binding.tvShelf.text = currentBook.name
        holder.binding.tvLanguage.text = currentBook.language
        holder.binding.tvDescription.text = currentBook.description
        holder.binding.tvNuOfCopies.text = currentBook.numOfCopies
        holder.binding.tvNumOfPages.text = currentBook.numOfPages
        holder.binding.tvYear.text = currentBook.year

    }

    override fun getItemCount(): Int {
        return data.size
    }

}