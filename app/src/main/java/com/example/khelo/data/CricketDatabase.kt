package com.example.khelo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.khelo.data.dao.MatchDao
import com.example.khelo.data.dao.PlayerDao
import com.example.khelo.data.dao.PlayerMatchStatsDao
import com.example.khelo.data.dao.UserDao
import com.example.khelo.data.entities.Match
import com.example.khelo.data.entities.Player
import com.example.khelo.data.entities.PlayerMatchStats
import com.example.khelo.data.entities.User

@Database(
    entities = [
        User::class,
        Player::class,
        Match::class,
        PlayerMatchStats::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CricketDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun playerDao(): PlayerDao
    abstract fun matchDao(): MatchDao
    abstract fun playerMatchStatsDao(): PlayerMatchStatsDao

    companion object {
        @Volatile
        private var INSTANCE: CricketDatabase? = null

        fun getDatabase(context: Context): CricketDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CricketDatabase::class.java,
                    "cricket_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
