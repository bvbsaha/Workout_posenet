package com.miguelrochefort.fitnesscamera

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.speech.tts.TextToSpeech.QUEUE_ADD
import com.miguelrochefort.fitnesscamera.lib.BodyPart
import com.miguelrochefort.fitnesscamera.lib.Person


class RepetitionCounter(context: Context) {

    val tts: TextToSpeech

    init {
        tts = TextToSpeech(context,
            OnInitListener { status ->
                if (status == TextToSpeech.SUCCESS) {

                }
            })
    }

    val MIN_AMPLITUDE = 40
    val REP_THRESHOLD = 0.8
    val MIN_CONFIDENCE = 0.5

    var count = 0

    var first = true
    var goal = 1
    var prev_y = 0
    var prev_dy = 0
    var top = 0
    var bottom = 0

    fun OnFrame(person: Person) : Int {
        //WriteToCsv(person)

        // Simple repetition count using only the nose as key point
        if (person.keyPoints[BodyPart.NOSE.ordinal].score >= MIN_CONFIDENCE) {
            var y = 1000 - person.keyPoints[BodyPart.NOSE.ordinal].position.y
            var dy = y - prev_y
            if (!first) {
                if (bottom != 0 && top != 0) {
                    if (goal == 1 && dy > 0 && (y - bottom) > (top - bottom) * REP_THRESHOLD) {
                        if (top - bottom > MIN_AMPLITUDE) {
                            count++
                            goal = -1
                            OnRep()
                        }
                    }
                    else if (goal == -1 && dy < 0 && (top - y) > (top - bottom) * REP_THRESHOLD) {
                        goal = 1
                    }
                }

                // TODO: Use MIN_AMPLITUDE
                if (dy < 0 && prev_dy >= 0 && prev_y - bottom > MIN_AMPLITUDE) {
                    top = prev_y
                }
                else if (dy > 0 && prev_dy <= 0 && top - prev_y > MIN_AMPLITUDE) {
                    bottom = prev_y
                }
            }

            first = false
            prev_y = y
            prev_dy = dy
        }

        return count
    }

    // Write frames to CSV for later analysis in Google Colab
    var csv = ""
    fun WriteToCsv(person: Person) {
        val x = System.currentTimeMillis()
        val ys = person.keyPoints.map { kp -> if (kp.score >= 0.5) kp.position.y.toString() else "" }
        val values = ys.joinToString(",")
        csv += "${x},${values}\n"
    }

    fun OnRep() {
        tts.speak(count.toString(), QUEUE_ADD, null)
    }

    fun Reset() {
        count = 0
    }
}