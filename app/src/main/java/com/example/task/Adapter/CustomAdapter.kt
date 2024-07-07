package com.example.task.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.db.Channels

class CustomAdapter(private val mList: List<Channels>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.channel_list_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        holder.itemName.setText(ItemsViewModel.name)
        holder.itemCreator.setText(ItemsViewModel.creator)
        holder.itemGroupEmail.setText(ItemsViewModel.group_email)
        holder.itemFolderName.setText(ItemsViewModel.group_folder_name)

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val itemName: TextView = itemView.findViewById(R.id.itemName)
        val itemCreator: TextView = itemView.findViewById(R.id.itemCreator)
        val itemGroupEmail: TextView = itemView.findViewById(R.id.itemGroupEmail)
        val itemFolderName: TextView = itemView.findViewById(R.id.itemFolderName)
    }
}