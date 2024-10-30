package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.model.Product
import id.co.bca.intra.e_commerce.repository.ProductRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@SpringBootTest
class ProductServiceTest {

    private val productRepository = mock(ProductRepository::class.java)
    private val productService = ProductService(productRepository)

    @Test
    fun `test getListProduct return products`() {

        runBlocking {
            // Mock Data
            val products = listOf(
                Product(1, "Lays", 5000.00, "Keripik Kentang"),
                Product(2, "Tango", 7000.00, "Kue Ringan"),
                Product(3, "Ore", 8000.00, "Kue Ringan"),
            )

            `when`(productRepository.findAll()).thenReturn(Flux.fromIterable(products))

            //Verify behavior
            val result = productService.findAll()
            StepVerifier.create(result)
                .expectNext(products[0])
                .expectNext(products[1])
                .expectNext(products[2])
                .verifyComplete()
        }

    }

    @Test
    fun `test createProduct save product`() {

        runBlocking {
            //Mock Data
            val product = Product(1, "Lays", 5000.00, "Keripik Kentang")

            `when`(productRepository.save(product))
                .thenReturn(Mono.just(product.copy(id = 1)))

            //Verify Behavior
            val result = productService.createProduct(product)

            StepVerifier.create(result)
                .expectNextMatches {
                    it.id == 1L && it.name == "Lays" && it.price == 5000.00 && it.description == "Keripik Kentang"
                }.verifyComplete()
        }

    }

    @Test
    fun `test deleteProduct deletes Product`() {
        runBlocking{


            // Mock behavior
            `when`(productRepository.deleteById(1)).thenReturn(Mono.empty())

            // Verify behavior
            val result = productService.deleteProduct(1)
            StepVerifier.create(result).verifyComplete()
        }
    }

    @Test
    fun `test searchProduct return product`() {

        runBlocking {
            // Mock Data
            val products = listOf(
                Product(1, "Lays", 5000.00, "Keripik Kentang"),
                Product(2, "Tango", 7000.00, "Kue Ringan"),
                Product(3, "Ore", 8000.00, "Kue Ringan"),
            )

            `when`(productRepository.findAll().filter{ it.name.contains("a")}).thenReturn(Flux.fromIterable(products))

            //Verify behavior
            val result = productService.searchProduct("a")
            StepVerifier.create(result)
                .expectNext(products[0])
                .expectNext(products[1])
                .verifyComplete()
        }
    }
}