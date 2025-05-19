package com.example.loacalpoint.viewModel

import android.util.Log // 导入 Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val TAG = "AuthViewModel"

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun signUpWithEmail(email: String, password: String) {
        Log.d(TAG, "signUpWithEmail: Attempting to sign up with email: $email")
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signUpWithEmail: Success")
                    _authState.value = AuthState.Success()
                } else {
                    Log.e(TAG, "signUpWithEmail: Failure", task.exception)
                    _authState.value = AuthState.Error(task.exception?.message ?: "Registration failed")
                }
            }
    }

    fun signInWithEmail(email: String, password: String) {
        Log.d(TAG, "signInWithEmail: Attempting to sign in with email: $email")
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail: Success")
                    _authState.value = AuthState.Success()
                } else {
                    Log.e(TAG, "signInWithEmail: Failure", task.exception)
                    _authState.value = AuthState.Error(task.exception?.message ?: "Login failed")
                }
            }
    }

    fun resetState() {
        Log.d(TAG, "resetState: Auth state reset to Idle")
        _authState.value = AuthState.Idle
    }

    fun sendPasswordResetEmail(email: String) {
        Log.d(TAG, "sendPasswordResetEmail: Attempting to send reset email to: $email")
        _authState.value = AuthState.Loading
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "sendPasswordResetEmail: Success")
                    _authState.value = AuthState.Success("The reset mail has been sent. Please check your mailbox.")
                } else {
                    Log.e(TAG, "sendPasswordResetEmail: Failure", task.exception)
                    val message = when (task.exception) {
                        is FirebaseAuthInvalidUserException -> "The Email is not registered"
                        is FirebaseAuthInvalidCredentialsException -> "The Email format is incorrect."
                        else -> task.exception?.message ?: "Reset failed"
                    }
                    _authState.value = AuthState.Error(message)
                }
            }
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val message: String = "Successful！") : AuthState()
    data class Error(val message: String) : AuthState()
}