package com.codeshode.passwordmanager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeshode.passwordmanager.R
import com.codeshode.passwordmanager.data.model.Password
import com.codeshode.passwordmanager.ui.theme.BlackPrimary
import com.codeshode.passwordmanager.ui.theme.BluePrimary
import com.codeshode.passwordmanager.ui.theme.BorderColor
import com.codeshode.passwordmanager.ui.theme.GreenLight
import com.codeshode.passwordmanager.ui.theme.LabelColor
import com.codeshode.passwordmanager.ui.theme.RedPrimary
import com.codeshode.passwordmanager.ui.theme.SuccessGreen
import com.codeshode.passwordmanager.ui.theme.WarningYellow
import kotlinx.coroutines.launch

@Composable
fun CustomTextField(
    value: String, onValueChange: (String) -> Unit, label: String,
    textStyle: TextStyle = TextStyle(
        fontWeight = FontWeight.W600,
        fontSize = 16.sp,
    ),
    singleLine: Boolean = true, enabled: Boolean = true,
    shape: RoundedCornerShape = RoundedCornerShape(8.dp),
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    keyboardActions: KeyboardActions = KeyboardActions(),
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = BorderColor,
        unfocusedBorderColor = BorderColor,
        disabledBorderColor = Color.Transparent,
        disabledTextColor = BlackPrimary,
        focusedTextColor = BlackPrimary,
        unfocusedTextColor = BlackPrimary,
        disabledLabelColor = LabelColor,
        focusedLabelColor = LabelColor,
        unfocusedLabelColor = LabelColor,
        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White
    ),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    supportingText: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        label = { Text(label) },
        shape = shape,
        isError = isError,
        singleLine = singleLine, enabled = enabled,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = colors,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        supportingText = supportingText
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordCard(
    password: Password,
    onClick: (Password) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .shadow(3.dp, ambientColor = Color.Black)
            .background(Color.White)
            .clickable { onClick(password) }
            .fillMaxWidth()
            .padding(24.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = password.accountType,
                style = MaterialTheme.typography.titleLarge,
                color = BlackPrimary
            )
            val mask: Char = '\u2217'
            Text(
                text = "$mask".repeat(password.password.length),
                style = MaterialTheme.typography.titleLarge,
                color = LabelColor,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentHeight()
            )
        }
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Show Details",
            modifier = Modifier.align(Alignment.CenterEnd), tint = BlackPrimary
        )

    }
}


@Composable
fun AddPasswordCard(onAddClick: (Password) -> Unit) {
    var accountName by remember {
        mutableStateOf("")
    }
    var userName by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isError by remember {
        mutableStateOf(false)
    }
    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = 28.dp, end = 28.dp, bottom = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CustomTextField(
            value = accountName,
            onValueChange = { accountName = it },
            label = "Account Name"
        )
        CustomTextField(
            value = userName,
            onValueChange = { userName = it },
            label = "Username/ Email"
        )
        CustomTextField(
            value = password,
            onValueChange = { if (it.length <= 36) password = it },
            label = "Password",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    onAddClick(
                        Password(
                            accountType = accountName,
                            username = userName,
                            password = password
                        )
                    )
                }
            ),
            supportingText = {
                if (password.isNotEmpty()) {
                    val strengthPoints = passwordStrength(password)
                    var text = ""
                    var color = Color.Unspecified
                    when (strengthPoints) {
                        0 -> {
                            text = "Very Weak"
                            color = MaterialTheme.colorScheme.error
                        }

                        1 -> {
                            text = "Weak"
                            color = MaterialTheme.colorScheme.error
                        }

                        2 -> {
                            text = "Moderate"
                            color = WarningYellow
                        }

                        3 -> {
                            text = "Strong"
                            color = GreenLight
                        }

                        4 -> {
                            text = "Very Strong"
                            color = SuccessGreen
                        }

                        5 -> {
                            text = "Excellent"
                            color = SuccessGreen
                        }

                        else -> "Unknown"
                    }
                    Text(text = text, color = color)
                }
            }
        )
        Button(
            onClick = {
                password = generatePassword(length = 16)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = BlackPrimary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                text = "Generate Password",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Button(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            onClick = {
                if (accountName.isNotEmpty() && userName.isNotEmpty() && password.isNotEmpty()) {
                    onAddClick(
                        Password(
                            accountType = accountName,
                            username = userName,
                            password = password
                        )
                    )
                } else {
                    isError = true
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = BlackPrimary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                text = "Add New Account",
                style = MaterialTheme.typography.titleMedium
            )
        }

        if (isError) {
            Text(text = "Please fill all fields", color = MaterialTheme.colorScheme.error)
        }
    }
}

fun passwordStrength(password: String): Int {
    var strengthPoints = 0
    if (password.length >= 8) {
        strengthPoints++
    }
    if (password.any { it.isUpperCase() }) {
        strengthPoints++
    }
    if (password.any { it.isLowerCase() }) {
        strengthPoints++
    }
    if (password.any { it.isDigit() }) {
        strengthPoints++
    }
    if (password.any { !it.isLetterOrDigit() }) {
        strengthPoints++
    }
    return strengthPoints
}

fun generatePassword(
    length: Int = 12,
    includeUppercase: Boolean = true,
    includeLowercase: Boolean = true,
    includeDigits: Boolean = true,
    includeSpecialChars: Boolean = true
): String {
    val uppercaseChars = ('A'..'Z').toList()
    val lowercaseChars = ('a'..'z').toList()
    val digits = ('0'..'9').toList()
    val specialChars = listOf('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '+', '=')

    val allChars = mutableListOf<Char>()
    if (includeUppercase) allChars += uppercaseChars
    if (includeLowercase) allChars += lowercaseChars
    if (includeDigits) allChars += digits
    if (includeSpecialChars) allChars += specialChars

    require(allChars.isNotEmpty()) { "At least one character set must be included" }

    return (1..length)
        .map { allChars.random() }
        .joinToString("")
}

@Composable
fun AccountDetailsCard(
    password: Password,
    onDeleteClick: (Password) -> Unit,
    onEditClick: (Password) -> Unit
) {
    var newAccountName by remember {
        mutableStateOf(password.accountType)
    }
    var newUserName by remember {
        mutableStateOf(password.username)
    }
    var newPassword by remember {
        mutableStateOf(password.password)
    }
    var editState by remember {
        mutableStateOf(false)
    }
    var isError by remember {
        mutableStateOf(false)
    }
    var passShown by remember {
        mutableStateOf(false)
    }
    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = 28.dp, end = 28.dp, bottom = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Account Details",
            style = MaterialTheme.typography.titleLarge,
            color = BluePrimary,
            modifier = Modifier.align(Alignment.Start)
        )
        CustomTextField(
            value = if (editState) newAccountName else password.accountType,
            onValueChange = { newAccountName = it }, enabled = editState,
            label = "Account Name"
        )
        CustomTextField(
            value = if (editState) newUserName else password.username,
            onValueChange = { newUserName = it }, enabled = editState,
            label = "Username/ Email"
        )
        CustomTextField(
            value = if (editState) newPassword else password.password,
            onValueChange = { if (it.length <= 36) newPassword = it }, enabled = editState,
            label = "Password",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            visualTransformation = if (passShown) VisualTransformation.None else PasswordVisualTransformation(
                '\u2217'
            ),
            supportingText = {
                if (editState && newPassword.isNotEmpty()) {
                    val strengthPoints = passwordStrength(newPassword)
                    var text = ""
                    var color = Color.Unspecified
                    when (strengthPoints) {
                        0 -> {
                            text = "Very Weak"
                            color = MaterialTheme.colorScheme.error
                        }

                        1 -> {
                            text = "Weak"
                            color = MaterialTheme.colorScheme.error
                        }

                        2 -> {
                            text = "Moderate"
                            color = WarningYellow
                        }

                        3 -> {
                            text = "Strong"
                            color = GreenLight
                        }

                        4 -> {
                            text = "Very Strong"
                            color = SuccessGreen
                        }

                        5 -> {
                            text = "Excellent"
                            color = SuccessGreen
                        }

                        else -> "Unknown"
                    }
                    Text(text = text, color = color)
                }
            }
        ) {
            IconButton(onClick = { passShown = !passShown }) {
                Icon(
                    painter = if (passShown) painterResource(id = R.drawable.baseline_visibility_24) else painterResource(
                        R.drawable.baseline_visibility_off_24
                    ),
                    contentDescription = "Show Password",
                    tint = LabelColor
                )
            }
        }
        if (editState) {
            Button(
                onClick = {
                    newPassword = generatePassword(length = 16)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BlackPrimary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "Generate Password",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(.45f)
                    .padding(top = 16.dp),
                onClick = {
                    editState = !editState
                    isError = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (editState) RedPrimary else BlackPrimary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = if (editState) "Cancel" else "Edit",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .padding(top = 16.dp),
                onClick = {
                    if (editState) {
                        if (newAccountName.isNotEmpty() && newUserName.isNotEmpty() && newPassword.isNotEmpty()) {
                            onEditClick(
                                Password(
                                    id = password.id,
                                    accountType = newAccountName,
                                    username = newUserName,
                                    password = newPassword
                                )
                            )
                        } else {
                            isError = true
                        }
                    } else {
                        onDeleteClick(password)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (editState) BlackPrimary else RedPrimary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = if (editState) "Save" else "Delete",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        if (isError && editState) {
            Text(text = "Please fill all fields", color = Color.Red)
        }
    }
}

