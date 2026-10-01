package uz.bekhzod0211.dispatcheranurtaxi.data.model.response.login


import com.google.gson.annotations.SerializedName

data class Bearer(
    @SerializedName("token")
    val token: String,
    @SerializedName("type")
    val type: String
)