package com.codeshode.passwordmanager.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeshode.passwordmanager.data.model.Password
import com.codeshode.passwordmanager.ui.components.AccountDetailsCard
import com.codeshode.passwordmanager.ui.components.AddPasswordCard
import com.codeshode.passwordmanager.ui.screens.HomeState
import com.codeshode.passwordmanager.ui.components.CustomTextField
import com.codeshode.passwordmanager.ui.components.PasswordCard
import com.codeshode.passwordmanager.ui.theme.BackgroundColor
import com.codeshode.passwordmanager.ui.theme.BlackPrimary
import com.codeshode.passwordmanager.ui.theme.BluePrimary
import com.codeshode.passwordmanager.ui.theme.BorderColor
import com.codeshode.passwordmanager.ui.theme.HandleColor
import com.codeshode.passwordmanager.util.EncryptionUtil
import com.codeshode.passwordmanager.util.KeyStoreHelper
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val context = LocalContext.current
    Log.i("HOME_SCREEN", "HomeScreen: ${KeyStoreHelper.getKey(context)}")
    val viewModel: HomeViewModel = hiltViewModel()
    viewModel.getAllPasswords(context)
    val state by viewModel.state.collectAsState()
    val passwordList by viewModel.passwords.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var selectedPassword by remember {
        mutableStateOf<Password?>(null)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.changeState(HomeState.ADD_STATE) },
                shape = RoundedCornerShape(10.dp),
                containerColor = BluePrimary, contentColor = Color.White) {
                Icon(Icons.Filled.Add, contentDescription = "Add New Password",modifier = Modifier.size(36.dp))
            }
        },
        containerColor = BackgroundColor,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            TopAppBar(
                title = { Text("Password Manager") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundColor,
                    titleContentColor = BlackPrimary
                ))
            Divider(Modifier.fillMaxWidth(), color = Color(0xFFE8E8E8))
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(passwordList.size) { index ->
                    PasswordCard(password = passwordList[index]) {
                        selectedPassword = it
                        viewModel.changeState(HomeState.EDIT_STATE)
                    }
                }
            }

            if (state == HomeState.EDIT_STATE || state == HomeState.ADD_STATE) {
                ModalBottomSheet(
                    onDismissRequest = {
                        viewModel.changeState(HomeState.INITIAL_STATE)
                    },
                    sheetState = sheetState,
                    containerColor = BackgroundColor,
                    dragHandle = {
                        BottomSheetDefaults.DragHandle(
                            color = HandleColor,
                            width = 46.dp,
                            height = 4.dp
                        )
                    }
                ) {
                    if (state == HomeState.ADD_STATE) {
                        AddPasswordCard {
                            viewModel.addPassword(it,context)
                            coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    viewModel.changeState(HomeState.INITIAL_STATE)
                                }
                            }
                        }
                    } else {
                        AccountDetailsCard(password = selectedPassword!!,
                            onDeleteClick = {
                                viewModel.deletePassword(it.id)
                                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        viewModel.changeState(HomeState.INITIAL_STATE)
                                    }
                                }
                            },
                            onEditClick = {
                                viewModel.addPassword(it,context)
                                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        viewModel.changeState(HomeState.INITIAL_STATE)
                                    }
                                }
                            })
                    }
                }
            }
        }
    }
}