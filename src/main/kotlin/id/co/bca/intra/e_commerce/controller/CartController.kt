package id.co.bca.intra.e_commerce.controller

import id.co.bca.intra.e_commerce.service.CartService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cart")
class CartController(private val cartService: CartService) {

    //TODO MASIH PERLU LOGIC
    @PostMapping("/add/{accountId}/{productId}/{quantity}")
    suspend fun addToCart(@PathVariable accountId: Long, @PathVariable productId: Long, @PathVariable quantity: Int) {
        cartService.addToCart(accountId, productId, quantity)
    }
}
