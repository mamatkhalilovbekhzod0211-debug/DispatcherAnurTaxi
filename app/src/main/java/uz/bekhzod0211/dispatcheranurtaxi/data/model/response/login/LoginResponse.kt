package uz.bekhzod0211.dispatcheranurtaxi.data.model.response.login


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    val access_token: String,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("authorization")
    val authorization: String,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("security")
    val security: Security,
    @SerializedName("token")
    val token: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("user")
    val user: User
)