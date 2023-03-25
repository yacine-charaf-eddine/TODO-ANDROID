package com.example.todo.data.domain

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiServiceGenerator {
    companion object{
        val INSTANCE: ApiServiceGenerator = ApiServiceGenerator()
    }

    private var toDoApiClient: ToDoClient? = null
    private var toDoRetrofit: Retrofit? = null

    private val API_BASE_TODO = "http://localhost:3001/"


    fun getToDoClient(): ToDoClient? {
        return if (toDoApiClient == null) {
            if (toDoRetrofit == null) {
                val build: Retrofit = Retrofit.Builder().baseUrl(API_BASE_TODO)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(newHttpClient()).addConverterFactory(
                        GsonConverterFactory.create(
                            GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                                .setLenient().create()
                        )
                    ).build()
                toDoRetrofit = build
                toDoApiClient = build.create(ToDoClient::class.java)
                toDoApiClient
            } else {
                toDoRetrofit!!.create(ToDoClient::class.java)
            }
        } else {
            toDoApiClient
        }
    }

    private fun newHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).build()
    }
}