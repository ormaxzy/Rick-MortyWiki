package com.example.rickandmortywiki.api

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("/api/character")
    fun loadList(@Query("page") page: Int): Call<Response>

    @GET("/api/location")
    fun loadLocationList(@Query("page") page: Int): Call<LocationResponse>

    companion object {
        const val PAGE_SIZE = 20

        private val interceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        private val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit by lazy {
            Retrofit
                .Builder()
                .baseUrl("https://rickandmortyapi.com")
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create<Api>()
        }
    }
}

@JsonClass(generateAdapter = true)
data class LocationResponse(
    @Json(name = "info") val info: Info,
    @Json(name = "results") val results: List<LocationItem>
)

@JsonClass(generateAdapter = true)
data class Info(
    @Json(name = "count") val count: Int,
    @Json(name = "pages") val pages: Int,
    @Json(name = "next") val next: String?,
    @Json(name = "prev") val prev: String?
)

@JsonClass(generateAdapter = true)
data class LocationItem(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "type") val type: String,
    @Json(name = "dimension") val dimension: String,
    @Json(name = "residents") val residents: List<String>,
    @Json(name = "url") val url: String,
    @Json(name = "created") val created: String
)

@JsonClass(generateAdapter = true)
data class Response(
    @Json(name = "results") val results: List<Character>
)

@Parcelize
@JsonClass(generateAdapter = true)
data class Character(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "status") val status: String,
    @Json(name = "species") val species: String,
    @Json(name = "gender") val gender: String,
    @Json(name = "location") val location: Location,
    @Json(name = "image") val image: String,
    @Json(name = "episode") val episode: List<String>

) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String,
) : Parcelable