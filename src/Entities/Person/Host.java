package Entities.Person;

import Entities.Property.Property;
import Entities.RentalAgreement.RentalAgreement;

import java.util.*;

public class Host extends Person {
    private List<Property> propertyManage = new ArrayList<>();
    private List<RentalAgreement> rentalAgreements = new ArrayList<>();

    public Host() {
        super();
    }

    public Host(List<Property> propertyManage, List<RentalAgreement> rentalAgreements) {
        this.propertyManage = propertyManage;
        this.rentalAgreements = rentalAgreements;
    }

    public Host(String id, String fullName, Date dateOfBirth, String contactInfo, List<Property> propertyManage, List<RentalAgreement> rentalAgreements) {
        super(id, fullName, dateOfBirth, contactInfo);
        this.propertyManage = propertyManage;
        this.rentalAgreements = rentalAgreements;
    }

    public List<Property> getPropertyManage() {
        return propertyManage;
    }

    public List<RentalAgreement> getRentalAgreements() {
        return rentalAgreements;
    }

    public void setPropertyManage(List<Property> propertyManage) {
        this.propertyManage = propertyManage;
    }

    public void addProperty(Property property) {
        if(propertyManage == null) {
            propertyManage = new ArrayList<>();
        }
        propertyManage.add(property);
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
        sb.append("List of Property Owned:\n");
        for (Property p : propertyManage) {
            sb.append("\t" + p.toString() + "\n");
        }
        return sb.toString();
    }

    @Override
    public String toFile() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toFile());
        sb.append(",");
        sb.append("HOST");
        return sb.toString();
    }

    @Override
    public List<String> validate() {
        List<String> errors = super.validate();
        if (propertyManage == null) {
            errors.add("Property list is null");
        } else {
            for (Property p : propertyManage) {
                if (p != null) {
                    errors.addAll(p.validate());
                }
            }
        }
        if (rentalAgreements == null) {
            errors.add("Rental agreement list is null");
        } else {
            for (RentalAgreement r : rentalAgreements) {
                if (r != null) {
                    errors.addAll(r.validate());
                }
            }
        }

        return errors;
    }

    @Override
    public String toString() {
        return String.format("Host ID: %s\tFullname: %s\tContact: %s", getId(), getFullName(), getContactInfo());
    }
}
