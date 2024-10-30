package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.model.Account
import id.co.bca.intra.e_commerce.model.Cart
import id.co.bca.intra.e_commerce.model.Product
import id.co.bca.intra.e_commerce.repository.AccountRepository
import id.co.bca.intra.e_commerce.repository.CartRepository
import id.co.bca.intra.e_commerce.repository.ProductRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@SpringBootTest
class CartServiceTest {

    private val cartRepository = mock(CartRepository::class.java)
    private val productRepository = mock(ProductRepository::class.java)
    private val accountRepository = mock(AccountRepository::class.java)
    private val cartService = CartService(cartRepository, productRepository, accountRepository)

    @Test
    fun `test addToCart return cart`() {

        runBlocking {
            // Mock Data
            val account = Account(1, "Bayu", "Jakarta Barat", 1000.00, false)
            val product = Product(1, "Lays", 5000.00, "Keripik Kentang")

            val products = mutableMapOf<Product, Int>()
            products[product] = 2

            val cart = Cart(1, account, products)

            // Mock Behavior
            `when`(cartRepository.findById(1)).thenReturn(Mono.just(cart))

            //Verify behavior
            val result = cartRepository.findById(1)
            StepVerifier.create(result)
                .expectNext(cart)
                .verifyComplete()
        }
    }

}