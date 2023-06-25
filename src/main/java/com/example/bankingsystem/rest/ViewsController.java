package com.example.bankingsystem.rest;

import com.example.bankingsystem.service.ViewsService;
import com.example.bankingsystem.views.Allaccounttransactions;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
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

        int totalPages = viewDataPage.getTotalPages() - 1;
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

    @GetMapping("/")
    public String getNavbar() {
        return "navbar";
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

    @GetMapping("/customerInfo")
    public String getCustomerInfo(@RequestParam String embg, Model model) {
        model.addAttribute("customer", viewsService.getCustomerInfo(embg).get(0));
        return "customer-info";
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

    @GetMapping("/addAccount")
    public String addAccount() {
        return "add-account";
    }

    @PostMapping("/add-account")
    public String addAccount(@RequestParam("accountType") String accountType,
                             @RequestParam("balance") BigDecimal balance,
                             @RequestParam("customerEmbg") String customerEmbg,
                             @RequestParam("currencyCode") String currencyCode,
                             @RequestParam("branchId") int branchId) {
        String accountNumber = viewsService.addAccount(accountType, balance, customerEmbg, currencyCode, branchId);
        System.out.println(accountNumber);
        return "redirect:/bankingSystem/";
    }

    @GetMapping("/addOnlineBanking")
    public String addOnlineBanking() {
        return "add-online-banking";
    }

    @PostMapping("/create-online-banking")
    public String createOnlineBanking(@RequestParam("p_UserEmbg") String embg,
                                      @RequestParam("p_Username") String username,
                                      @RequestParam("p_Password") String password) {
        viewsService.createOnlineBanking(embg, username, password);
        return "redirect:/bankingSystem/";
    }

    @GetMapping("/addClient")
    public String addClient() {
        return "add-client";
    }

    @GetMapping("/add-customer")
    public String addCustomer(@RequestParam("embg") String embg,
                              @RequestParam("firstname") String firstname,
                              @RequestParam("lastname") String lastname,
                              @RequestParam("dateofbirth") LocalDate dateofbirth,
                              @RequestParam("city") String city,
                              @RequestParam("address") String address,
                              @RequestParam("email") String email,
                              @RequestParam("phonenumber") String phonenumber) {
        viewsService.addCustomer(embg, firstname, lastname, dateofbirth, city, address, email, phonenumber);
        return "redirect:/bankingSystem/customerInfo?embg=" + embg;
    }

    @GetMapping("/addCard")
    public String addCard() {
        return "add-card";
    }

    @PostMapping("/add-card")
    public String addCard(@RequestParam("cardType") String cardType,
                          @RequestParam("accountNumber") String accountNumber) {
        viewsService.addCard(cardType, accountNumber);
        return "redirect:/bankingSystem/";
    }

    @GetMapping("/makeTransaction")
    public String makeTransaction() {
        return "make-account-transaction";
    }

    @GetMapping("/make-account-transaction")
    public String makeTransaction(@RequestParam("type") String type,
                                  @RequestParam("date") LocalDate date,
                                  @RequestParam("amount") BigDecimal amount,
                                  @RequestParam("accountNumberFrom") String accountNumberFrom,
                                  @RequestParam("accountNumberTo") String accountNumberTo) {
        viewsService.makeTransaction(type, date, amount, accountNumberFrom, accountNumberTo);
        return "redirect:/bankingSystem/";
    }

    @GetMapping("/addEmployeeToBranch")
    public String addEmployeeToBranch() {
        return "add-employee-to-branch";
    }

    @PostMapping("/add-employee-to-branch")
    public String addEmployeeToBranch(@RequestParam("p_JobTitle") String p_JobTitle,
                                      @RequestParam("p_IsManager") Boolean p_IsManager,
                                      @RequestParam("p_ManagedBy") int p_ManagedBy,
                                      @RequestParam("p_BranchId") int p_BranchId,
                                      @RequestParam("p_UserEmbg") String p_UserEmbg) {
        viewsService.addEmployeeToBranch(p_JobTitle, p_IsManager, p_ManagedBy, p_BranchId, p_UserEmbg);
        return "redirect:/bankingSystem/";
    }

    @GetMapping("/exchangeMoney")
    public String exchangeMoney() {
        return "exchange-money";
    }

    @GetMapping("/make-foreign-exchange-transaction")
    public String exchangeMoneySubmit(@RequestParam("amountToExchange") Integer amountToExchange,
                                      @RequestParam("accountNumberFrom") String accountNumberFrom,
                                      @RequestParam("accountNumberTo") String accountNumberTo) {
        viewsService.makeForeignExchangeTransaction(amountToExchange, accountNumberFrom, accountNumberTo);
        return "redirect:/bankingSystem/";
    }

    @GetMapping("/openLoan")
    public String openLoan() {
        return "open-loan";
    }

    @GetMapping("/add-fixed-loan")
    public String addFixedLoan(
            @RequestParam Integer amountBorrowed,
            @RequestParam String currencyCode,
            @RequestParam String loanFor,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam String customerEmbg,
            @RequestParam Integer responsibleEmpId,
            @RequestParam Boolean isFixed
    ) {
        viewsService.openLoan(amountBorrowed, currencyCode, loanFor, startDate, endDate, customerEmbg, responsibleEmpId, isFixed);
        return "redirect:/bankingSystem/";
    }

    @GetMapping("/payLoan")
    public String payLoan() {
        return "make-loan-transaction";
    }

    @PostMapping("/make-loan-transaction")
    public String makeLoanTransaction(
        @RequestParam LocalDate currentDateToPay,
        @RequestParam Integer p_Amount,
        @RequestParam Integer p_LoanId
    ) {
        viewsService.makeLoanTransaction(currentDateToPay, p_Amount, p_LoanId);
        return "make-loan-transaction";
    }

    @GetMapping("/generateExchangeRates")
    public String generateExchangeRates() {
        return "generate-exchange-rates";
    }

@PostMapping("/generateExchangeRates")
public String generateExchangeRatesPost() {
    viewsService.generateExchangeRatesForToday();
    return "redirect:/bankingSystem/exchangeRatesToday";
}

    @GetMapping("/makeAtmTransaction")
    public String makeAtmTransaction() {
        return "make-atm-transaction";
    }

    @PostMapping("/make-atm-transaction")
    public String makeAtmTransaction(@RequestParam("p_Type") String p_Type,
                                     @RequestParam("p_CardNumber") String p_CardNumber,
                                     @RequestParam("p_CCV") String p_CCV,
                                     @RequestParam("p_AtmId") int p_AtmId,
                                     @RequestParam("p_Amount") BigDecimal p_Amount) {
        viewsService.makeAtmTransaction(p_Type, p_CardNumber, p_CCV, p_AtmId, p_Amount);
        return "redirect:/bankingSystem/";
    }

    @GetMapping("/withdrawMoney")
    public String withdrawMoney() {
        return "withdraw-exchange";
    }

    @PostMapping("/withdraw-foreign-exchange")
    public String withdrawMoney(@RequestParam("amountToExchange") BigDecimal amountToExchange,
                                     @RequestParam("currencyCode") String currencyCode,
                                     @RequestParam("accountNumberFrom") String accountNumberFrom) {
        viewsService.withdrawMoney(amountToExchange, currencyCode, accountNumberFrom);
        return "redirect:/bankingSystem/";
    }

}
