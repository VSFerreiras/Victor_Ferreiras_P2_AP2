package ucne.edu.victor_ferreiras_p2_ap2.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("api/Gastos")
    suspend fun getAllGastos(): List<GastoResponse>

    @GET("api/Gastos/{id}")
    suspend fun getGastoById(@Path("id") id: Int): GastoResponse

    @POST("api/Gastos")
    suspend fun createGasto(@Body gastoRequest: GastoRequest): GastoResponse

    @PUT("api/Gastos/{id}")
    suspend fun updateGasto(@Path("id") id: Int, @Body gastoRequest: GastoRequest)
}

