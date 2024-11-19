package DAO;

import Entities.Payment.Payment;
import Entities.Person.Host;
import Entities.Person.Owner;
import Entities.Person.Person;
import Entities.Person.Tenant;
import Entities.Property.Property;
import Entities.RentalAgreement.RentalAgreement;
import Managers.PersonManager;

import java.util.ArrayList;
import java.util.List;

public class WritingList {
    public static void mapPropertyForPerson(DAO<Owner> ownerDAO, DAO<Host> hostDAO, DAO<Property> propertyDAO){
        List<String> lines = new ArrayList<>();
        for (Owner owner : ownerDAO.getAll()){
            for(Property property : owner.getPropertiesOwned()){
                lines.add(owner.getId() + "," + property.getPropertyId());
            }
        }
        for(Host host : hostDAO.getAll()){
            for(Property property : host.getPropertyManage()){
                lines.add(host.getId() + "," + property.getPropertyId());
            }
        }
        FileDAO.writeLines("PropertyMember.txt", lines);
    }
    public static void mapHostForOwner(DAO<Owner> ownerDAO, DAO<Host> hostDAO){
        List<String> lines = new ArrayList<>();
        for (Owner owner : ownerDAO.getAll()){
            for(Host host : owner.getManagingHosts()){
                lines.add(owner.getId() + "," + host.getId());
            }
        }
        FileDAO.writeLines("OwnerHost.txt", lines);
    }
    public static void mapMemberToRentalAgreement(PersonManager personManager, DAO<RentalAgreement> rentalAgreementDAO){
        List<String> lines = new ArrayList<>();
        for (RentalAgreement rentalAgreement : rentalAgreementDAO.getAll()){
            if(rentalAgreement.getMainTenant() != null){
                lines.add(rentalAgreement.getAgreementId() + "," + rentalAgreement.getMainTenant().getId() + ",MainTenant");
            }
            for(Tenant tenant : rentalAgreement.getSubTents()){
                lines.add(rentalAgreement.getAgreementId() + "," + tenant.getId() + ",SubTenant");
            }
            if(rentalAgreement.getOwner() != null){
                lines.add(rentalAgreement.getAgreementId() + "," + rentalAgreement.getOwner().getId() + ",Owner");
            }
            for(Host host : rentalAgreement.getHosts()){
                lines.add(rentalAgreement.getAgreementId() + "," + host.getId() + ",Host");
            }
        }
        FileDAO.writeLines("RentalAgreementMember.txt", lines);
    }
}
