package Eteration.BankApp.service;

import Eteration.BankApp.entity.BankAccount;
import Eteration.BankApp.entity.dto.Amount;
import Eteration.BankApp.entity.dto.PhoneBill;
import Eteration.BankApp.entity.Transaction;

public interface TransactionService {
    Transaction DepositTransaction(BankAccount bankAccount, Transaction transaction, Amount amount);
    Transaction WithdrawalTransaction(BankAccount bankAccount, Transaction transaction, Amount amount);
    Transaction PhoneBillTransaction(BankAccount bankAccount, Transaction transaction, PhoneBill phoneBill);
}
