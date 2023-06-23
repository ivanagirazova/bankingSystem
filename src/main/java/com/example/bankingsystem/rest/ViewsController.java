package com.example.bankingsystem.rest;

import com.example.bankingsystem.service.ViewsService;
import com.example.bankingsystem.views.Allaccounttransactions;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/bankingSystem")
@AllArgsConstructor
public class ViewsController {

    private final ViewsService viewsService;

    // da se proveri paginacija
    @GetMapping("/allAccountTransactions")
    public String getMyViewData(
            @RequestParam int accountId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "50") int size,
            Model model) {
        Page<Allaccounttransactions> viewDataPage = viewsService.findAllWithPagination(accountId, page, size);

        int totalPages = viewDataPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("accountNumber", accountId);
        model.addAttribute("viewDataPage", viewDataPage);
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
