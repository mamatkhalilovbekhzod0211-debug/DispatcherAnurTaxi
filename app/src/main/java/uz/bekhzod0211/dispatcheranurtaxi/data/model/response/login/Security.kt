package uz.bekhzod0211.dispatcheranurtaxi.data.model.response.login


import com.google.gson.annotations.SerializedName

data class Security(
    @SerializedName("Bearer")
    val bearer: Bearer
)