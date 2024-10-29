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

    suspend fun updateProductName(id: Long, name: String): Mono<Product> {
        return productRepository.findById(id)
            .flatMap {
                val updatedProduct = it.copy(name = name, price = it.price, description = it.description)
                productRepository.save(updatedProduct)
            }
    }

    suspend fun updateProductPrice(id: Long, price: Double): Mono<Product> {
        return productRepository.findById(id)
            .flatMap {
                val updatedProduct = it.copy(name = it.name, price = price, description = it.description)
                productRepository.save(updatedProduct)
            }
    }

    suspend fun updateProductDescription(id: Long, description: String): Mono<Product> {
        return productRepository.findById(id)
            .flatMap {
                val updatedProduct = it.copy(name = it.name, price = it.price, description = description)
                productRepository.save(updatedProduct)
            }
    }

    suspend fun deleteProduct(id: Long): Mono<Void> = productRepository.deleteById(id)
}
