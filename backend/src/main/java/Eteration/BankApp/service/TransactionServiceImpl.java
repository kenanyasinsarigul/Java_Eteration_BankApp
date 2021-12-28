package Eteration.BankApp.service;

import Eteration.BankApp.entity.BankAccount;
import Eteration.BankApp.entity.TransactionType;
import Eteration.BankApp.entity.dto.Amount;
import Eteration.BankApp.entity.dto.PhoneBill;
import Eteration.BankApp.entity.Transaction;
import Eteration.BankApp.repository.BankAccountRepository;
import Eteration.BankApp.repository.TransactionRepository;
import Eteration.BankApp.util.RandomCodeGenerator;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;
    private  final BankAccountRepository bankAccountRepository;
    private final RandomCodeGenerator randomCodeGenerator;

    public TransactionServiceImpl(TransactionRepository transactionRepository, BankAccountRepository bankAccountRepository, RandomCodeGenerator randomCodeGenerator) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.randomCodeGenerator = randomCodeGenerator;
    }

    @Override
    public Transaction DepositTransaction(BankAccount bankAccount, Transaction transaction, Amount amount) {
        //Set Transaction Fields
        transaction.setBankAccount(bankAccount);
        transaction.setAmount(amount.getAmount());
        transaction.setCreatedDate(new Date());
        transaction.setType(TransactionType.DEPOSIT.toString());
        //Set Random Generated code
        RandomCodeGenerator randomCode=new RandomCodeGenerator();
        transaction.setApprovalCode(randomCode.getGeneratedString());
        //Save Transaction
        return this.transactionRepository.save(transaction);
    }

    @Override
    public Transaction WithdrawalTransaction(BankAccount bankAccount, Transaction transaction, Amount amount) {
        //Set Transaction Fields
        transaction.setBankAccount(bankAccount);
        transaction.setAmount(amount.getAmount());
        transaction.setCreatedDate(new Date());
        transaction.setType(TransactionType.WITHDRAWAL.toString());
        //Set Random Generated code
        RandomCodeGenerator randomCode=new RandomCodeGenerator();
        transaction.setApprovalCode(randomCode.getGeneratedString());
        //Save Transaction
        return this.transactionRepository.save(transaction);
    }

    @Override
    public Transaction PhoneBillTransaction(BankAccount bankAccount, Transaction transaction, PhoneBill phoneBill) {
        //Set Transaction Fields
        transaction.setBankAccount(bankAccount);
        transaction.setAmount(phoneBill.getBill());
        transaction.setCreatedDate(new Date());
        transaction.setType(TransactionType.WITHDRAWAL.toString()+" { PhoneBill: "+phoneBill.getOperator()+"/"+phoneBill.getPhoneNumber()+" }");
        //Set Random Generated code
        RandomCodeGenerator randomCode=new RandomCodeGenerator();
        transaction.setApprovalCode(randomCode.getGeneratedString());
        //Save Transaction
        return this.transactionRepository.save(transaction);
    }
}
