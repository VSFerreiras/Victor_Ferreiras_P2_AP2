package ucne.edu.victor_ferreiras_p2_ap2.data.repository

import ucne.edu.victor_ferreiras_p2_ap2.data.remote.ApiService
import ucne.edu.victor_ferreiras_p2_ap2.data.remote.GastoRequest
import ucne.edu.victor_ferreiras_p2_ap2.domain.mapper.GastoMapper
import ucne.edu.victor_ferreiras_p2_ap2.domain.model.Gasto
import javax.inject.Inject

class GastoRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getAllGastos(): List<Gasto> {
        val responses = apiService.getAllGastos()
        return GastoMapper.toGastoList(responses)
    }

    suspend fun getGastoById(id: Int): Gasto {
        val response = apiService.getGastoById(id)
        return GastoMapper.toGasto(response)
    }

    suspend fun createGasto(gasto: Gasto): Gasto {
        val request = GastoMapper.toGastoRequest(gasto)
        val response = apiService.createGasto(request)
        return GastoMapper.toGasto(response)
    }

    suspend fun updateGasto(id: Int, gasto: Gasto) {
        val request = GastoMapper.toGastoRequest(gasto)
        apiService.updateGasto(id, request)
    }
}