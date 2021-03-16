package uz.mobiler.oneid.data.api

import retrofit2.Response
import uz.mobiler.oneid.data.models.OneIdResponce
import javax.inject.Inject


class ApiHelperForRegister @Inject constructor(private val apiServiceForRegister: ApiServiceForRegister) {

    suspend fun getAuthData(code: String): Response<OneIdResponce> =
        apiServiceForRegister.getAuthData(code = code)

}