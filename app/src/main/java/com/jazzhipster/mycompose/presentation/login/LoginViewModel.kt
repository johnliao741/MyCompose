package com.jazzhipster.mycompose.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jazzhipster.mycompose.remote.model.BaseModel
import com.jazzhipster.mycompose.remote.model.LoginRequest
import com.jazzhipster.mycompose.remote.repository.HomeArticleRepository
import com.jazzhipster.mycompose.remote.vo.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val homeArticleRepository: HomeArticleRepository
):ViewModel(){
    val loginResult = MutableSharedFlow<Result<BaseModel<Unit>>>()
    val loginLiveData = loginResult.asLiveData()
    fun login(account: String, password: String) =viewModelScope.launch {
        loginResult.emit(Result.Loading)
        flow {
            emit(homeArticleRepository.login(LoginRequest(account, password)))
        }.catch {
            loginResult.emit(Result.Error(it))
        }.collectLatest {
            loginResult.emit(Result.Success(it))
        }

    }

}