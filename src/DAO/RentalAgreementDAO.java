package DAO;

import java.util.*;
import Entities.RentalAgreement.RentalAgreement;

public class RentalAgreementDAO implements DAO<RentalAgreement> {
    private List<RentalAgreement> rentalAgreements ;
    public RentalAgreementDAO() {
        rentalAgreements = FileDAO.readAllRentalAgreements();
    }
    @Override
    public void add(RentalAgreement rentalAgreement) {
        rentalAgreements.add(rentalAgreement);
    }

    @Override
    public void update(RentalAgreement rentalAgreement) {
        for(int i = 0; i < rentalAgreements.size(); i++) {
            if(rentalAgreements.get(i).getAgreementId().equals(rentalAgreement.getAgreementId())) {
                rentalAgreements.set(i, rentalAgreement);
                break;
            }
        }
    }

    @Override
    public void delete(RentalAgreement rentalAgreement) {
        rentalAgreements.remove(rentalAgreement);
    }

    @Override
    public RentalAgreement getOne(String id) {
        for (RentalAgreement agreement : rentalAgreements) {
            if (agreement.getAgreementId().equals(id)) {
                return agreement;
            }
        }
        return null;
    }

    @Override
    public List<RentalAgreement> getAll() {
        return rentalAgreements;
    }

    @Override
    public void save() {
        List<String> lines = new ArrayList<>();
        for (RentalAgreement agreement : rentalAgreements) {
            lines.add(agreement.toFile());
        }
        FileDAO.writeLines("RentalAgreement.txt", lines);
    }
}
