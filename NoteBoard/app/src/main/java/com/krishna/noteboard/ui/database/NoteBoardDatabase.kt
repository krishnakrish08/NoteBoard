package com.krishna.noteboard.ui.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.krishna.noteboard.ui.dao.NoteBoardDao
import com.krishna.noteboard.ui.model.NoteBoard

@Database(entities = [NoteBoard::class], version = 2, exportSchema = false)
abstract class NoteBoardDatabase : RoomDatabase() {
    abstract fun getDao(): NoteBoardDao

    companion object {
        private const val DATABASE_NAME = "NoteBoardDatabase"

        @Volatile
        var instance: NoteBoardDatabase? = null

        fun getInstance(context: Context): NoteBoardDatabase? {
            if (instance == null) {
                synchronized(NoteBoardDatabase::class.java)
                {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context, NoteBoardDatabase::class.java,
                            DATABASE_NAME
                        ).build()
                    }
                }
            }

            return instance
        }
    }
}