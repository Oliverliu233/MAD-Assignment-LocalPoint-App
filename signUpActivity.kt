package com.example.loacalpoint.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log // 确保导入 Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loacalpoint.R
import com.example.loacalpoint.ui.theme.LoacalPointTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import com.example.loacalpoint.viewModel.AuthViewModel
import com.example.loacalpoint.viewModel.AuthState
import android.widget.Toast

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: AuthViewModel = viewModel()
            val authState by viewModel.authState.collectAsState()
            var name by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            LoacalPointTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    SignUpScreen(
                        name = name,
                        email = email,
                        password = password,
                        onNameChange = { name = it },
                        onEmailChange = { email = it },
                        onPasswordChange = { password = it },
                        onSignInClick = { finish() },
                        onCreateAccountClick = {
                            Log.d("SignUpActivity", "onCreateAccountClick triggered. Email: $email, Name: $name, Password non-empty: ${password.isNotBlank()}") // 新增日志
                            if (email.isBlank() || password.isBlank() || name.isBlank()) {
                                Toast.makeText(this@SignUpActivity, "All fields cannot be blank", Toast.LENGTH_SHORT).show()
                            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                Toast.makeText(this@SignUpActivity, "Please enter the correct email address", Toast.LENGTH_SHORT).show()
                            } else if (password.length < 6) {
                                Toast.makeText(this@SignUpActivity, "The password must not be less than 6 digits", Toast.LENGTH_SHORT).show()
                            } else {
                                Log.d("SignUpActivity", "Calling viewModel.signUpWithEmail")
                                viewModel.signUpWithEmail(email, password)
                                Log.d("SignUpActivity", "Called viewModel.signUpWithEmail")
                            }
                        }
                    )
                    when (authState) {
                        is AuthState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        is AuthState.Success -> {
                            Toast.makeText(this@SignUpActivity, "registered successfully", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                            viewModel.resetState()
                            finish()
                        }
                        is AuthState.Error -> {
                            Toast.makeText(this@SignUpActivity, (authState as AuthState.Error).message, Toast.LENGTH_SHORT).show()
                            viewModel.resetState()
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}

@Composable
fun SignUpScreen(
    name: String,
    email: String,
    password: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onCreateAccountClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onSignInClick() }
                    .padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(130.dp)
                    .padding(end = 1.dp)

            )

        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "WELCOME!",
            color = Color(0xFFE53935),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.Start)
        )
        Text(
            text = "Sign up",
            color = Color(0xFFE53935),
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 4.dp, bottom = 38.dp)
        )
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Name") },
            placeholder = { Text("Enter your name") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            placeholder = { Text("Enter your email") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            placeholder = { Text("Choose a password (Min. 8 characters)") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = onCreateAccountClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        ) {
            Text("Create account", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp) // 按钮文字字号
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .align(Alignment.Start)
                .fillMaxWidth(),

        ) {
            Text(text = "Already have an account?", color = Color.Gray, fontSize = 15.sp) // 字体大小
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Sign in",
                color = Color(0xFF38029A),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.clickable { onSignInClick() }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

 