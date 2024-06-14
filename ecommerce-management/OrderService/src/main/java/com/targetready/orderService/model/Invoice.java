package com.targetready.orderService.model;
@Table(name = "orders")
public class Invoice {
     @Id
    @Column(name = "orderId")
    private String orderId;
    @Column(name = "amount")
    private double amount;
    @Column(name = "transactionId")
    private String transactionId;
    @Column(name = "status")
    private boolean status;
    @Column(name = "bank")
    private String bank;


    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
