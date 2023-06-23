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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ViewsService {

    private final NamedParameterJdbcTemplate jdbcTemplate;

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

        List<Allaccounttransactions> entities = jdbcTemplate.query(query, params, (rs, rowNum) -> {
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
        return jdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    //    CALL AddEmployeeToBranch('Cashier', FALSE, NULL, 1, '2102968450017');
//    public void addEmployeeToBranch(String jobTitle, boolean isManager, int managedBy, int branchId, int employeeEmbg) {
//        jdbcTemplate.update("CALL addemployeetobranch(?, ?, ?, ?, ?)", jobTitle, isManager, managedBy, branchId, employeeEmbg);
//    }

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
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
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
        return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
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
        return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
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
                SELECT cust.id AS customerid,
                       a.id    AS accountid,
                       a.accountnumber,
                       a.accounttype,
                       a.balance,
                       c.id    AS cardid,
                       c.cardnumber,
                       c.cardtype,
                       c.expiredate
                FROM account a
                         JOIN card c ON a.id = c.accountid
                         JOIN customer cust ON a.customerid = cust.id
                WHERE cust.id = :customerId;
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customerId", customerId);
        return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
            Customeraccounts data = new Customeraccounts();
            data.setCustomerid(rs.getLong("customerid"));
            data.setAccountid(rs.getLong("accountid"));
            data.setAccountnumber(rs.getString("accountnumber"));
            data.setAccounttype(rs.getString("accounttype"));
            data.setBalance(rs.getDouble("balance"));
            data.setCardid(rs.getLong("cardid"));
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
        return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
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
        return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
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
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
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
        return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
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
                SELECT l.id         AS loanid,
                       lt.id        AS transactionid,
                       lt."Date"    AS transactiondate,
                       lt.amount    AS transactionamount,
                       lt.principalamount,
                       lt.interestamount,
                       l.amountborrowed,
                       l.amountowed,
                       ir.value     AS interestrate,
                       ir.isfixedrate,
                       ir.interesttype,
                       ir.datefrom  AS rateeffectivefrom,
                       ir.dateto    AS rateeffectiveto,
                       lth.datefrom AS ratehistoryfrom,
                       lth.dateto   AS ratehistoryto
                FROM loan l
                         JOIN loantransaction lt ON l.id = lt.loanid
                         JOIN interestrates ir ON l.id = ir.id
                         JOIN loan_interestratehistory lth ON l.id = lth.loanid
                WHERE l.id = :loanId;
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("loanId", loanId);
        return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
            Loanactivity data = new Loanactivity();
            data.setLoanid(rs.getLong("loanid"));
            data.setTransactionid(rs.getLong("transactionid"));
            data.setTransactiondate(rs.getDate("transactiondate"));
            data.setTransactionamount(rs.getLong("transactionamount"));
            data.setPrincipalamount(rs.getLong("principalamount"));
            data.setInterestamount(rs.getLong("interestamount"));
            data.setAmountborrowed(rs.getLong("amountborrowed"));
            data.setAmountowed(rs.getLong("amountowed"));
            data.setInterestrate(rs.getLong("interestrate"));
            data.setIsfixedrate(rs.getBoolean("isfixedrate"));
            data.setInteresttype(rs.getString("interesttype"));
            data.setRateeffectivefrom(rs.getDate("rateeffectivefrom"));
            data.setRateeffectiveto(rs.getDate("rateeffectiveto"));
            data.setRatehistoryfrom(rs.getDate("ratehistoryfrom"));
            data.setRatehistoryto(rs.getDate("ratehistoryto"));
            return data;
        });
    }

    // PORCEDURI - NE RABOTAT
//    public void addCustomer(String embg, String firstName, String lastName, LocalDate dob, String city, String address, String email, String phoneNumber) {
//        jdbcTemplate.((ConnectionCallback<Object>) connection -> {
//            log.info("{}", dob);
//            log.info("{}", phoneNumber);
//
//            CallableStatement callableStatement = connection.prepareCall("{call addcustomer(?, ?, ?, ?, ?, ?, ?, ?)}");
//            callableStatement.setString(1, embg);
//            callableStatement.setString(2, firstName);
//            callableStatement.setString(3, lastName);
//            callableStatement.setDate(4, Date.valueOf(dob));
//            callableStatement.setString(5, city);
//            callableStatement.setString(6, address);
//            callableStatement.setString(7, email);
//            callableStatement.setString(8, phoneNumber);
//            callableStatement.execute();
//            log.info("Customer added");
//            return null;
//        });
//    }
//
//    public void addAccount(String accountType, double amount, String embg, String currencyCode, int branchId) {
//        jdbcTemplate.execute((ConnectionCallback<Object>) connection -> {
//
//            CallableStatement callableStatement = connection.prepareCall("{call addaccount(?, ?, ?, ?, ?)}");
//            callableStatement.setString(1, accountType);
//            callableStatement.setDouble(2, amount);
//            callableStatement.setString(3, embg);
//            callableStatement.setString(4, currencyCode);
//            callableStatement.setInt(5, branchId);
//            callableStatement.execute();
//            log.info("Account added");
//            return null;
//        });
//    }
}
