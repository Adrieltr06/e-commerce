package id.co.bca.intra.e_commerce.controller

import id.co.bca.intra.e_commerce.service.AccountService
import id.co.bca.intra.e_commerce.service.CartService
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean

@WebFluxTest(controllers = [CartController::class])
class CartControllerTest {

    @MockBean
    private lateinit var cartService: CartService
}