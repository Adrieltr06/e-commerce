package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.model.Product
import id.co.bca.intra.e_commerce.repository.ProductRepository
import id.co.bca.intra.e_commerce.util.DatabaseException
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun createProduct(product: Product): Mono<Product> {
        return productRepository.save(product)
            .onErrorResume { e -> Mono.error(DatabaseException("Failed to create product", e)) }
    }

    fun searchProduct(name: String): Flux<Product> {
        return productRepository.findAll()
            .filter { it.name.contains(name, ignoreCase = true) }
            .onErrorResume { e ->  Mono.error(DatabaseException("No products found with name: $name",e))}
    }

    fun findAll(): Flux<Product> {
        return productRepository.findAll()
    }

    fun updateProduct(id: Long, product: Product): Mono<Product> {
        return productRepository.findById(id)
            .flatMap { existingProduct ->
                val updatedProduct = existingProduct.copy(
                    name = product.name,
                    price = product.price,
                    description = product.description
                )
                productRepository.save(updatedProduct)
            }
            .onErrorResume { e -> Mono.error(DatabaseException("Failed to update product", e)) }
    }

    /*suspend fun updateProductName(id: Long, name: String): Mono<Product> {
        return productRepository.findById(id)
            .flatMap {
                val updatedProduct = it.copy(name = name)
                productRepository.save(updatedProduct)
            }
            .onErrorResume { e -> Mono.error(DatabaseException("Failed to update product name", e)) }
    }

    suspend fun updateProductPrice(id: Long, price: Double): Mono<Product> {
        return productRepository.findById(id)
            .flatMap {
                val updatedProduct = it.copy(price = price)
                productRepository.save(updatedProduct)
            }
            .onErrorResume { e -> Mono.error(DatabaseException("Failed to update product price", e)) }
    }

    suspend fun updateProductDescription(id: Long, description: String): Mono<Product> {
        return productRepository.findById(id)
            .flatMap {
                val updatedProduct = it.copy(description = description)
                productRepository.save(updatedProduct)
            }
            .onErrorResume { e -> Mono.error(DatabaseException("Failed to update product description", e)) }
    }*/

    fun deleteProduct(id: Long): Mono<Void> {
        return productRepository.findById(id)
            .flatMap { productRepository.deleteById(id) }
            .onErrorResume { e -> Mono.error(DatabaseException("Failed to delete product", e)) }
    }
}
