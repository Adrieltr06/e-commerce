package id.co.bca.intra.e_commerce.controller

import id.co.bca.intra.e_commerce.model.Account
import id.co.bca.intra.e_commerce.model.Cart
import id.co.bca.intra.e_commerce.model.Product
import id.co.bca.intra.e_commerce.service.CartService
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito.`when`
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import kotlin.test.Test

@WebFluxTest(controllers = [CartController::class])
class CartControllerTest {

    @MockBean
    private lateinit var cartService: CartService

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
            `when`(cartService.addToCart(1,1,2)).thenReturn(Mono.just(cart.copy(id = 1)))

            // Verify Behavior
            val client = WebTestClient.bindToController(CartController(cartService)).build()

            client.post().uri("/api/carts/add/1/1/2")
                .exchange()
                .expectStatus().isCreated
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Lays")
                .jsonPath("$.price").isEqualTo(5000.00)
                .jsonPath("$.description").isEqualTo("Keripik Kentang")
        }
    }

}