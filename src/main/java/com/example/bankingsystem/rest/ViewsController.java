package com.example.bankingsystem.rest;

import com.example.bankingsystem.service.ViewsService;
import com.example.bankingsystem.views.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
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
    public String getBranchEmployees(@RequestParam int branchId, Model model) {
        model.addAttribute("employees", viewsService.getBranchEmployees(branchId));
        return "branch-employees";
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

    @PostMapping("/addAccount")
    public String addCustomer(@RequestParam String accountType, @RequestParam double amount,
                              @RequestParam String embg, @RequestParam String currencyCode,
                              @RequestParam int branchId) {
//        viewsService.addAccount(accountType, amount, embg, currencyCode, branchId);
        return "redirect:/accounts";
    }

    @GetMapping("/customerInfo")
    public List<Customerinfo> getCustomerInfo() {
        return viewsService.getCustomerInfo();
//        model.addAttribute("transactions", viewsService.getAllAccountTransactions());
//        return "all-account-transactions";
    }

    @PostMapping("/addCustomer")
    public String addCustomer(@RequestParam String embg, @RequestParam String firstName,
                              @RequestParam String lastName, @RequestParam LocalDate dob,
                              @RequestParam String city, @RequestParam String address,
                              @RequestParam String email, @RequestParam String phoneNumber) {
//        viewsService.addCustomer(embg, firstName, lastName, dob, city, address, email, phoneNumber);
        return "redirect:/customers";
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

//    @PostMapping("")
//    public String

}
