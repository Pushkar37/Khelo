package com.example.khelo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.khelo.data.entities.Match
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match: Match): Long
    
    @Update
    suspend fun updateMatch(match: Match)
    
    @Query("SELECT * FROM matches WHERE matchId = :matchId")
    suspend fun getMatchById(matchId: Long): Match?
    
    @Query("SELECT * FROM matches WHERE matchId = :matchId")
    fun getMatchByIdFlow(matchId: Long): Flow<Match?>
    
    @Query("SELECT * FROM matches ORDER BY date DESC")
    fun getAllMatches(): Flow<List<Match>>
    
    @Query("SELECT * FROM matches WHERE status = 'ongoing' ORDER BY date DESC")
    fun getLiveMatches(): Flow<List<Match>>
    
    @Query("SELECT * FROM matches WHERE createdByUserPhone = :userPhone ORDER BY date DESC")
    fun getMatchesByUser(userPhone: String): Flow<List<Match>>
    
    @Query("UPDATE matches SET status = 'completed' WHERE matchId = :matchId")
    suspend fun completeMatch(matchId: Long)
    
    @Query("UPDATE matches SET team1Score = :score WHERE matchId = :matchId")
    suspend fun updateTeam1Score(matchId: Long, score: Int)
    
    @Query("UPDATE matches SET team2Score = :score WHERE matchId = :matchId")
    suspend fun updateTeam2Score(matchId: Long, score: Int)
    
    @Query("UPDATE matches SET team1Wickets = :wickets WHERE matchId = :matchId")
    suspend fun updateTeam1Wickets(matchId: Long, wickets: Int)
    
    @Query("UPDATE matches SET team2Wickets = :wickets WHERE matchId = :matchId")
    suspend fun updateTeam2Wickets(matchId: Long, wickets: Int)
    
    @Query("UPDATE matches SET currentInnings = :innings WHERE matchId = :matchId")
    suspend fun updateCurrentInnings(matchId: Long, innings: Int)
    
    @Query("UPDATE matches SET overs = :overs WHERE matchId = :matchId")
    suspend fun updateOvers(matchId: Long, overs: Float)
}
