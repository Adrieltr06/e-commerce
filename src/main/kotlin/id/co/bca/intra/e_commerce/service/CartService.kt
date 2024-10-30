package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.model.Cart
import id.co.bca.intra.e_commerce.model.Product
import id.co.bca.intra.e_commerce.repository.AccountRepository
import id.co.bca.intra.e_commerce.repository.CartRepository
import id.co.bca.intra.e_commerce.repository.ProductRepository
import id.co.bca.intra.e_commerce.util.DatabaseException
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val accountRepository: AccountRepository
) {

    suspend fun addToCart(accountId: Long, productId: Long, quantity: Int) : Mono<Cart> {
        var cart: Mono<Cart> = cartRepository.findByAccountId(accountId).flatMap { cart ->
            productRepository.findById(productId).flatMap { product ->
                val newCart = cart.copy(id = cart.id, account = cart.account, products = cart.products)
                newCart.products[product]?.plus(quantity)
                cartRepository.save(newCart)
            }
        }

        if (cart.awaitFirst() == null){
            accountRepository.findById(accountId).flatMap { account ->
                productRepository.findById(productId).flatMap { product ->
                    var products = mutableMapOf<Product, Int>()
                    products.put(product, quantity)
                    val newCart = Cart( id = null, account = account, products = products)
                    cartRepository.save(newCart)
                }
            }
        }
        
        return cart
    }
}