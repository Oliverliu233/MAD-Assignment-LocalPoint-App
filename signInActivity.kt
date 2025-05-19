package com.example.loacalpoint.activity


import android.content.Intent
import android.os.Bundle
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
import com.google.firebase.auth.FirebaseAuth
import android.util.Log
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.google.firebase.FirebaseApp


class SignInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            FirebaseApp.initializeApp(this)
            Log.d("FirebaseInit", "Firebase initialized successfully in SignInActivity.")
        } catch (e: Exception) {
            Log.e("FirebaseInit", "Firebase initialization failed in SignInActivity.", e)
        }

        // 自动登录检查逻辑
        if (FirebaseAuth.getInstance().currentUser != null) {
            Log.d("SignInActivity", "User already logged in, navigating to MainActivity.")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return // 提前返回，避免执行后续 setContent
        }
        setContent {
            val viewModel: AuthViewModel = viewModel()
            val authState by viewModel.authState.collectAsState()
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            LoacalPointTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    SignInScreen(
                        email = email,
                        password = password,
                        onEmailChange = { email = it },
                        onPasswordChange = { password = it },
                        onSignUpClick = {

                            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
                        },
                        onSignInClick = {
                            Log.d("SignInActivity", "onSignInClick triggered. Email: $email, Password non-empty: ${password.isNotBlank()}") // 新增日志
                            if (email.isBlank() || password.isBlank()) {
                                Toast.makeText(this@SignInActivity, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
                            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                Toast.makeText(this@SignInActivity, "Please enter the correct email address", Toast.LENGTH_SHORT).show()
                            } else {
                                Log.d("SignInActivity", "Calling viewModel.signInWithEmail") // 新增日志
                                viewModel.signInWithEmail(email, password)
                                Log.d("SignInActivity", "Called viewModel.signInWithEmail") // 新增日志
                            }
                        },

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
                            Toast.makeText(this@SignInActivity, "Login successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                            viewModel.resetState()
                            finish()
                        }
                        is AuthState.Error -> {
                            Toast.makeText(this@SignInActivity, (authState as AuthState.Error).message, Toast.LENGTH_SHORT).show()
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
fun SignInScreen(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit,

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(400.dp)
            )
        Text(
            text = "Hello again!",
            color = Color(0xFF38029A),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(8.dp))

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
            placeholder = { Text("Enter your password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = onSignInClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        ) {
            Text("Sign in", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Forget your password?", color = Color.Gray, fontSize = 15.sp,
            modifier = Modifier
                .align(Alignment.Start)
                )
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier
                .align(Alignment.Start)
                .fillMaxWidth(),
        ) {
            Text(text = "Don't have an account?",
                color = Color.Gray,
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(start = 2.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Sign up",
                color = Color(0xFF38029A),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.clickable { onSignUpClick() }
            )
        }

    }
}