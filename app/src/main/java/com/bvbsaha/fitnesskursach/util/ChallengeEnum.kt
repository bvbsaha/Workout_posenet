package com.bvbsaha.fitnesskursach.util

enum class ChallengeEnum(val challengeName: String = "Squatting") {

    SQUAT("Squatting");


    companion object {

        fun getAllExercises(): List<String> {
            val arrays = mutableListOf<String>()
            for (challenge in values()) {
                arrays.add(challenge.challengeName)
            }
            return arrays
        }

    }
}

