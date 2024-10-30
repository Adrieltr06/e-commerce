package id.co.bca.intra.e_commerce.model

import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table


//import jakarta.persistence.*

//@Entity
@Table(value = "carts")
data class Cart(

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id val id: Long?,

    @ManyToOne
    @JoinColumn(name = "account_id")
    val account: Account,

    @ManyToMany
    @JoinTable(
        name = "cart_product",
        joinColumns = [JoinColumn(name = "cart_id")],
        inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    val products: MutableMap<Product, Int> = mutableMapOf() // Product and quantity mapping
)
