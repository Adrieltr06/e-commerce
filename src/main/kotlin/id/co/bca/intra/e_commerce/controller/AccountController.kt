package id.co.bca.intra.e_commerce.controller

import id.co.bca.intra.e_commerce.model.Account
import id.co.bca.intra.e_commerce.service.AccountService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/accounts")
class AccountController(private val accountService: AccountService) {

    @PostMapping("/topup/{accountId}/{amount}")
    suspend fun topUp(@PathVariable accountId: Long, @PathVariable amount: Double) {
    }

    @PostMapping("/create")
    suspend fun createUser(@RequestBody account: Account) {
    }
}
