package uz.bekhzod0211.dispatcheranurtaxi.data.domain.repository

import uz.bekhzod0211.dispatcheranurtaxi.data.model.request.main.OrderCreateRequest

interface MainRepository {
    suspend fun createOrder(order: OrderCreateRequest): Boolean
}