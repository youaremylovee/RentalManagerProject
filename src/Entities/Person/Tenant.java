package Entities.Person;
import Entities.Payment.Payment;
import Entities.RentalAgreement.RentalAgreement;

import java.util.*;
public class Tenant extends Person{
    private List<RentalAgreement> rentalAgreements = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();

    public Tenant() {
        super();
    }

    public Tenant(List<RentalAgreement> rentalAgreements, List<Payment> payments) {
        this.rentalAgreements = rentalAgreements;
        this.payments = payments;
    }

    public Tenant(String id, String fullName, Date dateOfBirth, String contactInfo, List<RentalAgreement> rentalAgreements, List<Payment> payments) {
        super(id, fullName, dateOfBirth, contactInfo);
        this.rentalAgreements = rentalAgreements;
        this.payments = payments;
    }

    public List<RentalAgreement> getRentalAgreements() {
        if(rentalAgreements == null) {
            rentalAgreements = new ArrayList<>();
        }
        return rentalAgreements;
    }

    public List<Payment> getPayments() {
        if(payments == null) {
            payments = new ArrayList<>();
        }
        return payments;
    }
    public void addPayment(Payment payment) {
        if(payments == null) {
            payments = new ArrayList<>();
        }
        payments.add(payment);
    }
    public void removePayment(Payment payment) {
        if(payments == null) {
            payments = new ArrayList<>();
        }
        payments.remove(payment);
    }
    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public void setRentalAgreements(List<RentalAgreement> rentalAgreements) {
        this.rentalAgreements = rentalAgreements;
    }

    @Override
    public void fromString(String line) {
        super.fromString(line);
    }

    @Override
    public String toDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toDisplay());
        sb.append("\n");
        sb.append("List of rental agreements:\n");
        for (RentalAgreement r : rentalAgreements) {
            sb.append("\t" + r.toString() + "\n");
        }
        sb.append("List of payments:\n");
        for (Payment p : payments) {
            sb.append("\t" + p.toString() + "\n");
        }
        return sb.toString();
    }

    @Override
    public String toFile() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toFile());
        sb.append(",");
        sb.append("TENANT");
        return sb.toString();
    }

    @Override
    public List<String> validate() {
        List<String> errors = super.validate();
        if (rentalAgreements == null) {
            errors.add("Rental agreement list is null");
        } else {
            for (RentalAgreement p : rentalAgreements) {
                if (p != null) {
                    errors.addAll(p.validate());
                }
            }
        }
        if (payments == null) {
            errors.add("Payment list is null");
        } else {
            for (Payment r : payments) {
                if (r != null) {
                    errors.addAll(r.validate());
                }
            }
        }
        return errors;
    }
}
