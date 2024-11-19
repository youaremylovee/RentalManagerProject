package Entities.Payment;

import Entities.Base.Entity;
import Utils.DateHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Payment implements Entity {
    private String paymentId;
    private double amount;
    private Date paymentDate;
    private String paymentMethod;
    private String tenantId;

    public Payment(String paymentId, double amount, Date paymentDate, String paymentMethod, String tenantId) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.tenantId = tenantId;
    }
    public Payment() {
        this.paymentId = "Default";
        this.amount = 0;
        this.paymentDate = new Date();
        this.paymentMethod = "Default";
        this.tenantId = "Default";
    }

    public String getPaymentId() {
        return paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public void fromString(String line) {
        String[] tokens = line.split(",");
        paymentId = tokens[0];
        amount = Double.parseDouble(tokens[1]);
        paymentDate = new Date(tokens[2]);
        paymentMethod = tokens[3];
        tenantId = tokens[4];
    }

    @Override
    public String toDisplay() {
        return String.format("Payment ID: %s\nAmount: %.2f\nPayment Date: %s\nPayment Method: %s\nTenant ID: %s\n", paymentId, amount, DateHandler.formatDate(paymentDate), paymentMethod, tenantId);
    }

    @Override
    public String toFile() {
        return String.format("%s,%f,%s,%s,%s", paymentId, amount,  DateHandler.formatDate(paymentDate), paymentMethod, tenantId);
    }

    @Override
    public List<String> validate() {
        List<String> errors = new ArrayList<>();
        if (paymentId == null || paymentId.isEmpty()) {
            errors.add("Payment ID cannot be empty");
        }
        if (amount < 0) {
            errors.add("Amount cannot be negative");
        }
        if (paymentDate == null) {
            errors.add("Payment Date cannot be empty");
        }
        if (paymentMethod == null || paymentMethod.isEmpty()) {
            errors.add("Payment Method cannot be empty");
        }
        if (tenantId == null || tenantId.isEmpty()) {
            errors.add("Tenant ID cannot be empty");
        }
        return errors;
    }

    @Override
    public String toString() {
        return String.format("Payment ID: %s\tAmount: %.2f\tPayment Date: %s\tPayment Method: %s", paymentId, amount, DateHandler.formatDate(paymentDate), paymentMethod);
    }
}
