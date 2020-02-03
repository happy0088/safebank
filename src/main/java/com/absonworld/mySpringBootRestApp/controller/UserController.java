package com.absonworld.mySpringBootRestApp.controller;

import com.absonworld.mySpringBootRestApp.entity.*;
import com.absonworld.mySpringBootRestApp.service.H2JDBCService;
import com.absonworld.mySpringBootRestApp.util.Common;
import com.absonworld.mySpringBootRestApp.util.sendMailService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private H2JDBCService service;


    private sendMailService mailService = new sendMailService();


    private Common commonUtils = new Common();

    /**
     * Get all users list.
     *
     * @return the list
     */
    @GetMapping("/users")
    public List<String> getAllUsers(@CookieValue(name = "sessionId") String sessionId) {
        List<String> userList = new ArrayList<>();
        userList.add("Abhishek");
        userList.add("happy");
        userList.add("Lavy");
        return userList;
    }

    @PostMapping(path = "/saveUser", consumes = "application/json", produces = "application/json")
    public int saveUser(@RequestBody User userDetails) {
        boolean isMailSent = mailService.sendMail(commonUtils.getMD5(userDetails.getUserName() + userDetails.getFullName() + userDetails.getEmail()));
        int status = service.createUser(userDetails);
        service.printTableData("USER");
        return status;

    }

    @PostMapping(path = "/validate", consumes = "application/json", produces = "application/json")
    public UserSessionDetails validate(@CookieValue(name = "sessionId") String sessionId,@RequestBody User userRequest) throws JSONException {
        System.out.println("UserName is " + userRequest);
        UserSessionDetails userSessionDetails =null;
        //String userName = (String) userRequest.get("user").getAsString();
        User user = service.getUserDetails(userRequest.getUserName(), userRequest.getPassword());
        if (null != user) {
            String sessionId1 = service.createSession(user.getCid());
            AccountDetails account = service.getAccountDetails(user.getCid());
            service.printTableData("SESSION");
            userSessionDetails= new UserSessionDetails();
            userSessionDetails.setBalance(account.getBalance());
            userSessionDetails.setCid(account.getCid());
            userSessionDetails.setSessionId(sessionId1);
            return userSessionDetails;
        }
        return null;
    }

    @PostMapping(path = "/getAccountDetails", consumes = "application/json", produces = "application/json")
    public AccountDetails getAccountDetails(@CookieValue(name = "sessionId") String sessionId,@RequestBody int cid) {
        System.out.println("Customer Id is " + cid);
        System.out.println("SEssion Id is " + sessionId);
        AccountDetails account = service.getAccountDetails(cid);
        return account;

    }


    @PostMapping(path = "/addPayee", consumes = "application/json", produces = "application/json")
    public List<Beneficiary> addPayee(@CookieValue(name = "sessionId") String sessionId,@RequestBody Beneficiary beneficiaryDetails) {
        System.out.println("Payee is " + beneficiaryDetails.getPayeeName());
        AccountDetails accountDetails = getAccountDetails("",beneficiaryDetails.getCid());
        beneficiaryDetails.setAccountId(accountDetails.getAccountId());
        int rows = service.addPayee(beneficiaryDetails);
        if (rows > 0) {
            return service.getBeneficiaryDetails(beneficiaryDetails.getCid());
        } else {
            System.out.println("Something went Wrong buddy !! , Beneficiary did not get added ");
        }
        return null;

    }

    @PostMapping(path = "/fetchPayeeList", consumes = "application/json", produces = "application/json")
    public List<Beneficiary> fetchPayeeList(@CookieValue(name = "sessionId") String sessionId,@RequestBody int cid) {
        System.out.println("customer is " + cid);
        return service.getBeneficiaryDetails(cid);
    }

    @PostMapping(path = "/getfundTransferHistory", consumes = "application/json", produces = "application/json")
    public List<Transactions> getfundTransferHistory(@CookieValue(name = "sessionId") String sessionId,@RequestBody int cid) {
        System.out.println("User is " + cid);
        return service.getTransactionDetails(cid);
    }

    @PostMapping(path = "/getfundTransferHistoryByName", consumes = "application/json", produces = "application/json")
    public List<Transactions> getfundTransferHistoryByName(@CookieValue(name = "sessionId") String sessionId,@RequestBody Beneficiary beneficiary) {
        System.out.println("User is " + beneficiary.getPayeeName());
        return service.getTransactionDetailsByName(beneficiary.getPayeeName());
    }

    public User updatePasswordSimple(@RequestBody User user) {
        System.out.println("UserName is " + user.getCid());
        int count = service.updatePassword(user.getCid(), user.getPassword());
        if (count > 0) {
            return user;
        }
        return null;

    }

    @PostMapping(path = "/updatePassword", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updatePassword(@CookieValue(name = "sessionId") String sessionId,@RequestBody String user) {
        User userDetails= new User();
        System.out.println("UserName is " + user);
        StringTokenizer st = new StringTokenizer(user,"&");
        int ccid=0;
        String ppwd="";
        while(st.hasMoreTokens()){
            //userDetails.setCid
                    String cid=(st.nextToken());
                     ccid= Integer.parseInt(cid.substring(cid.indexOf("=")+1,cid.length()));
            String pwd=(st.nextToken());
             ppwd= (pwd.substring(cid.indexOf("=")+1,pwd.length()));

        }

        int count = service.updatePassword(ccid, ppwd);
        if (count > 0) {
            return userDetails.toString();
        }
        return null;

    }


    @PostMapping(path = "/transferFunds", consumes = "application/json", produces = "application/json")
    public AccountDetails transferFunds(@CookieValue(name = "sessionId") String sessionId,@RequestBody Transactions transferDetail) throws InterruptedException {
        System.out.println("UserName is " + transferDetail.getCid());
        AccountDetails accountDetails = service.getAccountDetails(transferDetail.getCid());
        int updatedAmount = accountDetails.getBalance() - transferDetail.getAmount();
        int rows = service.updateAccountBalance(updatedAmount, transferDetail.getCid());

        accountDetails = service.getAccountDetails(transferDetail.getCid());
        return accountDetails;

    }

    @PostMapping(path = "/donate", consumes = "application/json", produces = "application/json")
    public AccountDetails donate(@CookieValue(name = "sessionId") String sessionId,@RequestBody Transactions donationDetail) throws InterruptedException {
        System.out.println("UserName is " + donationDetail.getCid());

        AccountDetails accountDetails = service.getAccountDetails(donationDetail.getCid());

        int updatedAmount = accountDetails.getBalance() - donationDetail.getAmount();
        //intentional delay for 10000 ms .
        System.out.println("Going to Sleep for 10000 ms");
        Thread.sleep(20000);
        System.out.println("Back to Work!!");
        int rows = service.updateAccountBalance(updatedAmount, donationDetail.getCid());

        accountDetails = service.getAccountDetails(donationDetail.getCid());
        return accountDetails;

    }

    @PostMapping(path = "/getcustmerDetails", consumes = "application/json", produces = "application/json")
    public List<User> getcustmerDetails(@CookieValue(name = "sessionId") String sessionId) {
        System.out.println("Fetching All user details");
        return service.getAllUserDetails();
    }

}