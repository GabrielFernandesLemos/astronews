package br.com.chicorialabs.astranovos.data.repository

import br.com.chicorialabs.astranovos.data.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun listPosts() : Flow<List<Post>>
}