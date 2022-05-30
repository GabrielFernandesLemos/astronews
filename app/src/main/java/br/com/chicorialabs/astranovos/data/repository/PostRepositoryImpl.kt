package br.com.chicorialabs.astranovos.data.repository

import br.com.chicorialabs.astranovos.core.RemoteException
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.data.services.SpaceFlightNewsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class PostRepositoryImpl(private val service: SpaceFlightNewsService) : PostRepository {

    override suspend fun listPosts(): Flow<List<Post>> = flow {

        try {
            val postList = service.listPosts()
            emit(postList)
        } catch (ex: HttpException) {
            throw RemoteException("Unable to connect to SpaceFlight News API")
        }

    }
}