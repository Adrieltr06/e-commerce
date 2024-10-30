package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.model.Account
import id.co.bca.intra.e_commerce.model.Cart
import id.co.bca.intra.e_commerce.model.Order
import id.co.bca.intra.e_commerce.model.Product
import id.co.bca.intra.e_commerce.repository.CartRepository
import id.co.bca.intra.e_commerce.repository.OrderRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@SpringBootTest
class OrderServiceTest {

    private val orderRepository = mock(OrderRepository::class.java)
    private val cartRepository = mock(CartRepository::class.java)
    private val orderService = OrderService(orderRepository, cartRepository)

    @Test
    fun `test createOrder`() {

        runBlocking {
            // Mock Data
            val account = Account(1, "Bayu", "Jakarta Barat", 1000.00, false)
            val product = Product(1, "Lays", 5000.00, "Keripik Kentang")

            val products = mutableMapOf<Product, Int>()
            products[product] = 2

            val order = Order( 1, account, products, "Outstanding", 10000.00, 2)

            // Mock Behavior
            `when`(orderRepository.findById(1)).thenReturn(Mono.just(order))

            //Verify behavior
            val result = orderRepository.findById(1)
            StepVerifier.create(result)
                .expectNext(order)
                .verifyComplete()
        }
    }

    @Test
    fun `test completeOrder`() {

        runBlocking {
            // Mock Data
            val account = Account(1, "Bayu", "Jakarta Barat", 1000.00, false)
            val product = Product(1, "Lays", 5000.00, "Keripik Kentang")

            val products = mutableMapOf<Product, Int>()
            products[product] = 2

            val order = Order( 1, account, products, "Complete", 10000.00, 2)

            // Mock Behavior
            `when`(orderRepository.findById(1)).thenReturn(Mono.just(order))

            //Verify behavior
            val result = orderRepository.findById(1)
            StepVerifier.create(result)
                .expectNext(order)
                .verifyComplete()
        }
    }

}