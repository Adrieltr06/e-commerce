package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.model.Account
import id.co.bca.intra.e_commerce.model.Cart
import id.co.bca.intra.e_commerce.model.Product
import id.co.bca.intra.e_commerce.repository.CartRepository
import id.co.bca.intra.e_commerce.repository.ProductRepository
import id.co.bca.intra.e_commerce.util.DatabaseException
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@SpringBootTest
class CartServiceTest {

    private val cartRepository = mock(CartRepository::class.java)
    private val productRepository = mock(ProductRepository::class.java)
    private val cartService = CartService(cartRepository, productRepository)

    @Test
    fun `test addToCart successfully adds product to existing cart`() {
        // Mock data
        val accountId = 1L
        val productId = 1L
        val account = Account(id = accountId, name = "Test Account", address = "Address", balance = 100.0, isAdmin = false)
        val product = Product(id = productId, name = "Product1", price = 50.0, description = "A product")
        val cart = Cart(id = 1L, account = account, products = mutableMapOf(product to 1))

        `when`(cartRepository.findByAccountId(accountId)).thenReturn(Mono.just(cart))
        `when`(productRepository.findById(productId)).thenReturn(Mono.just(product))
        `when`(cartRepository.save(cart)).thenReturn(Mono.just(cart))

        // Verify behavior
        val result = cartService.addToCart(accountId, productId, 2)
        StepVerifier.create(result)
            .verifyComplete()

        verify(cartRepository).findByAccountId(accountId)
        verify(productRepository).findById(productId)
        verify(cartRepository).save(cart)
        assert(cart.products[product] == 3) // Existing quantity 1 + new quantity 2
    }
}
