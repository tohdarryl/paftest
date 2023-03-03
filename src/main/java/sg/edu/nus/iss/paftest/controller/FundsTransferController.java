package sg.edu.nus.iss.paftest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sg.edu.nus.iss.paftest.model.Account;
import sg.edu.nus.iss.paftest.model.Transaction;
import sg.edu.nus.iss.paftest.model.TransferDetail;
import sg.edu.nus.iss.paftest.repository.AccountsRepository;
import sg.edu.nus.iss.paftest.service.FundsTransferService;
import sg.edu.nus.iss.paftest.service.LogAuditService;

@Controller
@RequestMapping(path={"","/"})
public class FundsTransferController {
    
    @Autowired
    FundsTransferService ftSvc;

    @Autowired
    LogAuditService laSvc;


    @GetMapping
    public String showLanding(Model model){
        List<Account> results = ftSvc.findAll();
        System.out.println(results);
        Transaction t = new Transaction();    
        model.addAttribute("accounts", results);
        model.addAttribute("transaction", t);
        return "index";
    }

    @PostMapping(path="/transfer")
    public String postTransaction(@Valid Transaction t, BindingResult bResult, Model model){
        
        if(bResult.hasErrors()){
            return "index";
        }
        
        t.setSameAccount(t.checkForAccounts(t.getFromAccountId(), t.getToAccountId()));  
        if(t.getSameAccount() == true){
            return "index";
        }

        Account a = ftSvc.findById(t.getFromAccountId());
        Account b = ftSvc.findById(t.getToAccountId());
        t.setSuffBalance(t.sufficientBalance(a.getBalance(), t.getAmount()));
        if(t.getSuffBalance() == false){
            return "index";
        }
        
        TransferDetail detail =ftSvc.createResult(t.getFromAccountId(), t.getToAccountId(), t.getAmount());
        System.out.println(detail);
        laSvc.save(detail);
        model.addAttribute("transferdetail", detail);
        model.addAttribute("transaction", t);
        model.addAttribute("fromAccount", a);
        model.addAttribute("toAccount", b);

        return "confirmation";
    }


    
}
