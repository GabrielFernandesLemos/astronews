package br.com.chicorialabs.astranovos.data.repository

import br.com.chicorialabs.astranovos.core.RemoteException
import br.com.chicorialabs.astranovos.data.model.Launch
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.data.services.SpaceFlightNewsServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


/**
 * Essa classe implementa a interface PostRepository, inicialmente
 * usando um serviço mockado. Os dados são retornados na forma de um flow.
 */

class PostRepositoryImpl(private val service: SpaceFlightNewsServices) : PostRepository {

    /**
     * Essa função usa o construtor flow { } para emitir a lista de Posts
     * na forma de um fluxo de dados. Aqui a função acessa um serviço
     * mockado. No uso real é preciso usar um bloco try-catch para
     * lidar com exceções no acesso à API.
     */
    override suspend fun listPosts(): Flow<List<Post>> = flow {

        try {
            val postList = service.listPost()
            emit(postList)
        }catch (ex: HttpException){
            throw RemoteException("Unable to connect to SpaceFlight News Api")
        }


    }
}