package id.co.bca.intra.e_commerce.dto

data class ApiResponse(
    val status: Int,
    val message: String,
    val data: Any? = null
)

