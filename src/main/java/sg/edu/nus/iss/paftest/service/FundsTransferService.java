package sg.edu.nus.iss.paftest.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.paftest.exception.TransferException;
import sg.edu.nus.iss.paftest.model.Account;
import sg.edu.nus.iss.paftest.model.TransferDetail;
import sg.edu.nus.iss.paftest.repository.AccountsRepository;

@Service
public class FundsTransferService {

    @Autowired
    AccountsRepository accRepo;
    
    public List<Account> findAll(){
        return accRepo.findAll();
    }

    public Account findById(String id){
        return accRepo.findById(id);
    }

    @Transactional(rollbackFor = TransferException.class)
    public TransferDetail createResult(String fromAccountId, String toAccountId, Double amount){
        TransferDetail detail = new TransferDetail();
        detail.setTransactionId(UUID.randomUUID().toString()
        .substring(0, 8));
        detail.setDate(LocalDate.now());
        detail.setFromAccountId(fromAccountId);
        detail.setToAccountId(toAccountId);
        detail.setAmount(amount);

        Account fromAccount = accRepo.findById(fromAccountId);
        Double fromBalance = (fromAccount.getBalance()).doubleValue() - amount;
        fromAccount.setBalance(BigDecimal.valueOf(fromBalance));

        Account toAccount = accRepo.findById(toAccountId);
        Double toBalance = (toAccount.getBalance()).doubleValue() + amount;
        toAccount.setBalance(BigDecimal.valueOf(toBalance));

        accRepo.update(fromAccount);
        accRepo.update(toAccount);

        return detail;
    }

    
}
