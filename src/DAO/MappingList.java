package DAO;

import Entities.Payment.Payment;
import Entities.Person.Host;
import Entities.Person.Owner;
import Entities.Person.Person;
import Entities.Person.Tenant;
import Entities.Property.Property;
import Entities.RentalAgreement.RentalAgreement;
import Managers.PersonManager;

import java.util.List;

public class MappingList {
    public static void mapPaymentToTenant(DAO<Payment> paymentDAO, DAO<Tenant> tenantDAO){
        for (Payment payment : paymentDAO.getAll()){
            for (Tenant tenant : tenantDAO.getAll()){
                if (payment.getTenantId() == tenant.getId()){
                    tenant.addPayment(payment);
                }
            }
        }
    }
    public static void mapPropertyForPerson(DAO<Owner> ownerDAO, DAO<Host> hostDAO, DAO<Property> propertyDAO){
        List<String> lines = FileDAO.readLines("PropertyMember.txt");
        for (String line : lines){
            String[] data = line.split(",");
            String personId = data[0];
            String propertyId = data[1];
            for (Owner owner : ownerDAO.getAll()){
                Property property = propertyDAO.getOne(propertyId);
                if (owner.getId().equals(personId) && property != null){
                    owner.addProperty(property);
                }
            }
            for (Host host : hostDAO.getAll()){
                if (host.getId() == personId && propertyDAO.getOne(propertyId) != null){
                    host.addProperty(propertyDAO.getOne(propertyId));
                }
            }
        }
    }
    public static void mapHostForOwner(DAO<Owner> ownerDAO, DAO<Host> hostDAO){
        List<String> lines = FileDAO.readLines("OwnerHost.txt");
        for (String line : lines){
            String[] data = line.split(",");
            String ownerId = data[0];
            String hostId = data[1];
            for (Owner owner : ownerDAO.getAll()){
                if (owner.getId().equals(ownerId) && hostDAO.getOne(hostId) != null){
                    owner.addManagingHost(hostDAO.getOne(hostId));
                }
            }
        }
    }
    public static void mapMemberToRentalAgreement(PersonManager personManager, DAO<RentalAgreement> rentalAgreementDAO){
        List<String> lines = FileDAO.readLines("RentalAgreementMember.txt");
        for (String line : lines){
            String[] data = line.split(",");
            String rentalAgreementId = data[0];
            String personId = data[1];
            String type = data[2];
            RentalAgreement rentalAgreement = rentalAgreementDAO.getOne(rentalAgreementId);
            Person person = personManager.getOne(personId);
            if(rentalAgreement == null || person == null){
                continue;
            }
            switch (type){
                case "MainTenant":
                    rentalAgreement.setMainTenant((Tenant) person);
                    break;
                case "SubTenant":
                    rentalAgreement.addSubTenant((Tenant) person);
                    break;
                case "Owner":
                    rentalAgreement.setOwner((Owner) person);
                    break;
                case "Host":
                    rentalAgreement.addHost((Host) person);
                    break;
            }
        }
    }
    public static void mapPropertyToRentalAgreement(DAO<Property> propertyDAO, DAO<RentalAgreement> rentalAgreementDAO){
        for(RentalAgreement rentalAgreement : rentalAgreementDAO.getAll()){
            for(Property property : propertyDAO.getAll()){
                if(rentalAgreement.getPropertyLeased().getPropertyId().equals(property.getPropertyId())){
                    rentalAgreement.setPropertyLeased(property);
                }
            }
        }
    }
}
