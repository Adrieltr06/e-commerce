package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.model.Account
import id.co.bca.intra.e_commerce.model.Order
import id.co.bca.intra.e_commerce.model.Product
import id.co.bca.intra.e_commerce.repository.AccountRepository
import id.co.bca.intra.e_commerce.repository.OrderRepository
import id.co.bca.intra.e_commerce.util.DatabaseException
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@SpringBootTest
class OrderServiceTest {

    private val orderRepository = mock(OrderRepository::class.java)
    private val orderService = OrderService(orderRepository)

    @Test
    fun `test createOrder successfully creates an order`() {
        // Mock data
        val account = Account(id = 1L, name = "Test Account", address = "some", balance = 200.0, isAdmin = false)
        val products = mutableMapOf(Product(id = 1L, name = "Product1", price = 100.0, description="something") to 2)
        val order = Order(id = 1L, account = account, products = products, status = "Pending", totalAmount = 200.0, totalQuantity = 2)

        `when`(orderRepository.save(order)).thenReturn(Mono.just(order))

        // Verify behavior
        val result = orderService.createOrder(order)
        StepVerifier.create(result)
            .expectNextMatches {
                it.id == order.id &&
                        it.status == "Pending" &&
                        it.totalAmount == 200.0 &&
                        it.totalQuantity == 2 &&
                        it.products == products &&
                        it.account == account
            }
            .verifyComplete()
    }

    @Test
    fun `test completeOrder successfully completes an order`() {
        // Mock data
        val account = Account(id = 1L, name = "Test Account", address = "some", balance = 200.0, isAdmin = false)
        val products = mutableMapOf(Product(id = 1L, name = "Product1", price = 100.0, description="something") to 2)
        val orderId = 1L
        val order = Order(id = orderId, account = account, products = products, status = "Pending", totalAmount = 200.0, totalQuantity = 2)
        val completedOrder = order.copy(status = "Complete")

        `when`(orderRepository.findById(orderId)).thenReturn(Mono.just(order))
        `when`(orderRepository.save(completedOrder)).thenReturn(Mono.just(completedOrder))

        // Verify behavior
        val result = orderService.completeOrder(orderId)
        StepVerifier.create(result)
            .expectNextMatches {
                it.status == "Complete" &&
                        it.id == orderId &&
                        it.totalAmount == 200.0 &&
                        it.totalQuantity == 2 &&
                        it.products == products &&
                        it.account == account
            }
            .verifyComplete()
    }
}
