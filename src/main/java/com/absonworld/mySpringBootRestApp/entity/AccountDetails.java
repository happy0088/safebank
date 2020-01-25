package com.absonworld.mySpringBootRestApp.entity;

public class AccountDetails {

    int cid;
    int balance;
    int accountId;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "AccountDetails{" +
                "cid=" + cid +
                ", balance=" + balance +
                ", accountId=" + accountId +
                '}';
    }
}
