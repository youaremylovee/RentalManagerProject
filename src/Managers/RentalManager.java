package Managers;

import DAO.DAO;
import DAO.RentalAgreementDAO;
import Entities.RentalAgreement.RentalAgreement;
import Entities.RentalAgreement.StatusAgreement;

import java.util.ArrayList;
import java.util.List;

public class RentalManager implements Manager<RentalAgreement> {
    private DAO<RentalAgreement> dao;
    public RentalManager(DAO<RentalAgreement> dao) {
        this.dao = dao;
    }
    @Override
    public Response add(RentalAgreement rentalAgreement) {
        RentalAgreement old = dao.getOne(rentalAgreement.getAgreementId());
        if (old != null) {
            return new Response(false, "Rental Agreement Id already exists");
        }
        if(!rentalAgreement.validate().isEmpty()){
            return new Response(false, rentalAgreement.validate());
        }
        dao.add(rentalAgreement);
        return new Response(true, "Rental Agreement added successfully");
    }

    @Override
    public Response update(RentalAgreement rentalAgreement) {
        RentalAgreement old = dao.getOne(rentalAgreement.getAgreementId());
        if (old == null) {
            return new Response(false, "Rental Agreement Id does not exist");
        }
        if(!rentalAgreement.validate().isEmpty()){
            return new Response(false, rentalAgreement.validate());
        }
        dao.update(rentalAgreement);
        return new Response(true, "Rental Agreement updated successfully");
    }

    @Override
    public Response delete(RentalAgreement rentalAgreement) {
        RentalAgreement old = dao.getOne(rentalAgreement.getAgreementId());
        if (old == null) {
            return new Response(false, "Rental Agreement Id does not exist");
        }
        dao.delete(rentalAgreement);
        return new Response(true, "Rental Agreement deleted successfully");
    }

    @Override
    public RentalAgreement getOne(String ID) {
        return dao.getOne(ID);
    }
    @Override
    public List<RentalAgreement> getAll() {
        return dao.getAll();
    }
    public List<RentalAgreement> getByOwnerName(String ownerName){
        List<RentalAgreement> r = new ArrayList<>();
        for(RentalAgreement rentalAgreement : dao.getAll()){
            if(rentalAgreement.getOwner().getFullName().contains(ownerName)){
                r.add(rentalAgreement);
            }
        }
        return r;
    }
    public List<RentalAgreement> getByPropertyAddress(String address){
        List<RentalAgreement> r = new ArrayList<>();
        for(RentalAgreement rentalAgreement : dao.getAll()){
            if(rentalAgreement.getPropertyLeased().getAddress().equals(address)){
                r.add(rentalAgreement);
            }
        }
        return r;
    }
    public List<RentalAgreement> getByStatus(StatusAgreement status){
        List<RentalAgreement> r = new ArrayList<>();
        for(RentalAgreement rentalAgreement : dao.getAll()){
            if(rentalAgreement.getStatus().equals(status)){
                r.add(rentalAgreement);
            }
        }
        return r;
    }
}
