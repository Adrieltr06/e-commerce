package id.co.bca.intra.e_commerce.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

//import jakarta.persistence.*

//@Entity

@Table(value = "products")
data class Product(
//    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id val id: Long,
    var name: String,
    var price: Double,
    var description: String
)
