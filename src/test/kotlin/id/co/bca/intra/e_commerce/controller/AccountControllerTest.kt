package id.co.bca.intra.e_commerce.controller

import id.co.bca.intra.e_commerce.service.AccountService
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean

@WebFluxTest(controllers = [AccountController::class])
class AccountControllerTest {

    @MockBean
    private lateinit var accountService: AccountService
}