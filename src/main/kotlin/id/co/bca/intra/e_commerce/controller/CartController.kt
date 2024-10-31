package id.co.bca.intra.e_commerce.controller

import id.co.bca.intra.e_commerce.service.CartService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cart")
class CartController(private val cartService: CartService) {

    @PostMapping("/add/{accountId}/{productId}/{quantity}")
    suspend fun addToCart(
        @PathVariable accountId: Long,
        @PathVariable productId: Long,
        @PathVariable quantity: Int
    ): ResponseEntity<Map<String, Any>> {
        return try {
            cartService.addToCart(accountId, productId, quantity)
            ResponseEntity.status(HttpStatus.CREATED).body(
                mapOf(
                    "code" to HttpStatus.CREATED.value(),
                    "message" to "Product added to cart successfully"
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                mapOf(
                    "code" to HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "message" to "Failed to add product to cart: ${e.message}"
                )
            )
        }
    }
}