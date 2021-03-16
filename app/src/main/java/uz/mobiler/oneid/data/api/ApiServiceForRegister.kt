package uz.mobiler.oneid.data.api

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query
import uz.mobiler.oneid.data.models.OneIdResponce

interface ApiServiceForRegister {

    @POST("Authorization.do")
    suspend fun getAuthData(
        @Query("grant_type") grant_type: String = "one_authorization_code",
        @Query("client_id") client_id: String = "",
        @Query("client_secret") client_secret: String = "",
        @Query("code") code: String
    ): Response<OneIdResponce>

}