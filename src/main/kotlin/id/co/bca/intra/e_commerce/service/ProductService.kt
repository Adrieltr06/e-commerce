package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.model.Product
import id.co.bca.intra.e_commerce.repository.ProductRepository
import id.co.bca.intra.e_commerce.util.DatabaseException
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ProductService(private val productRepository: ProductRepository) {

    suspend fun createProduct(product: Product): Mono<Product> {
        return productRepository.save(product).onErrorResume {
            e -> Mono.error(DatabaseException("Failed to create account", e))
        }
    }

    suspend fun searchProduct(name: String): Flux<Product> {
        return productRepository.findAll().filter{ it.name.contains(name) }
    }

    suspend fun findAll(): Flux<Product> {
        return productRepository.findAll()
    }

    suspend fun updateProduct(id: Long, product: Product): Mono<Product> {
        return productRepository.findById(id)
            .flatMap {
                val updatedProduct = it.copy(name = it.name, price = it.price, description = it.description)
                productRepository.save(updatedProduct)
            }
    }

    suspend fun deleteProduct(id: Long): Mono<Void> = productRepository.deleteById(id)
}
