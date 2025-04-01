package com.example.khelo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.khelo.data.entities.PlayerMatchStats
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerMatchStatsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayerStats(playerStats: PlayerMatchStats)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlayerStats(playerStatsList: List<PlayerMatchStats>)
    
    @Update
    suspend fun updatePlayerStats(playerStats: PlayerMatchStats)
    
    @Query("SELECT * FROM player_match_stats WHERE matchId = :matchId AND playerPhone = :playerPhone")
    suspend fun getPlayerStats(matchId: Long, playerPhone: String): PlayerMatchStats?
    
    @Query("SELECT * FROM player_match_stats WHERE matchId = :matchId AND playerPhone = :playerPhone")
    fun getPlayerStatsFlow(matchId: Long, playerPhone: String): Flow<PlayerMatchStats?>
    
    @Query("SELECT * FROM player_match_stats WHERE matchId = :matchId")
    fun getMatchPlayerStats(matchId: Long): Flow<List<PlayerMatchStats>>
    
    @Query("SELECT * FROM player_match_stats WHERE playerPhone = :playerPhone ORDER BY matchId DESC")
    fun getPlayerAllMatchStats(playerPhone: String): Flow<List<PlayerMatchStats>>
    
    @Query("UPDATE player_match_stats SET runsScored = runsScored + :runs WHERE matchId = :matchId AND playerPhone = :playerPhone")
    suspend fun updateRuns(matchId: Long, playerPhone: String, runs: Int)
    
    @Query("UPDATE player_match_stats SET ballsFaced = ballsFaced + :balls WHERE matchId = :matchId AND playerPhone = :playerPhone")
    suspend fun updateBallsFaced(matchId: Long, playerPhone: String, balls: Int)
    
    @Query("UPDATE player_match_stats SET wicketsTaken = wicketsTaken + :wickets WHERE matchId = :matchId AND playerPhone = :playerPhone")
    suspend fun updateWickets(matchId: Long, playerPhone: String, wickets: Int)
    
    @Query("UPDATE player_match_stats SET catches = catches + :catches WHERE matchId = :matchId AND playerPhone = :playerPhone")
    suspend fun updateCatches(matchId: Long, playerPhone: String, catches: Int)
    
    @Query("UPDATE player_match_stats SET ballsBowled = ballsBowled + :balls WHERE matchId = :matchId AND playerPhone = :playerPhone")
    suspend fun updateBallsBowled(matchId: Long, playerPhone: String, balls: Int)
    
    @Query("UPDATE player_match_stats SET runsConceded = runsConceded + :runs WHERE matchId = :matchId AND playerPhone = :playerPhone")
    suspend fun updateRunsConceded(matchId: Long, playerPhone: String, runs: Int)
    
    @Query("UPDATE player_match_stats SET isOut = 1, outMethod = :outMethod WHERE matchId = :matchId AND playerPhone = :playerPhone")
    suspend fun markPlayerOut(matchId: Long, playerPhone: String, outMethod: String)
}
