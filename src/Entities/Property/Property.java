package Entities.Property;


import java.util.*;

public class Property implements Entities.Base.Entity {
    protected String propertyId;
    protected String address;
    protected double pricing;
    protected Status status;

    public Property() {
        this.propertyId = "";
        this.address = "";
        this.pricing = 0;
        this.status = null;
    }

    public Property(String propertyId, String address, double pricing, Status status) {
        this.propertyId = propertyId;
        this.address = address;
        this.pricing = pricing;
        this.status = status;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public String getAddress() {
        return address;
    }

    public double getPricing() {
        return pricing;
    }

    public Status getStatus() {
        return status;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPricing(double pricing) {
        this.pricing = pricing;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    @Override
    public void fromString(String line) {
        String[] parts = line.split(",");
        this.propertyId = parts[1];
        this.address = parts[2];
        this.pricing = Double.parseDouble(parts[3]);
        this.status = statusFromStr(parts[4]);
    }

    private Status statusFromStr(String status) {
        for (Status s : Status.values()) {
            if (s.getValue().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("No enum constant for status: " + status);
    }


    @Override
    public String toDisplay() {
        return String.format("Property ID: %s\nAddress: %s\nPricing: %.2f\nStatus: %s\n", propertyId, address, pricing, status);
    }

    @Override
    public String toString() {
        return String.format("Property ID: %s\tAddress: %s\tPricing: %.2f\tStatus: %s", propertyId, address, pricing, status);
    }

    @Override
    public String toFile() {
        return String.format("Property,%s,%s,%.2f,%s", propertyId, address, pricing, status);
    }

    @Override
    public List<String> validate() {
        List<String> errors = new ArrayList<>();
        if(propertyId == null || propertyId.isEmpty()) {
            errors.add("Property ID cannot be empty");
        }
        if(address == null || address.isEmpty()) {
            errors.add("Address cannot be empty");
        }
        if(pricing < 0) {
            errors.add("Pricing cannot be negative");
        }
        if(status == null) {
            errors.add("Status cannot be empty");
        }
        return errors;
    }
}
