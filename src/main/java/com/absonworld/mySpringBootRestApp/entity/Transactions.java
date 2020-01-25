package com.absonworld.mySpringBootRestApp.entity;

public class Transactions {

    int cid;
    int amount;
    int payeeAccountId;
    int accountId;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPayeeAccountId() {
        return payeeAccountId;
    }

    public void setPayeeAccountId(int payeeAccountId) {
        this.payeeAccountId = payeeAccountId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "cid=" + cid +
                ", amount=" + amount +
                ", payeeAccountId=" + payeeAccountId +
                ", accountId=" + accountId +
                '}';
    }
}
