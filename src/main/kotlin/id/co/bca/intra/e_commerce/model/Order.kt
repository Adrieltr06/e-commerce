package id.co.bca.intra.e_commerce.model

import jakarta.persistence.*

@Entity
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

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
