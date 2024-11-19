package Entities.Person;

import Entities.Property.Property;
import Entities.RentalAgreement.RentalAgreement;

import java.util.*;

public class Owner extends Person {
    private List<Property> propertiesOwned = new ArrayList<>();
    private List<RentalAgreement> rentalAgreements= new ArrayList<>();
    private List<Host> managingHosts= new ArrayList<>();

    public Owner() {
        super();
    }

    public Owner(List<Property> propertiesOwned, List<RentalAgreement> rentalAgreements, List<Host> managingHosts) {
        this.propertiesOwned = propertiesOwned;
        this.rentalAgreements = rentalAgreements;
        this.managingHosts = managingHosts;
    }

    public Owner(String id, String fullName, Date dateOfBirth, String contactInfo, List<Property> propertiesOwned, List<RentalAgreement> rentalAgreements, List<Host> managingHosts) {
        super(id, fullName, dateOfBirth, contactInfo);
        this.propertiesOwned = propertiesOwned;
        this.rentalAgreements = rentalAgreements;
        this.managingHosts = managingHosts;
    }

    public List<Property> getPropertiesOwned() {
        return propertiesOwned;
    }

    public void addProperty(Property property){
        propertiesOwned.add(property);
    }

    public List<RentalAgreement> getRentalAgreements() {
        return rentalAgreements;
    }

    public List<Host> getManagingHosts() {
        return managingHosts;
    }
    public void addManagingHost(Host host){
        if(managingHosts == null){
            managingHosts = new ArrayList<>();
        }
        managingHosts.add(host);
    }

    public void setPropertiesOwned(List<Property> propertiesOwned) {
        this.propertiesOwned = propertiesOwned;
    }

    public void setRentalAgreements(List<RentalAgreement> rentalAgreements) {
        this.rentalAgreements = rentalAgreements;
    }

    public void setManagingHosts(List<Host> managingHosts) {
        this.managingHosts = managingHosts;
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
        sb.append("List of Property Owned:\n");
        for (Property p : propertiesOwned) {
            sb.append("\t" + p.toString() + "\n");
        }
        sb.append("List of Hosts:\n");
        for (Host h : managingHosts) {
            sb.append("\t" + h.toString() + "\n");
        }
        return sb.toString();
    }

    @Override
    public String toFile() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toFile());
        sb.append(",");
        sb.append("OWNER");
        return sb.toString();
    }
}
