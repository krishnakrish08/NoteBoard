package com.krishna.noteboard.ui.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.krishna.noteboard.ui.model.NoteBoard

@Dao
interface NoteBoardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteBoard)

    @Query("SELECT * FROM noteBoard ORDER BY id DESC")
    fun getCardsData(): LiveData<List<NoteBoard>>

    @Update
    suspend fun update(note: NoteBoard)

    @Query("SELECT * FROM noteBoard WHERE data LIKE :data or title like :data")
    fun search(data: String): LiveData<List<NoteBoard>>

    @Delete
    suspend fun delete(note: NoteBoard)

}