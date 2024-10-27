package id.co.bca.intra.e_commerce.model

import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

//import jakarta.persistence.*

//@Entity
@Table(value = "orders")
data class Order(
    @Id val id: Long,

//    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @ManyToOne
    @JoinColumn(name = "account_id")
    val account: Account,

    @ManyToMany
    @JoinTable(
        name = "order_product",
        joinColumns = [JoinColumn(name = "order_id")],
        inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    val products: MutableMap<Product, Int> = mutableMapOf(),

    var status: String,
    var totalAmount: Double,
    var totalQuantity: Int
)
