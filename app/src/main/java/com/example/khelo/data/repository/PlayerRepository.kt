package com.example.khelo.data.repository

import com.example.khelo.data.dao.PlayerDao
import com.example.khelo.data.entities.Player
import kotlinx.coroutines.flow.Flow

class PlayerRepository(private val playerDao: PlayerDao) {
    
    suspend fun createPlayerProfile(userPhone: String): Result<Long> {
        return try {
            val player = Player(userPhone = userPhone)
            val id = playerDao.insertPlayer(player)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getPlayerByPhone(userPhone: String): Player? {
        return playerDao.getPlayerByPhone(userPhone)
    }
    
    fun getPlayerByPhoneFlow(userPhone: String): Flow<Player?> {
        return playerDao.getPlayerByPhoneFlow(userPhone)
    }
    
    fun getAllPlayers(): Flow<List<Player>> {
        return playerDao.getAllPlayers()
    }
    
    suspend fun updatePlayerStats(userPhone: String, runs: Int = 0, balls: Int = 0, wickets: Int = 0, catches: Int = 0) {
        if (runs > 0) {
            playerDao.updateRuns(userPhone, runs)
        }
        
        if (balls > 0) {
            playerDao.updateBallsFaced(userPhone, balls)
        }
        
        if (wickets > 0) {
            playerDao.updateWickets(userPhone, wickets)
        }
        
        if (catches > 0) {
            playerDao.updateCatches(userPhone, catches)
        }
    }
    
    suspend fun incrementMatchCount(userPhone: String) {
        playerDao.incrementMatchCount(userPhone)
    }
    
    suspend fun updateProfilePhoto(userPhone: String, photoPath: String) {
        val player = playerDao.getPlayerByPhone(userPhone) ?: return
        playerDao.updatePlayer(player.copy(profilePhotoPath = photoPath))
    }
}
