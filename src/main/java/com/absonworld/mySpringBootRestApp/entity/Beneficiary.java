package com.absonworld.mySpringBootRestApp.entity;

public class Beneficiary {

    int cid;
    int accountId;
    int payeeAccountId;
    String payeeName;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getPayeeAccountId() {
        return payeeAccountId;
    }

    public void setPayeeAccountId(int payeeAccountId) {
        this.payeeAccountId = payeeAccountId;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    @Override
    public String toString() {
        return "Beneficiary{" +
                "cid=" + cid +
                ", accountId=" + accountId +
                ", payeeAccountId=" + payeeAccountId +
                ", payeeName='" + payeeName + '\'' +
                '}';
    }
}
