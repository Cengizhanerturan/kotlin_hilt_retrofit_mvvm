package com.example.hiltmvvm.core.di

import com.example.hiltmvvm.core.constant.ApiConstants.Companion.BASE_URL
import com.example.hiltmvvm.core.repository.Repository
import com.example.hiltmvvm.core.service.RetrofitApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRetrofitApi(): RetrofitApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(logging).build()
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitApi::class.java)
    }

    @Singleton
    @Provides
    fun injectRepository(api: RetrofitApi): Repository {
        return Repository(api)
    }

    /*
    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context): RequestManager {
        return Glide.with(context)
            .setDefaultRequestOptions(
                RequestOptions()
                    .placeholder(R.drawable.photo_background)
                    .error(R.drawable.photo_error)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            )
    }

     */
}