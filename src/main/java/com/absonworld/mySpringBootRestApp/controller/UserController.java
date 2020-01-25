package com.absonworld.mySpringBootRestApp.controller;

import com.absonworld.mySpringBootRestApp.entity.AccountDetails;
import com.absonworld.mySpringBootRestApp.entity.Beneficiary;
import com.absonworld.mySpringBootRestApp.entity.Transactions;
import com.absonworld.mySpringBootRestApp.entity.User;
import com.absonworld.mySpringBootRestApp.service.H2JDBCService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private H2JDBCService service;

    /**
     * Get all users list.
     *
     * @return the list
     */
    @GetMapping("/users")
    public List<String> getAllUsers() {
        List<String> userList = new ArrayList<>();
        userList.add("Abhishek");
        userList.add("happy");
        userList.add("Lavy");
        return userList;
    }

    @PostMapping(path = "/saveUser", consumes = "application/json", produces = "application/json")
    public int saveUser(@RequestBody User userDetails) {
        int status = service.createUser(userDetails);
        service.printTableData("USER");
        return status;

    }

    @PostMapping(path = "/validate", consumes = "application/json", produces = "application/json")
    public AccountDetails validate(@RequestBody User userRequest) throws JSONException {
        System.out.println("UserName is " + userRequest);
        //String userName = (String) userRequest.get("user").getAsString();
        User user = service.getUserDetails(userRequest.getUserName());
        if (null != user) {
            AccountDetails account = service.getAccountDetails(user.getCid());
            return account;
        }
        return null;
    }

    @PostMapping(path = "/getAccountDetails", consumes = "application/json", produces = "application/json")
    public AccountDetails getAccountDetails(@RequestBody int cid) {
        System.out.println("Customer Id is " + cid);
        AccountDetails account = service.getAccountDetails(cid);
        return account;

    }


    @PostMapping(path = "/addPayee", consumes = "application/json", produces = "application/json")
    public List<Beneficiary> addPayee(@RequestBody Beneficiary beneficiaryDetails) {
        System.out.println("Payee is " + beneficiaryDetails.getPayeeName());
        AccountDetails accountDetails = getAccountDetails(beneficiaryDetails.getCid());
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
    public List<Beneficiary> fetchPayeeList(@RequestBody int cid) {
        System.out.println("customer is " + cid);
        return service.getBeneficiaryDetails(cid);
    }

    @PostMapping(path = "/getfundTransferHistory", consumes = "application/json", produces = "application/json")
    public List<Transactions> getfundTransferHistory(@RequestBody int cid) {
        System.out.println("User is " + cid);
        return service.getTransactionDetails(cid);
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
    public String updatePassword(@RequestBody String user) {
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
    public AccountDetails transferFunds(@RequestBody Transactions transferDetail) throws InterruptedException {
        System.out.println("UserName is " + transferDetail.getCid());
        AccountDetails accountDetails = service.getAccountDetails(transferDetail.getCid());
        int updatedAmount = accountDetails.getBalance() - transferDetail.getAmount();
        int rows = service.updateAccountBalance(updatedAmount, transferDetail.getCid());

        accountDetails = service.getAccountDetails(transferDetail.getCid());
        return accountDetails;

    }

    @PostMapping(path = "/donate", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public AccountDetails donate(@RequestBody Transactions donationDetail) throws InterruptedException {
        System.out.println("UserName is " + donationDetail.getCid());

        AccountDetails accountDetails = service.getAccountDetails(donationDetail.getCid());

        int updatedAmount = accountDetails.getBalance() - donationDetail.getAmount();
        //intentional delay for 10000 ms .
        System.out.println("Going to Sleep for 10000 ms");
        Thread.sleep(10000);
        System.out.println("Back to Work!!");
        int rows = service.updateAccountBalance(updatedAmount, donationDetail.getCid());

        accountDetails = service.getAccountDetails(donationDetail.getCid());
        return accountDetails;

    }

}