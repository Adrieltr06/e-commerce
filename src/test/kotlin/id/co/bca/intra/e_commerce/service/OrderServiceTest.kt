package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.repository.AccountRepository
import id.co.bca.intra.e_commerce.repository.CartRepository
import id.co.bca.intra.e_commerce.repository.OrderRepository
import id.co.bca.intra.e_commerce.repository.ProductRepository
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class OrderServiceTest {

    private val orderRepository = mock(OrderRepository::class.java)
    private val accountRepository = mock(AccountRepository::class.java)
    private val orderService = OrderService(orderRepository, accountRepository)
}