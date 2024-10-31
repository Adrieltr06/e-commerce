package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.repository.CartRepository
import id.co.bca.intra.e_commerce.repository.ProductRepository
import id.co.bca.intra.e_commerce.util.DatabaseException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) {

    fun addToCart(accountId: Long, productId: Long, quantity: Int): Mono<Void> {
        return cartRepository.findByAccountId(accountId)
            .onErrorResume { e -> Mono.error(DatabaseException("Cart not found for account: $accountId", e)) }
            .flatMap { cart ->
                productRepository.findById(productId)
                    .onErrorResume { e -> Mono.error(DatabaseException("Product not found for ID: $productId",e))}
                    .flatMap { product ->
                        val existingQuantity = cart.products.getOrDefault(product, 0)
                        cart.products[product] = existingQuantity + quantity
                        cartRepository.save(cart)
                            .onErrorResume { e -> Mono.error(DatabaseException("Cannot save cart", e)) }
                    }
            }
            .then()
    }
}
