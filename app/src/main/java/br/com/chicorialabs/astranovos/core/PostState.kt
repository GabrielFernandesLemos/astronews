package br.com.chicorialabs.astranovos.core

import br.com.chicorialabs.astranovos.data.model.Post

sealed class PostState {
    object Loading : PostState()
    data class Success(val result: List<Post>) : PostState()
    data class Error(val error: Throwable) : PostState()
}