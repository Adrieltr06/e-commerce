package id.co.bca.intra.e_commerce.repository

import id.co.bca.intra.e_commerce.model.Product
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface ProductRepository: ReactiveCrudRepository<Product, Long> {
    fun findByNameContainingIgnoreCase(name: String): Flux<Product>
}