package com.jazzhipster.mycompose.presentation.login

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.*
import com.jazzhipster.mycompose.R
import com.jazzhipster.mycompose.navigation.PlayActions
import com.jazzhipster.mycompose.presentation.common.LoadingDialog
import com.jazzhipster.mycompose.presentation.common.ShowMessageDialog
import com.jazzhipster.mycompose.remote.vo.Result

import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalAnimatedInsets
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun LoginPage(actions: PlayActions) {
    val viewModel: LoginViewModel = hiltViewModel()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
            .padding(16.dp)


    ) {
        var account by remember { mutableStateOf(TextFieldValue("")) }
        var password by remember { mutableStateOf(TextFieldValue("")) }
        val loginResult by viewModel.loginResult.collectAsState(initial = Result.Initial)
        var showMsgDialog = remember {
            mutableStateOf(false)
        }
        var isLoading = remember {
            mutableStateOf(false)
        }

        if (isLoading.value) {
            LoadingDialog { isLoading.value = false }
        }
        if (showMsgDialog.value) {
            when (loginResult) {
                is Result.Error -> {
                    ShowMessageDialog("提示",
                        (loginResult as Result.Error).e.message ?: "未知錯誤", dismissDialog = {
                            showMsgDialog.value = false
                        }) {
                        showMsgDialog.value = false
                    }
                }
                is Result.Success -> {
                    ShowMessageDialog("提示",
                        "登入成功", dismissDialog = {
                            showMsgDialog.value = false
                            actions.enterHome()
                        }) {
                        showMsgDialog.value = false
                        actions.enterHome()
                    }
                }
            }

        }
        LaunchedEffect(loginResult) {
            when (loginResult) {
                Result.Loading -> {
                    isLoading.value = true
                }
                is Result.Success -> {
                    isLoading.value = false
                    showMsgDialog.value = true

                }
                is Result.Error -> {
                    isLoading.value = false
                    showMsgDialog.value = true

                }
            }
        }

        val context = LocalContext.current
        Column(
            modifier = Modifier
                .background(Color.Cyan)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black)
                    .aspectRatio(1.3f)
            )
            Spacer(modifier = Modifier.height(10.dp))
            LoginTextField(
                hintText = "Please input account",
                textFieldValue = account,
                onValueChange = { account = it },
                keyboardOptions =
                KeyboardOptions(keyboardType = KeyboardType.Text),
            )
            Spacer(modifier = Modifier.height(10.dp))
            LoginTextField(
                hintText = "Please input password",
                textFieldValue = password,
                onValueChange = { password = it },
                keyboardOptions =
                KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )
            TextButton(onClick = {
                val accountStr = account.text
                val passwordStr = password.text
                if (accountStr.isEmpty() || passwordStr.isEmpty()) {
                    Toast.makeText(context, "account or password is empty", Toast.LENGTH_LONG)
                        .show()
                    return@TextButton
                }
                viewModel.login(accountStr, passwordStr)
            }) {
                Text("Login")
            }
        }
    }
}


@ExperimentalFoundationApi
@Composable
fun LoginTextField(
    hintText: String,
    keyboardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textFieldValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .background(Color.White, shape = RoundedCornerShape(30.dp))
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(30.dp))
            .bringIntoViewRequester(bringIntoViewRequester)
            .padding(16.dp)
            ,
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = textFieldValue,
            onValueChange = { onValueChange(it) },
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                }


        ) { innerTextField ->
            if (textFieldValue.text.isEmpty())
                Text(
                    text = hintText,
                    color = Color.Gray, modifier = Modifier.alpha(0.8f)
                )
            innerTextField()
        }
    }


}