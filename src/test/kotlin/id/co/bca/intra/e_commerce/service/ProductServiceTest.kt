package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.repository.ProductRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@SpringBootTest
class ProductServiceTest {

    private val productRepository = mock(ProductRepository::class.java)
    private val productService = ProductService(productRepository)

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
}