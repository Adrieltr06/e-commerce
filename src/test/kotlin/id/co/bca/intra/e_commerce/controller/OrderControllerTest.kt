package id.co.bca.intra.e_commerce.controller

import id.co.bca.intra.e_commerce.model.Account
import id.co.bca.intra.e_commerce.model.Cart
import id.co.bca.intra.e_commerce.model.Order
import id.co.bca.intra.e_commerce.model.Product
import id.co.bca.intra.e_commerce.repository.CartRepository
import id.co.bca.intra.e_commerce.repository.OrderRepository
import id.co.bca.intra.e_commerce.service.CartService
import id.co.bca.intra.e_commerce.service.OrderService
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import kotlin.test.Test

@WebFluxTest(controllers = [OrderController::class])
class OrderControllerTest {

    @MockBean
    private lateinit var orderService: OrderService

    @org.junit.jupiter.api.Test
    fun `test createOrder`() {

        runBlocking {
            // Mock Data
            val account = Account(1, "Bayu", "Jakarta Barat", 1000.00, false)
            val product = Product(1, "Lays", 5000.00, "Keripik Kentang")

            val products = mutableMapOf<Product, Int>()
            products[product] = 2

            val order = Order( 1, account, products, "Outstanding", 10000.00, 2)

            // Mock Behavior
            `when`(orderService.createOrder(1)).thenReturn(Mono.just(order.copy(id = 1)))

            // Verify Behavior
            val client = WebTestClient.bindToController(OrderController(orderService)).build()

            client.post().uri("/api/orders/1")
                .exchange()
                .expectStatus().isCreated
        }
    }

    @org.junit.jupiter.api.Test
    fun `test completeOrder`() {

        runBlocking {
            // Mock Data
            val account = Account(1, "Bayu", "Jakarta Barat", 1000.00, false)
            val product = Product(1, "Lays", 5000.00, "Keripik Kentang")

            val products = mutableMapOf<Product, Int>()
            products[product] = 2

            val order = Order( 1, account, products, "Complete", 10000.00, 2)

            // Mock Behavior
            `when`(orderService.completeOrder(1)).thenReturn(Mono.just(order.copy(id = 1)))

            // Verify Behavior
            val client = WebTestClient.bindToController(OrderController(orderService)).build()

            client.post().uri("/api/orders/1")
                .exchange()
                .expectStatus().isCreated
        }
    }

}