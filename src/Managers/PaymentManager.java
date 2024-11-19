package Managers;

import DAO.DAO;
import Entities.Payment.Payment;

import java.util.List;

public class PaymentManager implements Manager<Payment>{
    private DAO<Payment> dao;
    public PaymentManager(DAO<Payment> dao) {
        this.dao = dao;
    }
    @Override
    public Response add(Payment payment) {
        Payment old = dao.getOne(payment.getPaymentId());
        if (old != null) {
            return new Response(false, "Payment Id already exists");
        }
        if(!payment.validate().isEmpty()){
            return new Response(false, payment.validate());
        }
        dao.add(payment);
        return new Response(true, "Payment added successfully");
    }

    @Override
    public Response update(Payment payment) {
        Payment old = dao.getOne(payment.getPaymentId());
        if (old == null) {
            return new Response(false, "Payment Id does not exist");
        }
        if(!payment.validate().isEmpty()){
            return new Response(false, payment.validate());
        }
        dao.update(payment);
        return new Response(true, "Payment updated successfully");
    }

    @Override
    public Response delete(Payment payment) {
        Payment old = dao.getOne(payment.getPaymentId());
        if (old == null) {
            return new Response(false, "Payment Id does not exist");
        }
        dao.delete(payment);
        return new Response(true, "Payment deleted successfully");
    }

    @Override
    public Payment getOne(String ID) {
        return dao.getOne(ID);
    }

    @Override
    public List<Payment> getAll() {
        return dao.getAll();
    }
}
