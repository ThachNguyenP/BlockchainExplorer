package com.example.blockexplorer

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.text.SimpleDateFormat
import java.util.*

object ApiHelper {

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    val retrofit by lazy { Retrofit.Builder()
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl("https://blockstream.info/api/")
        .build() }

    fun getDate(timestamp: Int) :String {
        val calendar = Calendar.getInstance(Locale.US)
        calendar.timeInMillis = timestamp * 1000L
        val simpleFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US)
        return simpleFormat.format(calendar.time)
    }

}