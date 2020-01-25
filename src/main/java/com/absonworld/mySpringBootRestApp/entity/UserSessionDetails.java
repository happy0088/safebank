package com.absonworld.mySpringBootRestApp.entity;

public class UserSessionDetails {

    int cid;
    int balance;
    String sessionId;

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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "UserSessionDetails{" +
                "cid=" + cid +
                ", balance=" + balance +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
