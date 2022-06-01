package com.krishna.noteboard.ui.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.krishna.noteboard.ui.database.NoteBoardDatabase
import com.krishna.noteboard.ui.model.NoteBoard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class NoteRepository {

    companion object {

        private var noteDatabase: NoteBoardDatabase? = null

        private fun initialiseDB(context: Context): NoteBoardDatabase? {
            return NoteBoardDatabase.getInstance(context)
        }

        fun insert(context: Context, note: NoteBoard) {
            noteDatabase = initialiseDB(context)

            CoroutineScope(IO).launch {
                noteDatabase?.getDao()?.insert(note)
            }
        }

        fun getCardData(context: Context): LiveData<List<NoteBoard>>? {
            noteDatabase = initialiseDB(context)
            return noteDatabase?.getDao()?.getCardsData()
        }

        fun update(context: Context, note: NoteBoard) {
            noteDatabase = initialiseDB(context)

            CoroutineScope(IO).launch {
                noteDatabase?.getDao()?.update(note)
            }
        }

        fun search(context: Context, data: String): LiveData<List<NoteBoard>>? {
            noteDatabase = initialiseDB(context)
            return noteDatabase?.getDao()?.search(data)
        }

        fun delete(context: Context, note: NoteBoard) {
            noteDatabase = initialiseDB(context)
            CoroutineScope(IO).launch {
                noteDatabase?.getDao()?.delete(note)
            }
        }
    }
}