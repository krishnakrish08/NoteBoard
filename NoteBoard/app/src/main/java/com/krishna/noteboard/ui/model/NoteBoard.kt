package com.krishna.noteboard.ui.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "noteBoard")
data class NoteBoard(
    var title: String,
    var data: String,
    var date: String,
    var characters: Long
) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}