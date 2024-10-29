package id.co.bca.intra.e_commerce.controller

import id.co.bca.intra.e_commerce.service.CartService
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean

@WebFluxTest(controllers = [ProductController::class])
class ProductControllerTest {

    @MockBean
    private lateinit var productController: ProductController
}