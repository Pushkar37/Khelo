package com.example.khelo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.khelo.data.entities.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: Player): Long
    
    @Update
    suspend fun updatePlayer(player: Player)
    
    @Query("SELECT * FROM players WHERE userPhone = :userPhone")
    suspend fun getPlayerByPhone(userPhone: String): Player?
    
    @Query("SELECT * FROM players WHERE userPhone = :userPhone")
    fun getPlayerByPhoneFlow(userPhone: String): Flow<Player?>
    
    @Query("SELECT * FROM players")
    fun getAllPlayers(): Flow<List<Player>>
    
    @Query("UPDATE players SET totalMatches = totalMatches + 1 WHERE userPhone = :userPhone")
    suspend fun incrementMatchCount(userPhone: String)
    
    @Query("UPDATE players SET totalRuns = totalRuns + :runs WHERE userPhone = :userPhone")
    suspend fun updateRuns(userPhone: String, runs: Int)
    
    @Query("UPDATE players SET totalBallsFaced = totalBallsFaced + :balls WHERE userPhone = :userPhone")
    suspend fun updateBallsFaced(userPhone: String, balls: Int)
    
    @Query("UPDATE players SET totalWickets = totalWickets + :wickets WHERE userPhone = :userPhone")
    suspend fun updateWickets(userPhone: String, wickets: Int)
    
    @Query("UPDATE players SET totalCatches = totalCatches + :catches WHERE userPhone = :userPhone")
    suspend fun updateCatches(userPhone: String, catches: Int)
}
