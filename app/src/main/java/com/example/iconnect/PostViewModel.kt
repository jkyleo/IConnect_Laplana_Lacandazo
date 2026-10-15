package com.example.iconnect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PostUiState())

    val uiState: StateFlow<PostUiState> =
        _uiState.asStateFlow()

    fun getPost(postId: Int) {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            try {
                val result = RetrofitInstance.api.getPost(postId)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        post = result
                    )
                }

            } catch (e: Exception) {

                e.printStackTrace()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unable to retrieve data."
                    )
                }
            }
        }
    }
}