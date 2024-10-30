package id.co.bca.intra.e_commerce.controller

import id.co.bca.intra.e_commerce.dto.ApiResponse
import id.co.bca.intra.e_commerce.model.Product
import id.co.bca.intra.e_commerce.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {

    @PostMapping("/create")
    suspend fun createProduct(@RequestBody product: Product) : Mono<ResponseEntity<ApiResponse>>{
        return productService.createProduct(product).map {
            ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse(
                    status = HttpStatus.OK.value(),
                    message = "Product Created"
                )
            )
        }.defaultIfEmpty(
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse(
                    status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    message = "Create Product Failed",
                )
            )
        )
    }

    @GetMapping
    suspend fun getListProduct(): Mono<ResponseEntity<ApiResponse>> {
        return productService.findAll().collectList().flatMap { list ->
            if(list.isEmpty()){
                Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse(
                        status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        message = "Product not found"
                    )
                ))
            } else {
                Mono.just(ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponse(
                        status = HttpStatus.OK.value(),
                        message = "Success Get List of Products",
                        data = list
                    )
                ))
            }
        }
    }

    @GetMapping("/search/{name}")
    suspend fun searchProduct(@PathVariable name: String): Mono<ResponseEntity<ApiResponse>> {
        return productService.searchProduct(name).collectList().flatMap { list ->
            if(list.isEmpty()){
                Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse(
                        status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        message = "Product not found",
                        data = list
                    )
                ))
            } else {
                Mono.just(ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponse(
                        status = HttpStatus.OK.value(),
                        message = "Success Search Product By Name",
                        data = list
                    )
                ))
            }
        }
    }

    @PutMapping("/{id}")
    suspend fun updateProduct(@PathVariable id: Long, @RequestBody product: Product): Mono<ResponseEntity<ApiResponse>> {
        return productService.updateProduct(id, product).map {
            ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse(
                    status = HttpStatus.OK.value(),
                    message = "Product Updated"
                )
            )
        }.defaultIfEmpty(
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse(
                    status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    message = "Update Product Failed",
                )
            )
        )
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteProduct(@PathVariable id: Long) : Mono<ResponseEntity<ApiResponse>> {
        return productService.deleteProduct(id).map {
            ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse(
                    status = HttpStatus.OK.value(),
                    message = "Account Deleted Successfully",
                )
            )
        }

    }
}
