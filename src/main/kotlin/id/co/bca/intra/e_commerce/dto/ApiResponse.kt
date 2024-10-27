package id.co.bca.intra.e_commerce.dto

data class ApiResponse<T>(
    val status: Int,
    val message: String,
    val data: T? = null
)

