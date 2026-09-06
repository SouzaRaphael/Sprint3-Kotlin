package br.com.lactarehub.domain.usecase

import br.com.lactarehub.domain.entity.CollectionPoint
import br.com.lactarehub.domain.entity.CollectionPointType
import br.com.lactarehub.domain.repository.CollectionPointRepository

class ListCollectionPoints(private val repository: CollectionPointRepository) {
    suspend operator fun invoke(
        type: CollectionPointType? = null,
        onlyOpenNow: Boolean = false,
        query: String = "",
    ): List<CollectionPoint> {
        val points = repository.listPoints()
        val normalized = query.trim().lowercase()

        return points.filter { point ->
            when {
                type != null && point.type != type -> false
                onlyOpenNow && !point.isOpenNow -> false
                normalized.isEmpty() -> true
                else -> point.name.lowercase().contains(normalized) ||
                    point.neighborhood.lowercase().contains(normalized) ||
                    point.address.lowercase().contains(normalized)
            }
        }
    }
}

class GetCollectionPoint(private val repository: CollectionPointRepository) {
    suspend operator fun invoke(id: String): CollectionPoint? = repository.getById(id)
}
