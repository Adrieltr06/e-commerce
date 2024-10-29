package id.co.bca.intra.e_commerce.controller

import id.co.bca.intra.e_commerce.model.Product
import id.co.bca.intra.e_commerce.service.ProductService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {

    @PostMapping("/create")
    suspend fun createProduct(@RequestBody product: Product) : Product{
        return productService.createProduct(product)
    }

    @GetMapping("/search/{name}")
    suspend fun searchProduct(@PathVariable name: String): List<Product> {
        return productService.searchProduct(name)
    }

    @PutMapping("/{id}/name/{name}")
    suspend fun updateProductName(@PathVariable id: Long, @PathVariable name: String): Mono<Product> =
        productService.updateProductName(id, name).switchIfEmpty(
            Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)))

    @PutMapping("/{id}/price/{address}")
    suspend fun updateProductPrice(@PathVariable id: Long, @PathVariable price: Double): Mono<Product> =
        productService.updateProductPrice(id, price).switchIfEmpty(
            Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)))

    @PutMapping("/{id}/description/{address}")
    suspend fun updateProductDescription(@PathVariable id: Long, @PathVariable description: String): Mono<Product> =
        productService.updateProductDescription(id, description).switchIfEmpty(
            Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)))

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteProduct(@PathVariable id: Long) : Mono<Void> =
        productService.deleteProduct(id).switchIfEmpty(
            Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)))
}
