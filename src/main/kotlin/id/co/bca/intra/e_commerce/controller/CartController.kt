package id.co.bca.intra.e_commerce.controller

import id.co.bca.intra.e_commerce.dto.ApiResponse
import id.co.bca.intra.e_commerce.service.CartService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/carts")
class CartController(private val cartService: CartService) {

    @PostMapping("/add/{accountId}/{productId}/{quantity}")
    suspend fun addToCart(@PathVariable accountId: Long, @PathVariable productId: Long, @PathVariable quantity: Int) : Mono<ResponseEntity<ApiResponse>> {
        return cartService.addToCart(accountId, accountId, quantity).map {
            ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse(
                    status = HttpStatus.OK.value(),
                    message = "Create Order Success",
                )
            )
        }.defaultIfEmpty(
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse(
                    status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    message = "Create Account Failed",
                )
            )
        )
    }
}
