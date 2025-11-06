package ucne.edu.victor_ferreiras_p2_ap2.domain.mapper

import ucne.edu.victor_ferreiras_p2_ap2.data.remote.GastoRequest
import ucne.edu.victor_ferreiras_p2_ap2.data.remote.GastoResponse
import ucne.edu.victor_ferreiras_p2_ap2.domain.model.Gasto

object GastoMapper {
    fun toGasto(response: GastoResponse): Gasto {
        return Gasto(
            gastoId = response.gastoId,
            fecha = response.fecha,
            suplidor = response.suplidor,
            ncf = response.ncf,
            itbis = response.itbis,
            monto = response.monto
        )
    }

    fun toGastoRequest(gasto: Gasto): GastoRequest {
        return GastoRequest(
            fecha = gasto.fecha,
            suplidor = gasto.suplidor,
            ncf = gasto.ncf,
            itbis = gasto.itbis,
            monto = gasto.monto
        )
    }

    fun toGastoList(responses: List<GastoResponse>): List<Gasto> {
        return responses.map { toGasto(it) }
    }
}