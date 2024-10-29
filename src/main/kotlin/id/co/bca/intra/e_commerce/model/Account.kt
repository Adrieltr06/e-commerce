package id.co.bca.intra.e_commerce.model

//import jakarta.persistence.*
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

//@Entity
@Table(value = "accounts")
data class Account(

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id val id: Long,
    var name: String,
    var address: String,
    var balance: Double,
    val isAdmin: Boolean
)
