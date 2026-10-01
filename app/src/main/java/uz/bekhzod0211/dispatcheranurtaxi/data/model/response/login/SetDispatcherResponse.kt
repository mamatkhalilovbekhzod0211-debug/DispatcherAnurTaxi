package uz.bekhzod0211.dispatcheranurtaxi.data.model.response.login


import com.google.gson.annotations.SerializedName

data class SetDispatcherResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("user_id")
    val userId: Int
)