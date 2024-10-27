package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.model.Product
import id.co.bca.intra.e_commerce.repository.ProductRepository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    suspend fun createProduct(product: Product): Product {
        return productRepository.save(product).awaitFirstOrNull() ?: throw Exception("Failed to create product")
    }

    suspend fun searchProduct(name: String): List<Product> {
        return productRepository.findByNameContainingIgnoreCase(name).collectList().awaitFirstOrNull() ?: emptyList()
    }
}