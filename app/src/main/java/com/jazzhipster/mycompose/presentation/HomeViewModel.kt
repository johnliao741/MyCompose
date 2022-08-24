package com.jazzhipster.mycompose.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jazzhipster.mycompose.presentation.tabs.CourseTabs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() :ViewModel(){
    val _position = MutableLiveData(CourseTabs.HOME_PAGE)
    val position:LiveData<CourseTabs> = _position

    fun onPositionChanged(position:CourseTabs){
        _position.value = position
    }
}