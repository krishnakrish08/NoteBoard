package com.krishna.noteboard.ui.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.krishna.noteboard.ui.model.NoteBoard
import com.krishna.noteboard.ui.repository.NoteRepository

class NoteBoardViewModel : ViewModel() {

    fun insert(context: Context, note: NoteBoard) {
        NoteRepository.insert(context, note)
    }

    fun getCardsData(context: Context): LiveData<List<NoteBoard>>? {
        return NoteRepository.getCardData(context)
    }

    fun update(context: Context, note: NoteBoard) {
        NoteRepository.update(context, note)
    }

    fun search(context: Context, data: String): LiveData<List<NoteBoard>>? {
        return NoteRepository.search(context, data)
    }

    fun delete(context: Context, note: NoteBoard) {
        NoteRepository.delete(context, note)
    }
}