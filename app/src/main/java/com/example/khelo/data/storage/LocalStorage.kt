package com.example.khelo.data.storage

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.khelo.data.model.Match
import com.example.khelo.data.model.Player
import com.example.khelo.data.model.PlayerMatchStats
import com.example.khelo.data.model.User
import com.example.khelo.utils.SecurityUtils
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID

/**
 * LocalStorage handles saving and retrieving data from SharedPreferences
 */
class LocalStorage(context: Context) {
    
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME, Context.MODE_PRIVATE
    )
    
    private val json = Json { 
        ignoreUnknownKeys = true 
        isLenient = true
        prettyPrint = false
        encodeDefaults = true
    }
    
    // Current logged-in user
    private var currentUserPhone: String? = null
    
    init {
        // Initialize data structures if they don't exist
        initializeDataStructures()
    }
    
    /**
     * Initializes data structures in SharedPreferences if they don't exist
     */
    private fun initializeDataStructures() {
        Log.d("LocalStorage", "Initializing data structures")
        
        // Initialize users if not exists
        if (!sharedPreferences.contains(KEY_USERS)) {
            Log.d("LocalStorage", "Initializing users list")
            saveUsers(emptyList())
        }
        
        // Initialize players if not exists
        if (!sharedPreferences.contains(KEY_PLAYERS)) {
            Log.d("LocalStorage", "Initializing players list")
            savePlayers(emptyList())
        }
        
        // Initialize matches if not exists
        if (!sharedPreferences.contains(KEY_MATCHES)) {
            Log.d("LocalStorage", "Initializing matches list")
            saveMatches(emptyList())
        }
        
        // Initialize player match stats if not exists
        if (!sharedPreferences.contains(KEY_PLAYER_MATCH_STATS)) {
            Log.d("LocalStorage", "Initializing player match stats list")
            savePlayerMatchStats(emptyList<PlayerMatchStats>())
        }
        
        Log.d("LocalStorage", "Data structures initialized")
    }
    
    // User Management
    fun saveUser(user: User): Boolean {
        Log.d("LocalStorage", "Saving user: ${user.phoneNumber}, ${user.name}")
        val users = getUsers().toMutableList()
        
        // Check if user already exists
        val existingUserIndex = users.indexOfFirst { it.phoneNumber == user.phoneNumber }
        if (existingUserIndex != -1) {
            Log.d("LocalStorage", "Updating existing user")
            users[existingUserIndex] = user
        } else {
            Log.d("LocalStorage", "Adding new user")
            users.add(user)
        }
        
        val result = saveUsers(users)
        Log.d("LocalStorage", "Save result: $result")
        return result
    }
    
    fun getUser(phoneNumber: String): User? {
        Log.d("LocalStorage", "Getting user with phone: $phoneNumber")
        val user = getUsers().find { it.phoneNumber == phoneNumber }
        Log.d("LocalStorage", "User found: ${user != null}")
        return user
    }
    
    fun getUserByEmail(email: String): User? {
        Log.d("LocalStorage", "Getting user with email: $email")
        val user = getUsers().find { it.email == email }
        Log.d("LocalStorage", "User found: ${user != null}")
        return user
    }
    
    /**
     * Attempts to log in a user with the provided phone number and password
     * 
     * @param phoneNumber The user's phone number
     * @param password The user's raw (unhashed) password
     * @return true if login was successful, false otherwise
     */
    fun loginUser(phoneNumber: String, password: String): Boolean {
        Log.d("LocalStorage", "Attempting login for phone: $phoneNumber")
        val user = getUser(phoneNumber)
        if (user == null) {
            Log.d("LocalStorage", "User not found")
            return false
        }
        
        Log.d("LocalStorage", "User found, comparing passwords")
        // Hash the provided password and compare with stored hash
        val passwordHash = SecurityUtils.hashPassword(password)
        if (user.passwordHash != passwordHash) {
            Log.d("LocalStorage", "Password mismatch")
            return false
        }
        
        Log.d("LocalStorage", "Login successful")
        currentUserPhone = phoneNumber
        sharedPreferences.edit().putString(KEY_CURRENT_USER, phoneNumber).apply()
        return true
    }
    
    fun logoutUser() {
        currentUserPhone = null
        sharedPreferences.edit().remove(KEY_CURRENT_USER).apply()
    }
    
    fun getCurrentUser(): User? {
        val phone = currentUserPhone ?: sharedPreferences.getString(KEY_CURRENT_USER, null) ?: return null
        currentUserPhone = phone
        return getUser(phone)
    }
    
    fun isLoggedIn(): Boolean {
        return getCurrentUser() != null
    }
    
    /**
     * Returns all users for debugging purposes
     */
    fun getAllUsers(): List<User> {
        return getUsers()
    }
    
    // Player Management
    fun savePlayer(player: Player): Boolean {
        val players = getPlayers().toMutableList()
        
        // Generate ID if it's a new player
        val newPlayer = if (player.id.isEmpty()) {
            player.copy(id = UUID.randomUUID().toString())
        } else {
            player
        }
        
        // Check if player already exists
        val existingPlayerIndex = players.indexOfFirst { it.id == newPlayer.id }
        if (existingPlayerIndex != -1) {
            players[existingPlayerIndex] = newPlayer
        } else {
            players.add(newPlayer)
        }
        
        return savePlayers(players)
    }
    
    fun getPlayer(id: String): Player? {
        return getPlayers().find { it.id == id }
    }
    
    fun getPlayerByPhone(phoneNumber: String): Player? {
        return getPlayers().find { it.userPhone == phoneNumber }
    }
    
    fun updatePlayerStats(
        phoneNumber: String,
        runsToAdd: Int = 0,
        ballsFacedToAdd: Int = 0,
        wicketsToAdd: Int = 0,
        catchesToAdd: Int = 0,
        incrementMatches: Boolean = false
    ): Boolean {
        val player = getPlayerByPhone(phoneNumber) ?: return false
        
        val updatedPlayer = player.copy(
            totalRuns = player.totalRuns + runsToAdd,
            totalBallsFaced = player.totalBallsFaced + ballsFacedToAdd,
            totalWickets = player.totalWickets + wicketsToAdd,
            totalCatches = player.totalCatches + catchesToAdd,
            totalMatches = if (incrementMatches) player.totalMatches + 1 else player.totalMatches
        )
        
        return savePlayer(updatedPlayer)
    }
    
    // Match Management
    fun saveMatch(match: Match): String {
        val matches = getMatches().toMutableList()
        
        // Generate ID if it's a new match
        val newMatch = if (match.matchId.isEmpty()) {
            match.copy(matchId = UUID.randomUUID().toString())
        } else {
            match
        }
        
        // Check if match already exists
        val existingMatchIndex = matches.indexOfFirst { it.matchId == newMatch.matchId }
        if (existingMatchIndex != -1) {
            matches[existingMatchIndex] = newMatch
        } else {
            matches.add(newMatch)
        }
        
        saveMatches(matches)
        return newMatch.matchId
    }
    
    fun getMatch(id: String): Match? {
        return getMatches().find { it.matchId == id }
    }
    
    fun getMatchById(id: String): Match {
        return getMatches().find { it.matchId == id }
            ?: throw IllegalArgumentException("Match with ID $id not found")
    }
    
    fun updateMatch(match: Match): Boolean {
        val matches = getMatches().toMutableList()
        val index = matches.indexOfFirst { it.matchId == match.matchId }
        
        if (index == -1) return false
        
        matches[index] = match
        return saveMatches(matches)
    }
    
    fun getLiveMatches(): List<Match> {
        return getMatches().filter { it.status == "ongoing" }
    }
    
    fun getMatchesByUser(phoneNumber: String): List<Match> {
        return getMatches().filter { it.createdByUserPhone == phoneNumber }
    }
    
    fun updateMatchScore(
        matchId: String,
        team: String,
        runsToAdd: Int = 0,
        wicketsToAdd: Int = 0,
        oversToAdd: Float = 0f
    ): Boolean {
        val match = getMatch(matchId) ?: return false
        
        val updatedMatch = if (team == "team1") {
            match.copy(
                team1Score = match.team1Score + runsToAdd,
                team1Wickets = match.team1Wickets + wicketsToAdd,
                overs = match.overs + oversToAdd
            )
        } else {
            match.copy(
                team2Score = match.team2Score + runsToAdd,
                team2Wickets = match.team2Wickets + wicketsToAdd,
                overs = match.overs + oversToAdd
            )
        }
        
        saveMatch(updatedMatch)
        return true
    }
    
    fun completeMatch(matchId: String): Boolean {
        val match = getMatch(matchId) ?: return false
        val updatedMatch = match.copy(status = "completed")
        saveMatch(updatedMatch)
        return true
    }
    
    // Player Match Stats Management
    fun savePlayerMatchStats(stats: PlayerMatchStats): Boolean {
        val allStats = getPlayerMatchStats().toMutableList()
        
        // Check if stats already exist
        val existingStatsIndex = allStats.indexOfFirst { 
            it.matchId == stats.matchId && it.playerPhone == stats.playerPhone 
        }
        
        if (existingStatsIndex != -1) {
            allStats[existingStatsIndex] = stats
        } else {
            allStats.add(stats)
        }
        
        return savePlayerMatchStats(allStats)
    }
    
    fun getPlayerStats(matchId: String, playerPhone: String): PlayerMatchStats? {
        return getPlayerMatchStats().find { 
            it.matchId == matchId && it.playerPhone == playerPhone 
        }
    }
    
    fun getMatchPlayerStats(matchId: String): List<PlayerMatchStats> {
        return getPlayerMatchStats().filter { it.matchId == matchId }
    }
    
    fun getPlayerAllMatchStats(playerPhone: String): List<PlayerMatchStats> {
        return getPlayerMatchStats().filter { it.playerPhone == playerPhone }
    }
    
    fun updatePlayerMatchStats(
        matchId: String,
        playerPhone: String,
        runsToAdd: Int = 0,
        ballsFacedToAdd: Int = 0,
        wicketsToAdd: Int = 0,
        catchesToAdd: Int = 0,
        ballsBowledToAdd: Int = 0,
        runsConcededToAdd: Int = 0,
        isOut: Boolean = false,
        outMethod: String? = null
    ): Boolean {
        val stats = getPlayerStats(matchId, playerPhone)
        
        val updatedStats = if (stats != null) {
            stats.copy(
                runsScored = stats.runsScored + runsToAdd,
                ballsFaced = stats.ballsFaced + ballsFacedToAdd,
                wicketsTaken = stats.wicketsTaken + wicketsToAdd,
                catches = stats.catches + catchesToAdd,
                ballsBowled = stats.ballsBowled + ballsBowledToAdd,
                runsConceded = stats.runsConceded + runsConcededToAdd,
                isOut = if (isOut) true else stats.isOut,
                outMethod = outMethod ?: stats.outMethod
            )
        } else {
            PlayerMatchStats(
                matchId = matchId,
                playerPhone = playerPhone,
                team = "", // This should be set properly
                runsScored = runsToAdd,
                ballsFaced = ballsFacedToAdd,
                wicketsTaken = wicketsToAdd,
                catches = catchesToAdd,
                ballsBowled = ballsBowledToAdd,
                runsConceded = runsConcededToAdd,
                isOut = isOut,
                outMethod = outMethod
            )
        }
        
        return savePlayerMatchStats(updatedStats)
    }
    
    // Private helper methods
    private fun getUsers(): List<User> {
        val usersJson = sharedPreferences.getString(KEY_USERS, null)
        Log.d("LocalStorage", "Users JSON: ${usersJson?.take(100)}${if (usersJson?.length ?: 0 > 100) "..." else ""}")
        
        return try {
            if (usersJson.isNullOrEmpty()) {
                Log.d("LocalStorage", "No users found in SharedPreferences")
                emptyList()
            } else {
                val users = json.decodeFromString<List<User>>(usersJson)
                Log.d("LocalStorage", "Loaded ${users.size} users")
                users
            }
        } catch (e: Exception) {
            Log.e("LocalStorage", "Error parsing users JSON", e)
            emptyList()
        }
    }
    
    private fun saveUsers(users: List<User>): Boolean {
        return try {
            val usersJson = json.encodeToString(users)
            Log.d("LocalStorage", "Saving ${users.size} users, JSON length: ${usersJson.length}")
            sharedPreferences.edit().putString(KEY_USERS, usersJson).apply()
            true
        } catch (e: Exception) {
            Log.e("LocalStorage", "Error saving users", e)
            false
        }
    }
    
    private fun getPlayers(): List<Player> {
        val playersJson = sharedPreferences.getString(KEY_PLAYERS, null) ?: return emptyList()
        return try {
            json.decodeFromString(playersJson)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    private fun savePlayers(players: List<Player>): Boolean {
        return try {
            val playersJson = json.encodeToString(players)
            sharedPreferences.edit().putString(KEY_PLAYERS, playersJson).apply()
            true
        } catch (e: Exception) {
            false
        }
    }
    
    private fun getMatches(): List<Match> {
        val matchesJson = sharedPreferences.getString(KEY_MATCHES, null) ?: return emptyList()
        return try {
            json.decodeFromString(matchesJson)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    private fun saveMatches(matches: List<Match>): Boolean {
        return try {
            val matchesJson = json.encodeToString(matches)
            sharedPreferences.edit().putString(KEY_MATCHES, matchesJson).apply()
            true
        } catch (e: Exception) {
            false
        }
    }
    
    private fun getPlayerMatchStats(): List<PlayerMatchStats> {
        val statsJson = sharedPreferences.getString(KEY_PLAYER_MATCH_STATS, null) ?: return emptyList()
        return try {
            json.decodeFromString(statsJson)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    private fun savePlayerMatchStats(stats: List<PlayerMatchStats>): Boolean {
        return try {
            val statsJson = json.encodeToString(stats)
            sharedPreferences.edit().putString(KEY_PLAYER_MATCH_STATS, statsJson).apply()
            true
        } catch (e: Exception) {
            false
        }
    }
    
    companion object {
        private const val PREFS_NAME = "cricket_app_prefs"
        private const val KEY_USERS = "users"
        private const val KEY_PLAYERS = "players"
        private const val KEY_MATCHES = "matches"
        private const val KEY_PLAYER_MATCH_STATS = "player_match_stats"
        private const val KEY_CURRENT_USER = "current_user"
        
        @Volatile
        private var instance: LocalStorage? = null
        
        fun getInstance(context: Context): LocalStorage {
            return instance ?: synchronized(this) {
                instance ?: LocalStorage(context.applicationContext).also { instance = it }
            }
        }
    }
}
