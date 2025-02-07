package com.example.khelo.scoring
data class Player(val id : String, var name: String, val team: String, var jerseyNo : Int, var batStat : BattingStats, var BowlStat : BowlingStats )

data class BattingStats( var runsScored : Int , var ballsFaced : Int , var fours : Int, var sixes : Int , var strikeRate : Float)
data class BowlingStats(var oversBowled : Overs, var runsConceded : Int, var economy : Float, var maidenOvers : Int)
data class Overs( var over : Int , var balls : Int)

