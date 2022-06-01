package com.krishna.noteboard.ui.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.krishna.noteboard.R
import com.krishna.noteboard.ui.model.NoteBoard
import com.krishna.noteboard.ui.viewModel.NoteBoardViewModel
import kotlinx.android.synthetic.main.activity_update.*
import java.text.SimpleDateFormat
import java.util.*

class UpdateActivity : AppCompatActivity(R.layout.activity_update) {

    private lateinit var date: Date
    private lateinit var getNote: NoteBoard
    private lateinit var noteViewModel: NoteBoardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getDate()

        noteViewModel = NoteBoardViewModel()
        updateCharacter.text = "0 characters"
        updateNote.addTextChangedListener(textWatcher)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            getNote = bundle.getSerializable("noteBoard") as NoteBoard
        }

        loadNote(getNote)

        updateData.setOnClickListener {
            updateNote()
        }
    }

    private fun loadNote(note: NoteBoard) {
        titleUpdateEdit.setText(note.title)
        updateNote.setText(note.data)
        updateCharacter.text = "${note.characters}"
    }

    private fun updateNote() {

        getNote.title = titleUpdateEdit.text.toString()
        getNote.data = updateNote.text.toString()
        getNote.date = updateDate.text.toString()
        getNote.characters = updateNote.text.toString().length.toLong()
        noteViewModel.update(this, getNote)

        val intent = Intent(this, NoteBoardActivity::class.java)
        startActivity(intent)
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            updateCharacter.text = " | Character " + s?.length.toString()

        }
    }

    private fun getDate() {
        date = Calendar.getInstance().time

        val simpleDateFormat = SimpleDateFormat("MMMM dd yyyy HH:mm:ss a ", Locale.getDefault())
        val dateTime = simpleDateFormat.format(date).toString()

        updateDate.text = dateTime
    }
}