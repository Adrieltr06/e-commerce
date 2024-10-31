package id.co.bca.intra.e_commerce.controller

import id.co.bca.intra.e_commerce.dto.ApiResponse
import id.co.bca.intra.e_commerce.model.Order
import id.co.bca.intra.e_commerce.service.OrderService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@WebFluxTest(controllers = [OrderController::class])
class OrderControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var orderService: OrderService

    @Test
    fun `test createOrder returns success response`() {
        // Mock data
        val order = Order(id = 1L, status = "Pending", totalQuantity = 3, totalAmount = 150.0, products = mutableMapOf(), account = null)
        val apiResponse = ApiResponse(status = HttpStatus.OK.value(), message = "Create Order Success")

        given(orderService.createOrder(order)).willReturn(Mono.just(order))

        // Perform request and verify response
        webTestClient.post()
            .uri("/api/order")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(order)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.status").isEqualTo(HttpStatus.OK.value())
            .jsonPath("$.message").isEqualTo("Create Order Success")
    }

    @Test
    fun `test completeOrder returns success response`() {
        // Mock data
        val order = Order(id = 1L, status = "Pending", totalQuantity = 3, totalAmount = 150.0, products = mutableMapOf(), account = null)
        val apiResponse = ApiResponse(status = HttpStatus.OK.value(), message = "Complete Order Success")

        given(orderService.completeOrder(order.id!!)).willReturn(Mono.just(order.copy(status = "Complete")))

        // Perform request and verify response
        webTestClient.put()
            .uri("/api/order")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(order)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.status").isEqualTo(HttpStatus.OK.value())
            .jsonPath("$.message").isEqualTo("Complete Order Success")
    }
}
