package id.co.bca.intra.e_commerce.model

import jakarta.persistence.*

@Entity
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var name: String,
    var address: String,
    var balance: Double,
    val isAdmin: Boolean
)
