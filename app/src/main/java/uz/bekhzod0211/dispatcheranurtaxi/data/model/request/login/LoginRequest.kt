package uz.bekhzod0211.dispatcheranurtaxi.data.model.request.login


import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("admin_secret")
    val adminSecret: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String
)