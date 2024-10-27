package id.co.bca.intra.e_commerce.model

import jakarta.persistence.*

@Entity
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var name: String,
    var price: Double,
    var description: String
)
