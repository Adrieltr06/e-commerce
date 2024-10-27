package id.co.bca.intra.e_commerce.controller

import id.co.bca.intra.e_commerce.model.Product
import id.co.bca.intra.e_commerce.service.ProductService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {

    @PostMapping("/create")
    suspend fun createProduct(@RequestBody product: Product) {
    }

    @GetMapping("/search/{name}")
    suspend fun searchProduct(@PathVariable name: String): List<Product> {
    }
}
