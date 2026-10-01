package uz.bekhzod0211.dispatcheranurtaxi.data.model.response.login


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_admin")
    val isAdmin: Boolean,
    @SerializedName("is_approved")
    val isApproved: Boolean,
    @SerializedName("is_dispatcher")
    val isDispatcher: Boolean,
    @SerializedName("is_driver")
    val isDriver: Boolean,
    @SerializedName("phone")
    val phone: String
)