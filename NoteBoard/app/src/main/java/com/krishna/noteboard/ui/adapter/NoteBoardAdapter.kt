package com.krishna.noteboard.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.krishna.noteboard.R
import com.krishna.noteboard.ui.listener.ItemClickListener
import com.krishna.noteboard.ui.model.NoteBoard
import java.text.SimpleDateFormat
import java.util.*

class NoteBoardAdapter(
    private var noteList: ArrayList<NoteBoard>,
    private val listener: ItemClickListener,
    private val flag: String
) : RecyclerView.Adapter<NoteBoardAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_grid_item, parent, false)
        )
    }

    override fun getItemCount(): Int = noteList.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = noteList[position]
        holder.title.text = note.title
        holder.data.text = note.data

        val simpleDateFormat = SimpleDateFormat("MMMM dd yyyy HH:mm:ss a ", Locale.getDefault())
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault())

        val date: Date = if (note.date.contains("AM") || note.date.contains("PM")) {
            simpleDateFormat.parse(note.date) as Date
        } else {
            dateFormat.parse(note.date) as Date
        }

        val dateTime = date.let { simpleDateFormat.format(it).toString() }

        holder.date.text = dateTime
    }

    fun setData(noteList: ArrayList<NoteBoard>) {
        this.noteList = noteList
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.title)
        val data: TextView = itemView.findViewById(R.id.data)
        val date: TextView = itemView.findViewById(R.id.date)

        init {
            itemView.setOnClickListener {
                listener.onClickListener(adapterPosition)
            }
        }
    }
}