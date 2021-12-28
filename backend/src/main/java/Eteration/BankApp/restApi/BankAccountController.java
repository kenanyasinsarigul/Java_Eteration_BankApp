package Eteration.BankApp.restApi;

import Eteration.BankApp.common.exception.AccountNotFoundException;
import Eteration.BankApp.common.exception.InsufficientBalanceException;
import Eteration.BankApp.entity.BankAccount;
import Eteration.BankApp.entity.dto.Amount;
import Eteration.BankApp.entity.dto.PhoneBill;
import Eteration.BankApp.entity.Transaction;
import Eteration.BankApp.service.BankAccountService;
import Eteration.BankApp.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account/v1")
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final TransactionService transactionService;

    public BankAccountController(BankAccountService bankAccountService, TransactionService transactionService) {
        this.bankAccountService = bankAccountService;
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<BankAccount> add(@RequestBody BankAccount account){
        BankAccount response=  this.bankAccountService.add(account);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<String> credit(@RequestBody Amount amount, @PathVariable String accountNumber){
        //Find Account
        BankAccount bankAccount=  this.bankAccountService.findByAccountNumber(accountNumber).orElse(null);
        //Check If Account Not Exist
        if (bankAccount==null){
            throw new AccountNotFoundException("Kullanıcı bulunamadı");
        }
        //Create New Transaction
        Transaction transaction=new Transaction();
        transaction=transactionService.DepositTransaction(bankAccount,transaction,amount);
        //Create The Credit Transaction
        bankAccount.setBalance(bankAccount.getBalance()+amount.getAmount());
        bankAccount.getTransactions().add(transaction);
        bankAccountService.add(bankAccount);
        //Return The Approval Code
        return ResponseEntity.ok(transaction.getApprovalCode());
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<String> debit(@RequestBody Amount amount, @PathVariable String accountNumber) throws Exception {
        //Find Account
        BankAccount bankAccount=  this.bankAccountService.findByAccountNumber(accountNumber).orElse(null);
        //Check If Account Not Exist And Balance Status
        if (bankAccount==null){
            throw new AccountNotFoundException("Kullanıcı bulunamadı");
        }
        else if (bankAccount.getBalance()<amount.getAmount()){
            throw new InsufficientBalanceException("Yetersiz Bakiye");
        }
        //Create New Transaction
        Transaction transaction=new Transaction();
        transaction=transactionService.WithdrawalTransaction(bankAccount,transaction,amount);
        //Create The Credit Transaction
        bankAccount.setBalance(bankAccount.getBalance()-amount.getAmount());
        bankAccount.getTransactions().add(transaction);
        bankAccountService.add(bankAccount);
        //Return The Approval Code
        return ResponseEntity.ok(transaction.getApprovalCode());
    }

    @PostMapping("/phonebill/{accountNumber}")
    public ResponseEntity<String> phoneBill(@RequestBody PhoneBill phoneBill, @PathVariable String accountNumber) throws Exception {
        //Find Account
        BankAccount bankAccount=  this.bankAccountService.findByAccountNumber(accountNumber).orElse(null);
        //Check If Account Not Exist And Balance Status
        if (bankAccount==null){
            throw new AccountNotFoundException("Kullanıcı bulunamadı");
        }
        else if (bankAccount.getBalance()<phoneBill.getBill()){
            throw new InsufficientBalanceException("Yetersiz Bakiye");
        }
        //Create New Transaction
        Transaction transaction=new Transaction();
        transaction=transactionService.PhoneBillTransaction(bankAccount,transaction,phoneBill);
        //Create The Credit Transaction
        bankAccount.setBalance(bankAccount.getBalance()-phoneBill.getBill());
        bankAccount.getTransactions().add(transaction);
        bankAccountService.add(bankAccount);
        //Return The Approval Code
        return ResponseEntity.ok(transaction.getApprovalCode());
    }

    @GetMapping()
    public ResponseEntity<List<BankAccount>> getAll(){
        return ResponseEntity.ok(this.bankAccountService.getAll());
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<BankAccount> findByAccountNumber( @PathVariable String accountNumber){
        Optional<BankAccount> op = this.bankAccountService.findByAccountNumber(accountNumber);
        if (op.isPresent()) {
            return ResponseEntity.ok(op.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
