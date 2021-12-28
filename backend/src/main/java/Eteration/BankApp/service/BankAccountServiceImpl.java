package Eteration.BankApp.service;

import Eteration.BankApp.entity.BankAccount;
import Eteration.BankApp.repository.BankAccountRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BankAccountServiceImpl implements BankAccountService{

    private BankAccountRepository bankAccountRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BankAccount add(BankAccount account) {
        return this.bankAccountRepository.save(account);
    }

    @Override
    public Optional<BankAccount> findByAccountNumber(String accountNumber){return this.bankAccountRepository.findByAccountNumber(accountNumber);}

    @Override
    public List<BankAccount> getAll() {
        return this.bankAccountRepository.findAll();
    }
}
