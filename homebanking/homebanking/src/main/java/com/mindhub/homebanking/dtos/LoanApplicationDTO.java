package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {

    private Long loanTypeId;
    private int amount;
    private String payments;
    private String toAccountNumber;

    public LoanApplicationDTO() {
    }

    public Long getLoanId() {
        return loanTypeId;
    }

    public void setLoanTypeId(Long loanId) {
        this.loanTypeId = loanId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPayments() {
        return payments;
    }

    public void setPayments(String payments) {
        this.payments = payments;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }
}
