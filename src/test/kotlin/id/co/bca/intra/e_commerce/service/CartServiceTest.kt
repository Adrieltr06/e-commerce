package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.repository.CartRepository
import id.co.bca.intra.e_commerce.repository.ProductRepository
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CartServiceTest {

    private val cartRepository = mock(CartRepository::class.java)
    private val productRepository = mock(ProductRepository::class.java)
    private val cartService = CartService(cartRepository, productRepository)
}