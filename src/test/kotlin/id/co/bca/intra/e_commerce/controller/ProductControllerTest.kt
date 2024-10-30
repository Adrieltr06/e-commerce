package id.co.bca.intra.e_commerce.controller

import id.co.bca.intra.e_commerce.model.Account
import id.co.bca.intra.e_commerce.model.Product
import id.co.bca.intra.e_commerce.service.AccountService
import id.co.bca.intra.e_commerce.service.CartService
import id.co.bca.intra.e_commerce.service.ProductService
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest(controllers = [ProductController::class])
class ProductControllerTest {

    @MockBean
    private lateinit var productService: ProductService

    @Test
    fun `test getListProduct return products`() {

        runBlocking {
            // Mock Data
            val products = listOf(
                Product(1, "Lays", 5000.00, "Keripik Kentang"),
                Product(2, "Tango", 7000.00, "Kue Ringan"),
                Product(3, "Ore", 8000.00, "Kue Ringan"),
            )

            `when`(productService.findAll()).thenReturn(Flux.fromIterable(products))

            val client = WebTestClient.bindToController(ProductController(productService)).build()

            client.get().uri("/api/products")
                .exchange()
                .expectStatus().isOk
                .expectBodyList(Product::class.java)
                .hasSize(3)
                .contains(products[0], products[1], products[2])
        }

    }

    @Test
    fun `test createProduct save product`() {

        runBlocking {
            // Mock Data
            val product = Product(1, "Lays", 5000.00, "Keripik Kentang")

            // Mock Behavior
            `when`(productService.createProduct(product)).thenReturn(Mono.just(product.copy(id = 1)))

            // Verify Behavior
            val client = WebTestClient.bindToController(ProductController(productService)).build()

            client.post().uri("/api/products")
                .bodyValue(product)
                .exchange()
                .expectStatus().isCreated
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Lays")
                .jsonPath("$.price").isEqualTo(5000.00)
                .jsonPath("$.description").isEqualTo("Keripik Kentang")
        }

    }

    @Test
    fun `test getProduct return product`() {

        runBlocking {
            // Mock Data
            val products = listOf(
                Product(1, "Lays", 5000.00, "Keripik Kentang"),
                Product(2, "Tango", 7000.00, "Kue Ringan"),
                Product(3, "Ore", 8000.00, "Kue Ringan"),
            )
            `when`(productService.searchProduct("a")).thenReturn(Flux.fromIterable(products))

            val client = WebTestClient.bindToController(ProductController(productService)).build()

            client.get().uri("/api/products/search/a")
                .exchange()
                .expectStatus().isOk
                .expectBodyList(Product::class.java)
                .hasSize(3)
                .contains(products[0], products[1])
        }
    }

}