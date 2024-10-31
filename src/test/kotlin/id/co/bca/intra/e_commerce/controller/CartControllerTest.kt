package id.co.bca.intra.e_commerce.controller

import id.co.bca.intra.e_commerce.service.CartService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.doThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@WebFluxTest(controllers = [CartController::class])
class CartControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var cartService: CartService

    @Test
    fun `test addToCart adds product to cart successfully`() {
        // Mocking successful addition to cart
        val accountId = 1L
        val productId = 1L
        val quantity = 2

        given(cartService.addToCart(accountId, productId, quantity)).willReturn(Mono.empty())

        // Perform request and verify response
        webTestClient.post()
            .uri("/cart/add/$accountId/$productId/$quantity")
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.code").isEqualTo(HttpStatus.CREATED.value())
            .jsonPath("$.message").isEqualTo("Product added to cart successfully")
    }

    @Test
    fun `test addToCart returns error when cart addition fails`() {
        // Mocking a failure during addition to cart
        val accountId = 1L
        val productId = 1L
        val quantity = 2
        val errorMessage = "Failed to add product to cart"

        doThrow(RuntimeException(errorMessage)).`when`(cartService).addToCart(accountId, productId, quantity)

        // Perform request and verify response
        webTestClient.post()
            .uri("/cart/add/$accountId/$productId/$quantity")
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody()
            .jsonPath("$.code").isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .jsonPath("$.message").isEqualTo("Failed to add product to cart: $errorMessage")
    }
}
