package Entities.Property;

import java.util.List;

public class CommercialProperty extends Property {
    private String businessType;
    private int parkingSpaces;
    private double squareFootage;

    public CommercialProperty() {
        super();
    }

    public CommercialProperty(String businessType, int parkingSpaces, double squareFootage) {
        this.businessType = businessType;
        this.parkingSpaces = parkingSpaces;
        this.squareFootage = squareFootage;
    }

    public CommercialProperty(String propertyId, String address, double pricing, Status status, String businessType, int parkingSpaces, double squareFootage) {
        super(propertyId, address, pricing, status);
        this.businessType = businessType;
        this.parkingSpaces = parkingSpaces;
        this.squareFootage = squareFootage;
    }

    public String getBusinessType() {
        return businessType;
    }

    public int getParkingSpaces() {
        return parkingSpaces;
    }

    public double getSquareFootage() {
        return squareFootage;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public void setParkingSpaces(int parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    public void setSquareFootage(double squareFootage) {
        this.squareFootage = squareFootage;
    }

    @Override
    public List<String> validate() {
        List<String> errors = super.validate();
        if (businessType == null || businessType.isEmpty()) {
            errors.add("Business type cannot be empty");
        }
        if (parkingSpaces < 0) {
            errors.add("Parking spaces cannot be negative");
        }
        if (squareFootage < 0) {
            errors.add("Square footage cannot be negative");
        }
        return errors;
    }

    @Override
    public String toFile() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("CommercialProperty,%s,%s,%.2f,%s", propertyId, address, pricing, status));
        sb.append(",");
        sb.append(businessType);
        sb.append(",");
        sb.append(parkingSpaces);
        sb.append(",");
        sb.append(squareFootage);
        return sb.toString();
    }

    @Override
    public String toDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toDisplay());
        sb.append(String.format("Business Type: %s\nParking Spaces: %d\nSquare Footage: %.2f\n", businessType, parkingSpaces, squareFootage));
        return sb.toString();
    }

    @Override
    public void fromString(String line) {
        super.fromString(line);
        String[] parts = line.split(",");
        businessType = parts[5];
        parkingSpaces = Integer.parseInt(parts[6]);
        squareFootage = Double.parseDouble(parts[7]);
    }
}
