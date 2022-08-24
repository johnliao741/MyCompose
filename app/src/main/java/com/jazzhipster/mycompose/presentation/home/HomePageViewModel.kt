package com.jazzhipster.mycompose.presentation.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jazzhipster.mycompose.remote.model.ArticleModel
import com.jazzhipster.mycompose.remote.model.BannerBean
import com.jazzhipster.mycompose.remote.repository.HomeArticleRepository
import com.jazzhipster.mycompose.remote.vo.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomePageViewModel @Inject constructor(
    val homeArticleRepository: HomeArticleRepository
) : ViewModel() {
    val _getBannerResult = MutableSharedFlow<Result<List<BannerBean>>>()
    val _searchPageFlow = MutableSharedFlow<Int>()
    //LiveData
    val getBannerLiveData = _getBannerResult.asLiveData()
    val isRefreshing = MutableLiveData<Boolean>(false)
    var getArticleResult = _searchPageFlow.flatMapLatest {
        homeArticleRepository.getPagingData(it)
    }

    var searchJob :Job? = null
    var bannerJob:Job? = null

    fun getData(){
        getBanner()
        searchArticle()
    }
    fun searchArticle(page:Int = 0){
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _searchPageFlow.emit(page)
        }
    }
    fun getBanner(){
        bannerJob?.cancel()
        bannerJob = viewModelScope.launch {
            _getBannerResult.emit(Result.Loading)
            flow {
                emit(homeArticleRepository.getBanner())
            }.catch {
                _getBannerResult.emit(Result.Error(it))
            }.collect{
                _getBannerResult.emit(Result.Success(it.data))
            }
        }
    }

    fun delete(articleModel: ArticleModel) {
        getArticleResult = getArticleResult
    }
}