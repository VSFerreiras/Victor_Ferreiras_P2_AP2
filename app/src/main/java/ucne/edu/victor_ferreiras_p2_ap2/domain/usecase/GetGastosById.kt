package ucne.edu.victor_ferreiras_p2_ap2.domain

import ucne.edu.victor_ferreiras_p2_ap2.domain.model.Gasto
import ucne.edu.victor_ferreiras_p2_ap2.data.repository.GastoRepository
import javax.inject.Inject

class GetGastoById @Inject constructor(
    private val repository: GastoRepository
) {
    suspend operator fun invoke(id: Int): Gasto = repository.getGastoById(id)
}