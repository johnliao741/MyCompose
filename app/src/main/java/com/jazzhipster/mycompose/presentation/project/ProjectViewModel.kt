package com.jazzhipster.mycompose.presentation.project

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jazzhipster.mycompose.remote.model.BaseModel
import com.jazzhipster.mycompose.remote.model.ProjectTreeModel
import com.jazzhipster.mycompose.remote.page_source.ProjectPagingSource
import com.jazzhipster.mycompose.remote.repository.HomeArticleRepository
import com.jazzhipster.mycompose.remote.service.HomePageService
import com.jazzhipster.mycompose.remote.vo.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    val homeArticleRepository: HomeArticleRepository
) : ViewModel() {
    val treeProjectResult = MutableSharedFlow<Result<BaseModel<List<ProjectTreeModel>>>>()
    val _position = MutableSharedFlow<Int>(0)

    //LiveData
    val treeProjectLiveData = treeProjectResult.asLiveData()
    val projectsLiveData = _position.flatMapLatest {
        homeArticleRepository.getProjectPagingData(it)
    }


    fun getData() = viewModelScope.launch {
        flow {
            emit(homeArticleRepository.getProjectTree())
        }.onStart {
            treeProjectResult.emit(Result.Loading)
        }.catch {
            treeProjectResult.emit(Result.Error(it))
        }.collectLatest {
            treeProjectResult.emit(Result.Success(it))
        }
    }

    fun onPositionChanged(position: Int) = viewModelScope.launch {
        Log.e("position", position.toString())
        _position.emit(position)
    }
}