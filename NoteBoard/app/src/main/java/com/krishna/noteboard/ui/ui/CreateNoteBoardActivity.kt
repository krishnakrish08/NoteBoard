package com.krishna.noteboard.ui.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.krishna.noteboard.R
import com.krishna.noteboard.ui.model.NoteBoard
import com.krishna.noteboard.ui.viewModel.NoteBoardViewModel
import kotlinx.android.synthetic.main.activity_create_note_board.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class CreateNoteBoardActivity : AppCompatActivity(R.layout.activity_create_note_board) {

    private lateinit var dateTime: String
    private lateinit var getData: String
    private lateinit var getTitle: String
    private lateinit var noteViewModel: NoteBoardViewModel
    private lateinit var date: Date
    private var getCharacter by Delegates.notNull<Long>()
    private lateinit var getDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar1)

        noteViewModel = NoteBoardViewModel()

        getDate()

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(true)
            setHomeAsUpIndicator(ContextCompat.getDrawable(this@CreateNoteBoardActivity, R.drawable.arrow))
        }

        toolbar1.setNavigationOnClickListener {
            backToHomePage()
        }

        note.addTextChangedListener(textWatcher)
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            characters.text = " | Characters " + s?.length.toString()

        }
    }

    private fun backToHomePage() {
        startActivity(Intent(this, NoteBoardActivity::class.java))
        finish()
    }

    private fun getDate() {
        date = Calendar.getInstance().time

        val simpleDateFormat = SimpleDateFormat("MMMM dd yyyy HH:mm:ss a ", Locale.getDefault())
        dateTime = simpleDateFormat.format(date).toString()

        currentDate.text = dateTime
    }

    private fun saveDataIntoDatabase() {
        getTitle = titleEdit.text.toString()
        getData = note.text.toString()
        getDate = dateTime
        getCharacter = getData.trim().length.toLong()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.note_board_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                saveDataIntoDatabase()
                if (getData.isNotEmpty()) {
                    noteViewModel.insert(
                        this,
                        NoteBoard(getTitle, getData, dateTime, getCharacter)
                    )
                    backToHomePage()
                } else {
                    Toast.makeText(this, "Enter any character", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}