package com.bvbsaha.fitnesskursach.model

import androidx.lifecycle.*
import com.bvbsaha.fitnesskursach.util.ChallengeEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(private val leaderBoardUseCase: LeaderBoardUseCase) : ViewModel() {



    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _type = MutableLiveData<String?>()
    val type: LiveData<String?>
        get() = _type



    fun setScore(score: Int) {
        _score.value = score
    }

    fun setType(type: String) {
        _type.value = type
    }

    init {
        _type.value = ChallengeEnum.SQUAT.challengeName
        _score.value = 0
    }


}