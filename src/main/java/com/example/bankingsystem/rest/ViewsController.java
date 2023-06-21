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
    @GetMapping("/allAccountTransactions")
    public String getAllAccountTransaction(@RequestParam int accountId, Model model) {
        model.addAttribute("accountTransactions", viewsService.getAllAccountTransactions(accountId));
        return "all-account-transactions";
    }

    @GetMapping("/branchAndAtmLocations")
    public String getBranchAndAtmLocations(Model model) {
        model.addAttribute("locations", viewsService.getBranchAndAtmLocations());
        return "branch-and-atm-locations";
    }

    @GetMapping("/branchEmployees")
    public String getBranchEmployees(@RequestParam int branchId, Model model) {
        model.addAttribute("employees", viewsService.getBranchEmployees(branchId));
        return "branch-employees";
    }

    @GetMapping("/cardAtmTransactions")
    public String getCardAtmTransactions(@RequestParam int cardId, Model model) {
        model.addAttribute("cardTransactions", viewsService.getCardAtmTransactions(cardId));
        return "card-atm-transactions";
    }

    @GetMapping("/customerAccounts")
    public String getCustomerAccounts(@RequestParam int customerId, Model model) {
        model.addAttribute("customerAccounts", viewsService.getCustomerAccounts(customerId));
        return "customer-accounts";
    }

    @PostMapping("/addAccount")
    public String addCustomer(@RequestParam String accountType, @RequestParam double amount,
                              @RequestParam String embg, @RequestParam String currencyCode,
                              @RequestParam int branchId) {
//        viewsService.addAccount(accountType, amount, embg, currencyCode, branchId);
        return "redirect:/accounts";
    }

    @GetMapping("/customerInfo")
    public String getCustomerInfo(@RequestParam String embg, Model model) {
        model.addAttribute("customer", viewsService.getCustomerInfo(embg).get(0));
        return "customer-info";
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
    public String getEmployeeContactInfo(@RequestParam String embg, Model model) {
        model.addAttribute("employee", viewsService.getEmployeeContactInfo(embg).get(0));
        return "employee-contact-info";
    }

    @GetMapping("/exchangeRatesToday")
    public String getExchangeRatesToday(Model model) {
        model.addAttribute("exchangeRates", viewsService.getExchangeRatesToday());
        return "exchange-rates-today";
    }

    @GetMapping("/foreignExchangeTransactions")
    public String getForeignExchangeTransactions(@RequestParam int accountId, Model model) {
        model.addAttribute("transactions", viewsService.getForeignExchangeTransactions(accountId));
        return "foreign-exchange-transactions";
    }

    @GetMapping("/loanActivity")
    public String getLoanActivity(@RequestParam int loanId, Model model) {
        model.addAttribute("loanActivity", viewsService.getLoanActivity(loanId));
        return "loan-activity";
    }

//    @PostMapping("")
//    public String

}
