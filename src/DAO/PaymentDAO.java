package DAO;

import Entities.Payment.Payment;
import Entities.Person.Host;

import java.util.List;

public class PaymentDAO implements DAO<Payment> {
    private List<Payment> payments;
    public PaymentDAO() {
        this.payments = FileDAO.readAllPayments();
    }
    @Override
    public void add(Payment payment) {
        payments.add(payment);
    }

    @Override
    public void update(Payment payment) {
        for (int i = 0; i < payments.size(); i++) {
            if (payments.get(i).getPaymentId().equals(payment.getPaymentId())) {
                payments.set(i, payment);
                return;
            }
        }
    }

    @Override
    public void delete(Payment payment) {
        payments.remove(payment);
    }

    @Override
    public Payment getOne(String id) {
        for(Payment h : payments){
            if(h.getPaymentId().equals(id)) return h;
        }
        return null;
    }

    @Override
    public List<Payment> getAll() {
        return payments;
    }

    @Override
    public void save() {
        List<String> lines = new java.util.ArrayList<>();
        for (Payment payment : payments) {
            lines.add(payment.toFile());
        }
        FileDAO.writeLines("Payment.txt", lines);
    }
}
