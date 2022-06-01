package com.krishna.noteboard.ui.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.krishna.noteboard.R
import com.krishna.noteboard.ui.adapter.NoteBoardAdapter
import com.krishna.noteboard.ui.listener.ItemClickListener
import com.krishna.noteboard.ui.model.NoteBoard
import com.krishna.noteboard.ui.viewModel.NoteBoardViewModel
import kotlinx.android.synthetic.main.activity_main.*


class NoteBoardActivity : AppCompatActivity(R.layout.activity_main), ItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var noteViewModel: NoteBoardViewModel
    private lateinit var noteAdapter: NoteBoardAdapter
    private lateinit var noteList: ArrayList<NoteBoard>
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)

        noteAdapter = NoteBoardAdapter(ArrayList(), this, "")

        initialiseRecyclerView()

        noteViewModel = ViewModelProvider(this)[NoteBoardViewModel::class.java]
        noteViewModel.getCardsData(this)?.observe(this, Observer {
            noteAdapter.setData(it as ArrayList<NoteBoard>)
            noteList = it
        })

        floatingActionButton.setOnClickListener {
            val intent = Intent(this, CreateNoteBoardActivity::class.java)
            startActivity(intent)
        }

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    private fun initialiseRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = noteAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.note_board_menu, menu)

        menu.findItem(R.id.save).isVisible = false

        val search = menu.findItem(R.id.search)

        searchView = search?.actionView as SearchView
        searchView.background = null
        searchView.isSubmitButtonEnabled = false

        val txtSearch =
            searchView.findViewById<View>(androidx.appcompat.R.id.search_src_text) as EditText
        txtSearch.setHintTextColor(Color.LTGRAY)
        txtSearch.setTextColor(Color.WHITE)


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null)
                    getItemsFromDB(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null)
                    getItemsFromDB(newText)
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun getItemsFromDB(data: String) {
        val searchText = "%$data%"
        noteViewModel.search(this, searchText)?.observe(this, Observer {
            Log.d("main", "$it")
            noteAdapter.setData(it as ArrayList<NoteBoard>)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
            }
            R.id.list -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClickListener(position: Int) {
        val intent = Intent(this, UpdateActivity::class.java)
        intent.putExtra("noteBoard", noteList[position])
        startActivity(intent)
    }

    private val simpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = noteList[position]
                when (direction) {
                    ItemTouchHelper.RIGHT -> {
                        noteViewModel.delete(this@NoteBoardActivity, note)
                        noteAdapter.notifyDataSetChanged()
                    }
                    ItemTouchHelper.LEFT -> {
                        noteViewModel.delete(this@NoteBoardActivity, note)
                        noteAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
}