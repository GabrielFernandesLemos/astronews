package br.com.chicorialabs.astranovos.data.services

import br.com.chicorialabs.astranovos.data.model.Post
import retrofit2.http.GET

interface SpaceFlightNewsServices {
    @GET("articles")
    suspend fun listPost() : List<Post>
}