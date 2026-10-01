package uz.bekhzod0211.dispatcheranurtaxi.data.model.request.main

data class Order(
    val customer_name: String,
    val customer_phone: String,
    val dropoff_location: DropoffLocation,
    val pickup_location: PickupLocation,
    val vehicle_type: String
)