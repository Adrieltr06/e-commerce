package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.repository.AccountRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@SpringBootTest
class AccountServiceTest {

    private val accountRepository = mock(AccountRepository::class.java)
    private val accountService = AccountService(accountRepository)

    @Test
    fun `test deleteUser deletes user`() {
        runBlocking{
            // Mock behavior
            `when`(accountRepository.deleteById(1)).thenReturn(Mono.empty())

            // Verify behavior
            val result = accountService.deleteUser(1)
            StepVerifier.create(result).verifyComplete()
        }
    }
}