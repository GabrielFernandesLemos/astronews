package br.com.chicorialabs.astranovos.data.di

import android.util.Log
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import br.com.chicorialabs.astranovos.data.repository.PostRepositoryImpl
import br.com.chicorialabs.astranovos.data.services.SpaceFlightNewsService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object DataModule {

    private const val BASE_URL = "https://api.spaceflightnewsapi.net/v3/"
    private const val OK_HTTP = "Ok Http"

    fun load() {
        loadKoinModules(postsModule() + networkModule())
    }

    private fun postsModule() : Module {
        return module {
            single<PostRepository> { PostRepositoryImpl(get()) }
        }
    }

    private fun networkModule() : Module {
        return module {

            single {
                val interceptor = HttpLoggingInterceptor {
                    Log.e(OK_HTTP, it, )
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }

            single {
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            }

            single {
                createService<SpaceFlightNewsService>(get(), get())
            }

        }
    }

    private inline fun <reified T> createService(
        client: OkHttpClient,
        factory: Moshi,
    ) : T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .build()
            .create(T::class.java)
    }

}