package Eteration.BankApp.service;

import Eteration.BankApp.entity.BankAccount;
import java.util.List;
import java.util.Optional;

public interface BankAccountService {
    BankAccount add(BankAccount account);
    Optional<BankAccount> findByAccountNumber(String accountNumber);
    List<BankAccount> getAll();
}
