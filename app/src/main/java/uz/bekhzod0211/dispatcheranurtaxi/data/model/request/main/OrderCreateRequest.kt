package uz.bekhzod0211.dispatcheranurtaxi.data.model.request.main

data class OrderCreateRequest(
    val broadcast: Broadcast,
    val order: Order
)