package com.example.bankingsystem.rest;

import com.example.bankingsystem.service.ViewsService;
import com.example.bankingsystem.views.*;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bankingSystem")
@AllArgsConstructor
public class ViewsController {

    private final ViewsService viewsService;

    // OVA NE RABOTI
    // za site da se smeni return type vo String radi thymeleaf i da se dodade Model model kako parametar
    @GetMapping("/allAccountTransactions")
    public List<Allaccounttransactions> getAllAccountTransaction() {
        return viewsService.getAllAccountTransactions();
//        model.addAttribute("transactions", viewsService.getAllAccountTransactions());
//        return "all-account-transactions";
    }

    @GetMapping("/branchAndAtmLocations")
//    public String getBranchAndAtmLocations(Model model) {
    public List<Branchandatmlocations> getBranchAndAtmLocations() {
        return viewsService.getBranchAndAtmLocations();
//        model.addAttribute("transactions", viewsService.getAllAccountTransactions());
//        return "all-account-transactions";
    }

    @GetMapping("/branchEmployees")
    public List<Branchemployees> getBranchEmployees() {
        return viewsService.getBranchEmployees();
//        model.addAttribute("transactions", viewsService.getAllAccountTransactions());
//        return "all-account-transactions";
    }

    @GetMapping("/cardAtmTransactions")
    public List<Cardatmtransactions> getCardAtmTransactions() {
        return viewsService.getCardAtmTransactions();
//        model.addAttribute("transactions", viewsService.getAllAccountTransactions());
//        return "all-account-transactions";
    }

    @GetMapping("/customerAccounts")
    public List<Customeraccounts> getCustomerAccounts() {
        return viewsService.getCustomerAccounts();
//        model.addAttribute("transactions", viewsService.getAllAccountTransactions());
//        return "all-account-transactions";
    }

    @GetMapping("/customerInfo")
    public List<Customerinfo> getCustomerInfo() {
        return viewsService.getCustomerInfo();
//        model.addAttribute("transactions", viewsService.getAllAccountTransactions());
//        return "all-account-transactions";
    }

    @GetMapping("/employeeContactInfo")
    public List<Employeecontactinfo> getEmployeeContactInfo() {
        return viewsService.getEmployeeContactInfo();
//        model.addAttribute("transactions", viewsService.getAllAccountTransactions());
//        return "all-account-transactions";
    }

    @GetMapping("/exchangeRatesToday")
    public List<Exchangeratestoday> getExchangeRatesToday() {
        return viewsService.getExchangeRatesToday();
//        model.addAttribute("transactions", viewsService.getAllAccountTransactions());
//        return "all-account-transactions";
    }

    @GetMapping("/foreignExchangeTransactions")
    public List<Foreignexchangetransactions> getForeignExchangeTransactions() {
        return viewsService.getForeignExchangeTransactions();
//        model.addAttribute("transactions", viewsService.getAllAccountTransactions());
//        return "all-account-transactions";
    }

    @GetMapping("/loanActivity")
    public List<Loanactivity> getLoanActivity() {
        return viewsService.getLoanActivity();
//        model.addAttribute("transactions", viewsService.getAllAccountTransactions());
//        return "all-account-transactions";
    }


}
