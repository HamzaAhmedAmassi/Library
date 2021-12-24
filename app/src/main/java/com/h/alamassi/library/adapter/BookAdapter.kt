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
import com.h.alamassi.library.databinding.ItemBookBinding
import com.h.alamassi.library.datasource.DatabaseHelper
import com.h.alamassi.library.fragment.BookDescriptionFragment
import com.h.alamassi.library.fragment.BooksFragment
import com.h.alamassi.library.model.Book

class BookAdapter( var activity: AppCompatActivity, var data: ArrayList<Book>) :
    RecyclerView.Adapter<BookAdapter.MyViewHolder>() {
    class MyViewHolder(var binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemBookBinding.inflate(activity.layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.root.setOnLongClickListener {
             val alertDialog = AlertDialog.Builder(activity)
             alertDialog.setTitle("Delete Book")
             alertDialog.setMessage("Are you sure to delete book ?")
             alertDialog.setIcon(R.drawable.ic_delete)
             alertDialog.setPositiveButton("Yes") { _, _ ->
                 if (DatabaseHelper(activity).deleteBook(data[position].id!!))
                     data.removeAt(position)
                 Toast.makeText(activity, "Deleted Successfully", Toast.LENGTH_SHORT).show()
             }
             alertDialog.setNegativeButton("No") { _, _ ->
             }
             alertDialog.create().show()
             true
         }
        val currentBook = data[position]
        holder.binding.root.setOnClickListener {
            val bundle = Bundle()
            bundle.putLong("book_id", currentBook.id ?: -1)
            activity.run {
                bundle.putLong("book_id", currentBook.id ?: -1)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, BookDescriptionFragment::class.java,bundle).commit()
            }
        }

        holder.binding.ivImageBook.setImageURI(Uri.parse(currentBook.image))
        holder.binding.tvBookName.text = data[position].name
        holder.binding.tvAuthor.text = data[position].author
        holder.binding.tvCategory.text = data[position].categoryId.toString()
        holder.binding.tvShelf.text = data[position].name
        holder.binding.ibFavorite.setOnClickListener {}

    }

    override fun getItemCount(): Int {
        return data.size
    }
}