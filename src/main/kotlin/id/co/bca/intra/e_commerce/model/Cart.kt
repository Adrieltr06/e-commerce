package id.co.bca.intra.e_commerce.model

import jakarta.persistence.*

@Entity
data class Cart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

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
