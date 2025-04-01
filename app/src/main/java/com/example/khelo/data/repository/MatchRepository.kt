package com.example.khelo.data.repository

import com.example.khelo.data.dao.MatchDao
import com.example.khelo.data.entities.Match
import kotlinx.coroutines.flow.Flow

class MatchRepository(private val matchDao: MatchDao) {
    
    suspend fun createMatch(
        team1Name: String,
        team2Name: String,
        team1Captain: String,
        team1ViceCaptain: String,
        team2Captain: String,
        team2ViceCaptain: String,
        tossWinner: String,
        tossDecision: String,
        createdByUserPhone: String
    ): Result<Long> {
        return try {
            val match = Match(
                team1Name = team1Name,
                team2Name = team2Name,
                team1Captain = team1Captain,
                team1ViceCaptain = team1ViceCaptain,
                team2Captain = team2Captain,
                team2ViceCaptain = team2ViceCaptain,
                tossWinner = tossWinner,
                tossDecision = tossDecision,
                createdByUserPhone = createdByUserPhone
            )
            val id = matchDao.insertMatch(match)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getMatchById(matchId: Long): Match? {
        return matchDao.getMatchById(matchId)
    }
    
    fun getMatchByIdFlow(matchId: Long): Flow<Match?> {
        return matchDao.getMatchByIdFlow(matchId)
    }
    
    fun getAllMatches(): Flow<List<Match>> {
        return matchDao.getAllMatches()
    }
    
    fun getLiveMatches(): Flow<List<Match>> {
        return matchDao.getLiveMatches()
    }
    
    fun getMatchesByUser(userPhone: String): Flow<List<Match>> {
        return matchDao.getMatchesByUser(userPhone)
    }
    
    suspend fun completeMatch(matchId: Long) {
        matchDao.completeMatch(matchId)
    }
    
    suspend fun updateScore(matchId: Long, team: String, runs: Int, wickets: Int) {
        if (team == "team1") {
            matchDao.updateTeam1Score(matchId, runs)
            matchDao.updateTeam1Wickets(matchId, wickets)
        } else {
            matchDao.updateTeam2Score(matchId, runs)
            matchDao.updateTeam2Wickets(matchId, wickets)
        }
    }
    
    suspend fun updateInnings(matchId: Long, innings: Int) {
        matchDao.updateCurrentInnings(matchId, innings)
    }
    
    suspend fun updateOvers(matchId: Long, overs: Float) {
        matchDao.updateOvers(matchId, overs)
    }
}
