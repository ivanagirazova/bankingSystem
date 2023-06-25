package com.example.bankingsystem.service;

import com.example.bankingsystem.views.Allaccounttransactions;
import com.example.bankingsystem.views.Branchandatmlocations;
import com.example.bankingsystem.views.Branchemployees;
import com.example.bankingsystem.views.Cardatmtransactions;
import com.example.bankingsystem.views.Customeraccounts;
import com.example.bankingsystem.views.Customerinfo;
import com.example.bankingsystem.views.Employeecontactinfo;
import com.example.bankingsystem.views.Exchangeratestoday;
import com.example.bankingsystem.views.Foreignexchangetransactions;
import com.example.bankingsystem.views.Loanactivity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ViewsService {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Page<Allaccounttransactions> findAllWithPagination(int accountId, int page, int size) {
        int offset = page * size;

        String query = """
                SELECT *
                FROM allaccounttransactions
                WHERE accountid = :accountId
                LIMIT :size
                OFFSET :offset;
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("accountId", accountId);
        params.addValue("size", size);
        params.addValue("offset", offset);

        List<Allaccounttransactions> entities = namedParameterJdbcTemplate.query(query, params, (rs, rowNum) -> {
            Allaccounttransactions data = new Allaccounttransactions();
            data.setAccountid(rs.getLong("accountid"));
            data.setTransactionid(rs.getLong("transactionid"));
            data.setType(rs.getString("type"));
            data.setDate(rs.getDate("date"));
            data.setAmount(rs.getLong("amount"));
            data.setCurrencycode(rs.getString("currencycode"));
            data.setAccountfrom(rs.getLong("accountfrom"));
            data.setAccountto(rs.getLong("accountto"));
            return data;
        });

        int totalCount = countTotalRecords(params); // Implement this method to count the total number of records in the view

        return new PageImpl<>(entities, PageRequest.of(page - 1, size), totalCount);
    }

    private int countTotalRecords(MapSqlParameterSource params) {
        String sql = """
                SELECT COUNT(*) FROM allaccounttransactions WHERE accountId = :accountId;
                """;
        return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    public List<Branchandatmlocations> getBranchAndAtmLocations() {
        String sql = """
                SELECT 'Branch'::text AS locationtype,
                        branch.id      AS locationid,
                        branch.address
                 FROM branch
                 UNION ALL
                 SELECT 'ATM'::text AS locationtype,
                        atm.id      AS locationid,
                        atm.address
                 FROM atm;
                """;
        return namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> {
            Branchandatmlocations data = new Branchandatmlocations();
            data.setLocationtype(rs.getString("locationtype"));
            data.setLocationid(rs.getLong("locationid"));
            data.setAddress(rs.getString("address"));
            return data;
        });
    }

    public List<Branchemployees> getBranchEmployees(Integer branchId) {
        String sql = """
                SELECT br.id  AS branchid,
                       emp.id AS employeeid,
                       emp.jobtitle,
                       emp.ismanager,
                       usr.firstname,
                       usr.lastname,
                       usr.email,
                       usr.phonenumber
                FROM employee emp
                         JOIN "User" usr ON emp.userid = usr.id
                         JOIN branch br ON emp.branchid = br.id
                WHERE br.id = :branchId;""";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("branchId", branchId);
        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> {
            Branchemployees data = new Branchemployees();
            data.setBranchid(rs.getLong("branchid"));
            data.setEmployeeid(rs.getLong("employeeid"));
            data.setJobtitle(rs.getString("jobtitle"));
            data.setIsmanager(rs.getBoolean("ismanager"));
            data.setFirstname(rs.getString("firstname"));
            data.setLastname(rs.getString("lastname"));
            data.setEmail(rs.getString("email"));
            data.setPhonenumber(rs.getString("phonenumber"));
            return data;
        });
    }

    public List<Cardatmtransactions> getCardAtmTransactions(int cardId) {
        String sql = """
                SELECT atm.id AS transactionid,
                       atm.type,
                       at."Date",
                       at.amount,
                       atm.cardid,
                       atm.accounttransactionid
                FROM atmtransaction atm
                         JOIN accounttransaction at ON atm.accounttransactionid = at.id
                         JOIN card c ON atm.cardid = c.id
                         JOIN account a ON c.accountid = a.id
                         JOIN customer cust ON a.customerid = cust.id
                WHERE c.id = :cardId;
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("cardId", cardId);
        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> {
            Cardatmtransactions data = new Cardatmtransactions();
            data.setTransactionid(rs.getLong("transactionid"));
            data.setType(rs.getString("type"));
            data.setDate(rs.getDate("date"));
            data.setAmount(rs.getLong("amount"));
            data.setCardid(rs.getLong("cardid"));
            data.setAccounttransactionid(rs.getLong("accounttransactionid"));
            return data;
        });
    }

    public List<Customeraccounts> getCustomerAccounts(int customerId) {
        String sql = """
                SELECT *
                FROM customeraccounts
                WHERE customerid = :customerId;
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customerId", customerId);
        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> {
            Customeraccounts data = new Customeraccounts();
            data.setCustomerid(rs.getLong("customerid"));
            data.setAccountid(rs.getLong("accountid"));
            data.setAccountnumber(rs.getString("accountnumber"));
            data.setAccounttype(rs.getString("accounttype"));
            data.setBalance(rs.getDouble("balance"));
            data.setCardid(rs.getLong("cardid"));
            data.setCurrencyCode(rs.getString("currencyCode"));
            data.setCardnumber(rs.getString("cardnumber"));
            data.setCardtype(rs.getString("cardtype"));
            data.setExpiredate(rs.getDate("expiredate"));
            return data;
        });
    }

    public List<Customerinfo> getCustomerInfo(String embg) {
        String sql = """	
                SELECT c.id AS customerid,
                       u.firstname,
                       u.lastname,
                       u.dateofbirth,
                       u.city,
                       u.address,
                       u.email,
                       u.phonenumber
                FROM customer c
                         JOIN "User" u ON c.userid = u.id
                WHERE u.embg = :embg;
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("embg", embg);
        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> {
            Customerinfo data = new Customerinfo();
            data.setCustomerid(rs.getLong("customerid"));
            data.setFirstname(rs.getString("firstname"));
            data.setLastname(rs.getString("lastname"));
            data.setDateofbirth(rs.getDate("dateofbirth"));
            data.setCity(rs.getString("city"));
            data.setAddress(rs.getString("address"));
            data.setEmail(rs.getString("email"));
            data.setPhonenumber(rs.getString("phonenumber"));
            return data;
        });
    }

    public List<Employeecontactinfo> getEmployeeContactInfo(String embg) {
        String sql = """
                SELECT emp.id     AS employeeid,
                       emp.jobtitle,
                       emp.ismanager,
                       br.id      AS branchid,
                       br.address AS branchaddress,
                       br.branchemail,
                       br.branchphonenumber,
                       usr.firstname,
                       usr.lastname,
                       usr.email,
                       usr.phonenumber
                FROM employee emp
                         JOIN "User" usr ON emp.userid = usr.id
                         JOIN branch br ON emp.branchid = br.id
                WHERE usr.embg = :embg;
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("embg", embg);
        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> {
            Employeecontactinfo data = new Employeecontactinfo();
            data.setEmployeeid(rs.getLong("employeeid"));
            data.setJobtitle(rs.getString("jobtitle"));
            data.setIsmanager(rs.getBoolean("ismanager"));
            data.setBranchid(rs.getLong("branchid"));
            data.setBranchaddress(rs.getString("branchaddress"));
            data.setBranchemail(rs.getString("branchemail"));
            data.setBranchphonenumber(rs.getString("branchphonenumber"));
            data.setFirstname(rs.getString("firstname"));
            data.setLastname(rs.getString("lastname"));
            data.setEmail(rs.getString("email"));
            data.setPhonenumber(rs.getString("phonenumber"));
            return data;
        });
    }

    public List<Exchangeratestoday> getExchangeRatesToday() {
        String sql = """
                SELECT er.id             AS exchangerateid,
                       er."Date",
                       er.exchangerate,
                       basecurrency.code AS basecurrencycode,
                       basecurrency.name AS basecurrencyname,
                       currency.code     AS currencycode,
                       currency.name     AS currencyname
                FROM exchangerate er
                         JOIN currency basecurrency ON er.basecurrency = basecurrency.id
                         JOIN currency currency ON er.currencyid = currency.id
                WHERE er."Date" = CURRENT_DATE;
                """;
        return namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> {
            Exchangeratestoday data = new Exchangeratestoday();
            data.setExchangerateid(rs.getLong("exchangerateid"));
            data.setDate(rs.getDate("date"));
            data.setExchangerate(rs.getDouble("exchangerate"));
            data.setBasecurrencycode(rs.getString("basecurrencycode"));
            data.setBasecurrencyname(rs.getString("basecurrencyname"));
            data.setCurrencycode(rs.getString("currencycode"));
            data.setCurrencyname(rs.getString("currencyname"));
            return data;
        });
    }

    public List<Foreignexchangetransactions> getForeignExchangeTransactions(int accountId) {
        String sql = """
                SELECT fet.id                        AS transactionid,
                       fet."Date",
                       fet.amount,
                       fet.convertedamount,
                       fromcurrency.code             AS fromcurrencycode,
                       fromcurrency.name             AS fromcurrencyname,
                       tocurrency.code               AS tocurrencycode,
                       tocurrency.name               AS tocurrencyname,
                       fromexchangerate.exchangerate AS fromexchangerate,
                       toexchangerate.exchangerate   AS toexchangerate,
                       accountfrom.accountnumber     AS fromaccountnumber,
                       accountto.accountnumber       AS toaccountnumber
                FROM foreignexchangetransaction fet
                         JOIN currency fromcurrency ON fet.fromcuurency = fromcurrency.id
                         JOIN currency tocurrency ON fet.tocurrency = tocurrency.id
                         JOIN exchangerate fromexchangerate ON fet.fromexchangerate = fromexchangerate.id
                         JOIN exchangerate toexchangerate ON fet.toexchangerate = toexchangerate.id
                         JOIN account accountfrom ON fet.accountidfrom = accountfrom.id
                         JOIN account accountto ON fet.accountidto = accountto.id
                WHERE fet.accountidfrom = :accountId OR fet.accountidto = :accountId;
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("accountId", accountId);
        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> {
            Foreignexchangetransactions data = new Foreignexchangetransactions();
            data.setTransactionid(rs.getLong("transactionid"));
            data.setDate(rs.getTimestamp("date"));
            data.setAmount(rs.getDouble("amount"));
            data.setConvertedamount(rs.getDouble("convertedamount"));
            data.setFromcurrencycode(rs.getString("fromcurrencycode"));
            data.setFromcurrencyname(rs.getString("fromcurrencyname"));
            data.setTocurrencycode(rs.getString("tocurrencycode"));
            data.setTocurrencyname(rs.getString("tocurrencyname"));
            data.setFromexchangerate(rs.getDouble("fromexchangerate"));
            data.setToexchangerate(rs.getDouble("toexchangerate"));
            data.setFromaccountnumber(rs.getString("fromaccountnumber"));
            data.setToaccountnumber(rs.getString("toaccountnumber"));
            return data;
        });
    }

    public List<Loanactivity> getLoanActivity(int loanId) {
        String sql = """
                SELECT *
                FROM loanactivity l
                WHERE l.loanId = :loanId;
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("loanId", loanId);
        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> {
            Loanactivity data = new Loanactivity();
            data.setLoanid(rs.getLong("loanid"));
            data.setTransactionid(rs.getLong("transactionid"));
            data.setTransactiondate(rs.getDate("transactiondate"));
            data.setTransactionamount(rs.getLong("transactionamount"));
            data.setPrincipalamount(rs.getLong("principalamount"));
            data.setInterestamount(rs.getLong("interestamount"));
            data.setAmountborrowed(rs.getLong("amountborrowed"));
            data.setAmountowed(rs.getLong("amountowed"));
            return data;
        });
    }

    public void makeForeignExchangeTransaction(Integer amount, String accountFrom, String accountTo) {
        jdbcTemplate.update("    CALL MakeForeignExchangeTransaction(?, ?, ?);",
                amount, accountFrom, accountTo);
    }

    //key constraint
    public void createOnlineBanking(String embg, String username, String password) {
        jdbcTemplate.update("CALL createonlinebanking(?, ?, ?)", embg, username, password);
    }

    // PORCEDURI
    public void addCustomer(String embg, String firstName, String lastName, LocalDate dob, String city, String address, String email, String phoneNumber) {
        jdbcTemplate.update("CALL AddCustomer(?, ?, ?, ?, ?, ?, ?, ?)", embg, firstName, lastName, dob, city, address, email, phoneNumber);
    }

    // NE RABOTI
    public String addAccount(String accountType, BigDecimal balance, String customerEmbg, String currencyCode, int branchId) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("p_AccountType", accountType);
        params.addValue("p_Balance", balance);
        params.addValue("p_CustomerEmbg", customerEmbg);
        params.addValue("p_CurrencyCode", currencyCode);
        params.addValue("p_BranchId", branchId);

        return namedParameterJdbcTemplate.query("SELECT addaccount(:p_AccountType, :p_Balance, :p_CustomerEmbg, :p_CurrencyCode, :p_BranchId)", params, (rs, numRows) -> rs.getString("addaccount")).get(0);
    }

    // RABOTI
    public void addCard(String cardType, String accountNumber) {
        jdbcTemplate.update("CALL createcard(?, ?)", cardType, accountNumber);
    }

    public void makeTransaction(String type, LocalDate date, BigDecimal amount, String accountNumberFrom, String accountNumberTo) {
        jdbcTemplate.update("CALL makeaccounttransaction(?, ?, ?, ?, ?)", type, date, amount, accountNumberFrom, accountNumberTo);
    }

    public void addEmployeeToBranch(String p_jobTitle, Boolean p_isManager, int p_managedBy, int p_branchId, String p_userEmbg) {
        jdbcTemplate.update("CALL addemployeetobranch(?, ?, ?, ?, ?)", p_jobTitle, p_isManager, p_managedBy, p_branchId, p_userEmbg);
    }

    public void generateExchangeRatesForToday() {
        jdbcTemplate.update("CALL GenerateExchangeRatesForToday();");
    }

    public void openLoan(
        Integer amountBorrowed,
        String currencyCode,
        String loanFor,
        LocalDate startDate,
        LocalDate endDate,
        String customerEmbg,
        Integer responsibleEmpId,
        Boolean isFixed
    ) {
        jdbcTemplate.update("CALL AddFixedLoan(?, ?, ?, ?, ?, ?, ?, ?);",
                amountBorrowed, currencyCode, loanFor, startDate, endDate, customerEmbg, responsibleEmpId, isFixed
        );
    }

    public void makeLoanTransaction(LocalDate date, Integer amount, Integer loanId) {
        jdbcTemplate.update("CALL MakeLoanTransaction(?, ?, ?);", date, amount, loanId);
    }

    public void makeAtmTransaction(String p_type, String p_cardNumber, String p_ccv, int p_atmId, BigDecimal p_amount) {
        jdbcTemplate.update("CALL makeatmtransaction(?, ?, ?, ?, ?)", p_type, p_cardNumber, p_ccv, p_atmId, p_amount);
    }

    public void withdrawMoney(BigDecimal amountToExchange, String currencyCode, String accountNumberFrom) {
        jdbcTemplate.update("CALL withdrawforeignexchangetransaction(?, ?, ?)", amountToExchange, currencyCode, accountNumberFrom);
    }
}
