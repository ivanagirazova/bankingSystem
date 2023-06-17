package com.example.bankingsystem.service;

import com.example.bankingsystem.views.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ViewsService {

    private final JdbcTemplate jdbcTemplate;

    public List<Allaccounttransactions> getAllAccountTransactions() {
        String sql = "SELECT * FROM public.allaccounttransactions;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
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
//        return jdbcTemplate.queryForList(sql, Allaccounttransactions.class);
    }

    //    CALL AddEmployeeToBranch('Cashier', FALSE, NULL, 1, '2102968450017');
    public void addEmployeeToBranch(String jobTitle, boolean isManager, int managedBy, int branchId, int employeeEmbg) {
        jdbcTemplate.update("CALL addemployeetobranch(?, ?, ?, ?, ?)", jobTitle, isManager, managedBy, branchId, employeeEmbg);
    }

    public List<Branchandatmlocations> getBranchAndAtmLocations() {
        String sql = "SELECT * FROM public.branchandatmlocations;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Branchandatmlocations data = new Branchandatmlocations();
            data.setLocationtype(rs.getString("locationtype"));
            data.setLocationid(rs.getLong("locationid"));
            data.setAddress(rs.getString("address"));
            return data;
        });
    }

    public List<Branchemployees> getBranchEmployees() {
        String sql = "SELECT * FROM public.branchemployees;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
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

    public List<Cardatmtransactions> getCardAtmTransactions() {
        String sql = "SELECT * FROM public.cardatmtransactions;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
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

    public List<Customeraccounts> getCustomerAccounts() {
        String sql = "SELECT * FROM public.customeraccounts;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
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

    public List<Customerinfo> getCustomerInfo() {
        String sql = "SELECT * FROM public.customerinfo;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
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

    public List<Employeecontactinfo> getEmployeeContactInfo() {
        String sql = "SELECT * FROM public.employeecontactinfo;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
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
        String sql = "SELECT * FROM public.exchangeratestoday;";
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

    public List<Foreignexchangetransactions> getForeignExchangeTransactions() {
        String sql = "SELECT * FROM public.foreignexchangetransactions;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
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

    public List<Loanactivity> getLoanActivity() {
        String sql = "SELECT * FROM public.loanactivity;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
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
    public void addCustomer(String embg, String firstName, String lastName, LocalDate dob, String city, String address, String email, String phoneNumber) {
        jdbcTemplate.execute((ConnectionCallback<Object>) connection -> {
            log.info("{}", dob);
            log.info("{}", phoneNumber);

            CallableStatement callableStatement = connection.prepareCall("{call addcustomer(?, ?, ?, ?, ?, ?, ?, ?)}");
            callableStatement.setString(1, embg);
            callableStatement.setString(2, firstName);
            callableStatement.setString(3, lastName);
            callableStatement.setDate(4, Date.valueOf(dob));
            callableStatement.setString(5, city);
            callableStatement.setString(6, address);
            callableStatement.setString(7, email);
            callableStatement.setString(8, phoneNumber);
            callableStatement.execute();
            log.info("Customer added");
            return null;
        });
    }

    public void addAccount(String accountType, double amount, String embg, String currencyCode, int branchId) {
        jdbcTemplate.execute((ConnectionCallback<Object>) connection -> {

            CallableStatement callableStatement = connection.prepareCall("{call addaccount(?, ?, ?, ?, ?)}");
            callableStatement.setString(1, accountType);
            callableStatement.setDouble(2, amount);
            callableStatement.setString(3, embg);
            callableStatement.setString(4, currencyCode);
            callableStatement.setInt(5, branchId);
            callableStatement.execute();
            log.info("Account added");
            return null;
        });
    }
}
