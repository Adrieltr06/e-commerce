package id.co.bca.intra.e_commerce.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(value = "accounts")
data class Account(
    @Id val id: Long,
    var name: String,
    var address: String,
    var balance: Double,
    val isAdmin: Boolean
)
