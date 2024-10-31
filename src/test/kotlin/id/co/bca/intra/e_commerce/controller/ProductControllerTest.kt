package id.co.bca.intra.e_commerce.controller

import id.co.bca.intra.e_commerce.dto.ApiResponse
import id.co.bca.intra.e_commerce.model.Product
import id.co.bca.intra.e_commerce.service.ProductService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.doThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest(controllers = [ProductController::class])
class ProductControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var productService: ProductService

    @Test
    fun `test createProduct creates a new product`() {
        val product = Product(id = 1L, name = "Test Product", price = 100.0, description = "A test product")

        given(productService.createProduct(product)).willReturn(Mono.just(product))

        webTestClient.post()
            .uri("/api/products/create")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(product)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.status").isEqualTo(HttpStatus.OK.value())
            .jsonPath("$.message").isEqualTo("Product Created")
    }

    @Test
    fun `test getListProduct returns list of products`() {
        val products = listOf(
            Product(id = 1L, name = "Product 1", price = 50.0, description = "First product"),
            Product(id = 2L, name = "Product 2", price = 100.0, description = "Second product")
        )

        given(productService.findAll()).willReturn(Flux.fromIterable(products))

        webTestClient.get()
            .uri("/api/products")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.status").isEqualTo(HttpStatus.OK.value())
            .jsonPath("$.message").isEqualTo("Success Get List of Products")
            .jsonPath("$.data").isArray
            .jsonPath("$.data[0].name").isEqualTo("Product 1")
    }

    @Test
    fun `test searchProduct returns matched products`() {
        val products = listOf(
            Product(id = 1L, name = "Test Product", price = 100.0, description = "A test product")
        )

        given(productService.searchProduct("Test")).willReturn(Flux.fromIterable(products))

        webTestClient.get()
            .uri("/api/products/search/Test")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.status").isEqualTo(HttpStatus.OK.value())
            .jsonPath("$.message").isEqualTo("Success Search Product By Name")
            .jsonPath("$.data[0].name").isEqualTo("Test Product")
    }

    @Test
    fun `test updateProduct updates an existing product`() {
        val product = Product(id = 1L, name = "Updated Product", price = 120.0, description = "An updated product")

        given(productService.updateProduct(1L, product)).willReturn(Mono.just(product))

        webTestClient.put()
            .uri("/api/products/1")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(product)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.status").isEqualTo(HttpStatus.OK.value())
            .jsonPath("$.message").isEqualTo("Product Updated")
    }

    @Test
    fun `test deleteProduct deletes a product`() {
        val productId = 1L
        given(productService.deleteProduct(productId)).willReturn(Mono.empty())

        webTestClient.delete()
            .uri("/api/products/$productId")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.status").isEqualTo(HttpStatus.OK.value())
            .jsonPath("$.message").isEqualTo("Product Deleted Successfully")
    }
}
