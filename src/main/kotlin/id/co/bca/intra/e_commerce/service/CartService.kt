package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.repository.CartRepository
import id.co.bca.intra.e_commerce.repository.ProductRepository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Service

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) {

    suspend fun addToCart(accountId: Long, productId: Long, quantity: Int) {
        val cart = cartRepository.findByAccountId(accountId).awaitFirstOrNull()
        val product = productRepository.findById(productId).awaitFirstOrNull()

        if (cart != null && product != null) {
            cart.products[product] = (cart.products[product] ?: 0) + quantity
            cartRepository.save(cart).awaitFirstOrNull()
        }
    }
}