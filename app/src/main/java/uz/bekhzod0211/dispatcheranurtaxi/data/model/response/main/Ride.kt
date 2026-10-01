package uz.bekhzod0211.dispatcheranurtaxi.data.model.response.main


import com.google.gson.annotations.SerializedName

data class Ride(
    @SerializedName("completed_at")
    val completedAt: Any,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("current_location")
    val currentLocation: Any,
    @SerializedName("customer")
    val customer: Any,
    @SerializedName("customer_id")
    val customerId: Int,
    @SerializedName("distance")
    val distance: Double,
    @SerializedName("driver_id")
    val driverId: Any,
    @SerializedName("dropoff_location")
    val dropoffLocation: DropoffLocation,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("fare")
    val fare: Double,
    @SerializedName("id")
    val id: Int,
    @SerializedName("pickup_location")
    val pickupLocation: PickupLocation,
    @SerializedName("rider_id")
    val riderId: Int,
    @SerializedName("route_geometry")
    val routeGeometry: RouteGeometry,
    @SerializedName("status")
    val status: String,
    @SerializedName("vehicle_type")
    val vehicleType: String
)