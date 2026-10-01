package uz.bekhzod0211.dispatcheranurtaxi.data.model.request.main

data class DropoffLocation(
    val address: String,
    val city: String,
    val lat: Double,
    val lng: Double
)