package id.co.bca.intra.e_commerce.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(value = "products")
data class Product(
    @Id val id: Long,
    var name: String,
    var price: Double,
    var description: String
)
