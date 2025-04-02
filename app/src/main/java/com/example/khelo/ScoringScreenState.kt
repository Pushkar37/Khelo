package com.example.khelo

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

// Define the PrimaryGreen color
val PrimaryGreen = Color(0xFF1B5E20)

// Define the ScoringScreenState class to manage the state
class ScoringScreenState(
    val team1Players: List<String>,
    val team2Players: List<String>,
    val team1NameSafe: String,
    val team2NameSafe: String,
    val tossWinner: String?,
    val tossDecision: String?,
    val team1Captain: String,
    val team1ViceCaptain: String,
    val team2Captain: String,
    val team2ViceCaptain: String,
    initialStriker: String,
    initialNonStriker: String,
    initialBowler: String,
    initialBattingTeamName: String,
    initialBowlingTeamName: String,
    initialBattedPlayers: List<String>,
    totalOvers: Int = 20
) {
    // Match state
    var currentInnings by mutableStateOf(1)
    var target by mutableStateOf(0)
    var totalOvers by mutableStateOf(totalOvers)
    
    // Team state
    var battingTeamName by mutableStateOf(initialBattingTeamName)
    var bowlingTeamName by mutableStateOf(initialBowlingTeamName)
    
    // Score state
    var totalRuns by mutableStateOf(0)
    var totalWickets by mutableStateOf(0)
    var totalExtras by mutableStateOf(0)
    var totalWides by mutableStateOf(0)
    var totalNoBalls by mutableStateOf(0)
    var totalByes by mutableStateOf(0)
    var totalLegByes by mutableStateOf(0)
    var completedOvers by mutableStateOf(0)
    var currentBalls by mutableStateOf(0)
    var currentOverBalls by mutableStateOf(0)
    var currentOverEvents by mutableStateOf<List<String>>(emptyList())
    var runsInCurrentOver by mutableStateOf(0)
    
    // Batsmen state
    var striker by mutableStateOf(initialStriker)
    var nonStriker by mutableStateOf(initialNonStriker)
    var strikerRuns by mutableStateOf(0)
    var strikerBalls by mutableStateOf(0)
    var strikerFours by mutableStateOf(0)
    var strikerSixes by mutableStateOf(0)
    var nonStrikerRuns by mutableStateOf(0)
    var nonStrikerBalls by mutableStateOf(0)
    var nonStrikerFours by mutableStateOf(0)
    var nonStrikerSixes by mutableStateOf(0)
    
    // Bowler state
    var currentBowler by mutableStateOf(initialBowler)
    var lastBowler by mutableStateOf("")
    var bowlerOvers by mutableStateOf(0)
    var bowlerMaidens by mutableStateOf(0)
    var bowlerRuns by mutableStateOf(0)
    var bowlerWickets by mutableStateOf(0)
    
    // Player lists
    var battedPlayers by mutableStateOf(initialBattedPlayers)
    var retiredHurtBatsmen by mutableStateOf<List<String>>(emptyList())
    var retiredOutBatsmen by mutableStateOf<List<String>>(emptyList())
    
    // Extras flags
    var isWide by mutableStateOf(false)
    var isNoBall by mutableStateOf(false)
    var isBye by mutableStateOf(false)
    var isLegBye by mutableStateOf(false)
    var isWicket by mutableStateOf(false)
    var isRunOut by mutableStateOf(false)
    
    // Wicket type flags
    var isBowled by mutableStateOf(false)
    var isCaught by mutableStateOf(false)
    var isStumped by mutableStateOf(false)
    var isLBW by mutableStateOf(false)
    var isHitWicket by mutableStateOf(false)
    var fielderName by mutableStateOf("")
    
    // Dialog states
    var showNewBatsmanDialog by mutableStateOf(false)
    var showNewBowlerDialog by mutableStateOf(false)
    var showInningsCompleteDialog by mutableStateOf(false)
    var showMatchCompleteDialog by mutableStateOf(false)
    var showRetireBatsmanDialog by mutableStateOf(false)
    var showRetireTypeDialog by mutableStateOf(false)
    var showWicketTypeDialog by mutableStateOf(false)
    var showScorecardDialog by mutableStateOf(false)
    var newBatsmanName by mutableStateOf("")
    var newBowlerName by mutableStateOf("")
    var retireBatsmanName by mutableStateOf("")
    var isRetiredHurt by mutableStateOf(false)
    
    // Player stats maps
    var batsmenStats by mutableStateOf<Map<String, BatsmanStats>>(mutableMapOf())
    var bowlersStats by mutableStateOf<Map<String, BowlerStats>>(mutableMapOf())
    var bowlersList by mutableStateOf<List<String>>(emptyList())
    
    // History for undo
    private var stateHistory by mutableStateOf<List<StateSnapshot>>(emptyList())
    
    // Save current state to history for undo
    fun saveToHistory() {
        val snapshot = StateSnapshot(
            totalRuns = totalRuns,
            totalWickets = totalWickets,
            totalExtras = totalExtras,
            totalWides = totalWides,
            totalNoBalls = totalNoBalls,
            totalByes = totalByes,
            totalLegByes = totalLegByes,
            completedOvers = completedOvers,
            currentBalls = currentBalls,
            currentOverBalls = currentOverBalls,
            currentOverEvents = currentOverEvents,
            runsInCurrentOver = runsInCurrentOver,
            strikerName = striker,
            nonStrikerName = nonStriker,
            strikerRuns = strikerRuns,
            nonStrikerRuns = nonStrikerRuns,
            strikerBalls = strikerBalls,
            nonStrikerBalls = nonStrikerBalls,
            strikerFours = strikerFours,
            nonStrikerFours = nonStrikerFours,
            strikerSixes = strikerSixes,
            nonStrikerSixes = nonStrikerSixes,
            bowlerName = currentBowler,
            bowlerOvers = bowlerOvers,
            bowlerMaidens = bowlerMaidens,
            bowlerRuns = bowlerRuns,
            bowlerWickets = bowlerWickets,
            batsmenStats = batsmenStats,
            bowlersStats = bowlersStats,
            battedPlayers = battedPlayers
        )
        
        // Add to history, limit to last 20 states
        stateHistory = (stateHistory + snapshot).takeLast(20)
    }
    
    // Undo last action
    fun undoLastAction() {
        if (stateHistory.isNotEmpty()) {
            val lastState = stateHistory.last()
            
            // Restore state from snapshot
            totalRuns = lastState.totalRuns
            totalWickets = lastState.totalWickets
            totalExtras = lastState.totalExtras
            totalWides = lastState.totalWides
            totalNoBalls = lastState.totalNoBalls
            totalByes = lastState.totalByes
            totalLegByes = lastState.totalLegByes
            completedOvers = lastState.completedOvers
            currentBalls = lastState.currentBalls
            currentOverBalls = lastState.currentOverBalls
            currentOverEvents = lastState.currentOverEvents
            runsInCurrentOver = lastState.runsInCurrentOver
            striker = lastState.strikerName
            nonStriker = lastState.nonStrikerName
            strikerRuns = lastState.strikerRuns
            nonStrikerRuns = lastState.nonStrikerRuns
            strikerBalls = lastState.strikerBalls
            nonStrikerBalls = lastState.nonStrikerBalls
            strikerFours = lastState.strikerFours
            nonStrikerFours = lastState.nonStrikerFours
            strikerSixes = lastState.strikerSixes
            nonStrikerSixes = lastState.nonStrikerSixes
            currentBowler = lastState.bowlerName
            bowlerOvers = lastState.bowlerOvers
            bowlerMaidens = lastState.bowlerMaidens
            bowlerRuns = lastState.bowlerRuns
            bowlerWickets = lastState.bowlerWickets
            batsmenStats = lastState.batsmenStats
            bowlersStats = lastState.bowlersStats
            battedPlayers = lastState.battedPlayers
            
            // Remove the used state from history
            stateHistory = stateHistory.dropLast(1)
        }
    }
    
    // Ball history for undo functionality
    data class BallHistory(
        val runs: Int,
        val isWide: Boolean,
        val isNoBall: Boolean,
        val isBye: Boolean,
        val isLegBye: Boolean,
        val isWicket: Boolean,
        val isRunOut: Boolean,
        val striker: String,
        val nonStriker: String,
        val strikerRuns: Int,
        val strikerBalls: Int,
        val strikerFours: Int,
        val strikerSixes: Int,
        val nonStrikerRuns: Int,
        val nonStrikerBalls: Int,
        val nonStrikerFours: Int,
        val nonStrikerSixes: Int,
        val totalRuns: Int,
        val totalWickets: Int,
        val totalExtras: Int,
        val totalWides: Int,
        val totalNoBalls: Int,
        val totalByes: Int,
        val totalLegByes: Int,
        val completedOvers: Int,
        val currentBalls: Int,
        val currentOverBalls: Int,
        val runsInCurrentOver: Int,
        val currentOverEvents: List<String>,
        val bowlerRuns: Int,
        val bowlerWickets: Int
    )
    
    private var ballHistory = mutableStateListOf<BallHistory>()
    
    // Save current state to ball history
    private fun saveBallHistory() {
        ballHistory.add(
            BallHistory(
                runs = 0, // Not storing the actual runs as it's already processed
                isWide = isWide,
                isNoBall = isNoBall,
                isBye = isBye,
                isLegBye = isLegBye,
                isWicket = isWicket,
                isRunOut = isRunOut,
                striker = striker,
                nonStriker = nonStriker,
                strikerRuns = strikerRuns,
                strikerBalls = strikerBalls,
                strikerFours = strikerFours,
                strikerSixes = strikerSixes,
                nonStrikerRuns = nonStrikerRuns,
                nonStrikerBalls = nonStrikerBalls,
                nonStrikerFours = nonStrikerFours,
                nonStrikerSixes = nonStrikerSixes,
                totalRuns = totalRuns,
                totalWickets = totalWickets,
                totalExtras = totalExtras,
                totalWides = totalWides,
                totalNoBalls = totalNoBalls,
                totalByes = totalByes,
                totalLegByes = totalLegByes,
                completedOvers = completedOvers,
                currentBalls = currentBalls,
                currentOverBalls = currentOverBalls,
                runsInCurrentOver = runsInCurrentOver,
                currentOverEvents = currentOverEvents,
                bowlerRuns = bowlerRuns,
                bowlerWickets = bowlerWickets
            )
        )
    }
    
    // Undo last ball
    fun undoLastBall(): Boolean {
        if (ballHistory.isEmpty()) return false
        
        val lastState = ballHistory.removeAt(ballHistory.size - 1)
        
        // Restore state
        isWide = lastState.isWide
        isNoBall = lastState.isNoBall
        isBye = lastState.isBye
        isLegBye = lastState.isLegBye
        isWicket = lastState.isWicket
        isRunOut = lastState.isRunOut
        striker = lastState.striker
        nonStriker = lastState.nonStriker
        strikerRuns = lastState.strikerRuns
        strikerBalls = lastState.strikerBalls
        strikerFours = lastState.strikerFours
        strikerSixes = lastState.strikerSixes
        nonStrikerRuns = lastState.nonStrikerRuns
        nonStrikerBalls = lastState.nonStrikerBalls
        nonStrikerFours = lastState.nonStrikerFours
        nonStrikerSixes = lastState.nonStrikerSixes
        totalRuns = lastState.totalRuns
        totalWickets = lastState.totalWickets
        totalExtras = lastState.totalExtras
        totalWides = lastState.totalWides
        totalNoBalls = lastState.totalNoBalls
        totalByes = lastState.totalByes
        totalLegByes = lastState.totalLegByes
        completedOvers = lastState.completedOvers
        currentBalls = lastState.currentBalls
        currentOverBalls = lastState.currentOverBalls
        runsInCurrentOver = lastState.runsInCurrentOver
        currentOverEvents = lastState.currentOverEvents
        bowlerRuns = lastState.bowlerRuns
        bowlerWickets = lastState.bowlerWickets
        
        return true
    }
    
    // Calculate run rate
    fun getRunRate(): Float {
        val totalOvers = completedOvers + (currentBalls / 6.0f)
        return if (totalOvers > 0) totalRuns / totalOvers else 0.0f
    }
    
    // Format run rate to 2 decimal places
    fun getFormattedRunRate(): String {
        return String.format("%.2f", getRunRate())
    }
    
    // Calculate required run rate for second innings
    fun getRequiredRunRate(): Float {
        if (currentInnings != 2 || target <= 0) return 0.0f
        
        val remainingRuns = target - totalRuns
        val remainingOvers = totalOvers - completedOvers - (currentBalls / 6.0f)
        
        return if (remainingOvers > 0) remainingRuns / remainingOvers else 999.99f
    }
    
    // Format required run rate to 2 decimal places
    fun getFormattedRequiredRunRate(): String {
        return String.format("%.2f", getRequiredRunRate())
    }
    
    // Calculate projected score
    fun getProjectedScore(): Int {
        val currentRunRate = getRunRate()
        val remainingOvers = totalOvers - completedOvers - (currentBalls / 6.0f)
        val projectedAdditionalRuns = (currentRunRate * remainingOvers).toInt()
        return totalRuns + projectedAdditionalRuns
    }
    
    // Process ball outcome
    fun processBallOutcome(runs: Int) {
        // Save current state for undo
        saveBallHistory()
        
        val isExtra = isWide || isNoBall
        val isBoundary = runs == 4 || runs == 6
        
        // Check for wicket
        if (isWicket) {
            processWicket()
            return
        }
        
        // Update runs and extras
        if (isWide) {
            // Wide: 1 run + any additional runs
            totalRuns += runs + 1
            totalExtras += runs + 1
            totalWides += runs + 1
            runsInCurrentOver += runs + 1
            
            // Add to current over events
            val eventText = if (runs > 0) "${runs}Wd" else "Wd"
            currentOverEvents = currentOverEvents + eventText
            
            // Update bowler stats
            bowlerRuns += runs + 1
        } else if (isNoBall) {
            // No ball: 1 run + any additional runs
            totalRuns += runs + 1
            totalExtras += 1
            totalNoBalls += 1
            runsInCurrentOver += runs + 1
            
            // Add to current over events
            val eventText = if (runs > 0) "${runs}Nb" else "Nb"
            currentOverEvents = currentOverEvents + eventText
            
            // Update bowler stats
            bowlerRuns += runs + 1
            
            // Update batsman stats if runs were scored (not counted as a ball faced)
            if (runs > 0 && !isBye && !isLegBye) {
                strikerRuns += runs
                if (runs == 4) strikerFours++
                if (runs == 6) strikerSixes++
            }
        } else if (isBye) {
            // Byes: runs count to total but not to batsman
            totalRuns += runs
            totalExtras += runs
            totalByes += runs
            runsInCurrentOver += runs
            
            // Add to current over events
            currentOverEvents = currentOverEvents + "${runs}b"
            
            // Update batsman stats (ball faced but no runs)
            strikerBalls++
            
            // Increment balls in current over
            currentBalls++
            currentOverBalls++
        } else if (isLegBye) {
            // Leg byes: runs count to total but not to batsman
            totalRuns += runs
            totalExtras += runs
            totalLegByes += runs
            runsInCurrentOver += runs
            
            // Add to current over events
            currentOverEvents = currentOverEvents + "${runs}lb"
            
            // Update batsman stats (ball faced but no runs)
            strikerBalls++
            
            // Increment balls in current over
            currentBalls++
            currentOverBalls++
        } else {
            // Normal delivery
            totalRuns += runs
            runsInCurrentOver += runs
            
            // Add to current over events
            currentOverEvents = currentOverEvents + runs.toString()
            
            // Update bowler stats
            bowlerRuns += runs
            
            // Update batsman stats
            strikerRuns += runs
            strikerBalls++
            if (runs == 4) strikerFours++
            if (runs == 6) strikerSixes++
            
            // Increment balls in current over
            currentBalls++
            currentOverBalls++
        }
        
        // Check for over completion
        if (!isExtra && currentOverBalls >= 6) {
            completeOver()
        }
        
        // Switch striker for odd runs (except on last ball of over which is handled in completeOver)
        if (runs % 2 == 1 && !(currentOverBalls == 6 && !isExtra)) {
            switchStriker()
        }
        
        // Check for innings/match completion
        checkForInningsCompletion()
        
        // Reset extras flags
        isWide = false
        isNoBall = false
        isBye = false
        isLegBye = false
    }
    
    // Process wicket
    fun processWicket() {
        // Save current state for undo
        saveBallHistory()
        
        // Increment wickets
        totalWickets++
        
        // Update bowler stats if not a run out and not a wide
        if (!isRunOut && !isWide) {
            bowlerWickets++
        }
        
        // Add to current over events
        currentOverEvents = currentOverEvents + "W"
        
        // Increment balls in current over if not a wide or no ball
        if (!isWide && !isNoBall) {
            currentBalls++
            currentOverBalls++
            strikerBalls++
        }
        
        // Check for over completion
        if (!isWide && !isNoBall && currentOverBalls >= 6) {
            completeOver()
        }
        
        // Check for innings completion
        if (totalWickets >= 10) {
            if (currentInnings == 1) {
                showInningsCompleteDialog = true
            } else {
                showMatchCompleteDialog = true
            }
        } else {
            // Show dialog to select new batsman
            showNewBatsmanDialog = true
        }
        
        // Reset extras flags
        isWide = false
        isNoBall = false
        isBye = false
        isLegBye = false
        isWicket = false
        isRunOut = false
        isBowled = false
        isCaught = false
        isStumped = false
        isLBW = false
        isHitWicket = false
        fielderName = ""
    }
    
    // Complete over
    fun completeOver() {
        // Update overs
        completedOvers++
        currentBalls = 0
        currentOverBalls = 0
        
        // Update bowler stats
        bowlerOvers++
        
        // Check for maiden over
        if (runsInCurrentOver == 0) {
            bowlerMaidens++
        }
        
        // Reset current over events and runs
        currentOverEvents = emptyList()
        runsInCurrentOver = 0
        
        // Switch striker
        switchStriker()
        
        // Set last bowler and show dialog to select new bowler
        lastBowler = currentBowler
        showNewBowlerDialog = true
        
        // Check for innings completion
        checkForInningsCompletion()
    }
    
    // Switch striker and non-striker
    fun switchStriker() {
        val tempName = striker
        striker = nonStriker
        nonStriker = tempName
        
        val tempRuns = strikerRuns
        strikerRuns = nonStrikerRuns
        nonStrikerRuns = tempRuns
        
        val tempBalls = strikerBalls
        strikerBalls = nonStrikerBalls
        nonStrikerBalls = tempBalls
        
        val tempFours = strikerFours
        strikerFours = nonStrikerFours
        nonStrikerFours = tempFours
        
        val tempSixes = strikerSixes
        strikerSixes = nonStrikerSixes
        nonStrikerSixes = tempSixes
    }
    
    // Check for innings completion
    private fun checkForInningsCompletion() {
        // Check if first innings is complete (all out or completed overs)
        if (currentInnings == 1 && (totalWickets >= 10 || completedOvers >= totalOvers)) {
            showInningsCompleteDialog = true
        }
        // Check if second innings is complete (all out, completed overs, or target achieved)
        else if (currentInnings == 2 && (totalWickets >= 10 || completedOvers >= totalOvers || totalRuns > target)) {
            showMatchCompleteDialog = true
        }
    }
    
    // Start second innings
    fun startSecondInnings() {
        // Switch batting and bowling teams
        val tempTeamName = battingTeamName
        battingTeamName = bowlingTeamName
        bowlingTeamName = tempTeamName
        
        // Set target
        target = totalRuns + 1
        
        // Reset innings variables
        totalRuns = 0
        totalWickets = 0
        totalExtras = 0
        totalWides = 0
        totalNoBalls = 0
        totalByes = 0
        totalLegByes = 0
        completedOvers = 0
        currentBalls = 0
        currentOverBalls = 0
        currentOverEvents = emptyList()
        runsInCurrentOver = 0
        
        // Reset batsmen
        striker = ""
        nonStriker = ""
        strikerRuns = 0
        strikerBalls = 0
        strikerFours = 0
        strikerSixes = 0
        nonStrikerRuns = 0
        nonStrikerBalls = 0
        nonStrikerFours = 0
        nonStrikerSixes = 0
        
        // Reset bowler
        currentBowler = ""
        lastBowler = ""
        bowlerOvers = 0
        bowlerMaidens = 0
        bowlerRuns = 0
        bowlerWickets = 0
        
        // Reset player lists
        battedPlayers = emptyList()
        retiredHurtBatsmen = emptyList()
        retiredOutBatsmen = emptyList()
        
        // Update innings
        currentInnings = 2
    }
    
    // Initialize a new batsman
    fun initializeBatsman(name: String) {
        if (!batsmenStats.containsKey(name)) {
            val newStats = BatsmanStats(name)
            batsmenStats = batsmenStats + (name to newStats)
        }
    }
    
    // Initialize a new bowler
    fun initializeBowler(name: String) {
        if (!bowlersStats.containsKey(name)) {
            val newStats = BowlerStats(name)
            bowlersStats = bowlersStats + (name to newStats)
            bowlersList = bowlersList + name
        }
    }
    
    // Get batsman stats
    fun getBatsmanStats(name: String): BatsmanStats {
        return batsmenStats[name] ?: BatsmanStats(name)
    }
    
    // Get bowler stats
    fun getBowlerStats(name: String): BowlerStats {
        return bowlersStats[name] ?: BowlerStats(name)
    }
}

// Batsman stats data class
data class BatsmanStats(
    val name: String,
    var runs: Int = 0,
    var balls: Int = 0,
    var fours: Int = 0,
    var sixes: Int = 0,
    var isOut: Boolean = false,
    var howOut: String = "",
    var bowler: String = "",
    var fielder: String = ""
)

// Bowler stats data class
data class BowlerStats(
    val name: String,
    var overs: Int = 0,
    var maidens: Int = 0,
    var runs: Int = 0,
    var wickets: Int = 0
)

// State snapshot for undo functionality
data class StateSnapshot(
    val totalRuns: Int,
    val totalWickets: Int,
    val totalExtras: Int,
    val totalWides: Int,
    val totalNoBalls: Int,
    val totalByes: Int,
    val totalLegByes: Int,
    val completedOvers: Int,
    val currentBalls: Int,
    val currentOverBalls: Int,
    val currentOverEvents: List<String>,
    val runsInCurrentOver: Int,
    val strikerName: String,
    val nonStrikerName: String,
    val strikerRuns: Int,
    val nonStrikerRuns: Int,
    val strikerBalls: Int,
    val nonStrikerBalls: Int,
    val strikerFours: Int,
    val nonStrikerFours: Int,
    val strikerSixes: Int,
    val nonStrikerSixes: Int,
    val bowlerName: String,
    val bowlerOvers: Int,
    val bowlerMaidens: Int,
    val bowlerRuns: Int,
    val bowlerWickets: Int,
    val batsmenStats: Map<String, BatsmanStats>,
    val bowlersStats: Map<String, BowlerStats>,
    val battedPlayers: List<String>
)

// Create a CompositionLocal for ScoringScreenState
val LocalScoringScreenState = compositionLocalOf<ScoringScreenState> { 
    error("No ScoringScreenState provided") 
}
