package Entities.Property;

import java.util.List;

public class ResidentialProperty extends Property {
    private int numberOfBedrooms;
    private boolean hasGarden;
    private boolean isPetFriendly;

    public ResidentialProperty() {
        super();
    }

    public ResidentialProperty(int numberOfBedrooms, boolean hasGarden, boolean isPetFriendly) {
        this.numberOfBedrooms = numberOfBedrooms;
        this.hasGarden = hasGarden;
        this.isPetFriendly = isPetFriendly;
    }

    public ResidentialProperty(String propertyId, String address, double pricing, Status status, int numberOfBedrooms, boolean hasGarden, boolean isPetFriendly) {
        super(propertyId, address, pricing, status);
        this.numberOfBedrooms = numberOfBedrooms;
        this.hasGarden = hasGarden;
        this.isPetFriendly = isPetFriendly;
    }

    public int getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    public boolean isHasGarden() {
        return hasGarden;
    }

    public boolean isPetFriendly() {
        return isPetFriendly;
    }

    public void setNumberOfBedrooms(int numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }

    public void setHasGarden(boolean hasGarden) {
        this.hasGarden = hasGarden;
    }

    public void setPetFriendly(boolean petFriendly) {
        isPetFriendly = petFriendly;
    }

    @Override
    public List<String> validate() {
        List<String> errors = super.validate();
        if (numberOfBedrooms < 0) {
            errors.add("Number of bedrooms cannot be negative");
        }
        return errors;
    }

    @Override
    public String toFile() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("ResidentialProperty,%s,%s,%.2f,%s", propertyId, address, pricing, status));
        sb.append(",");
        sb.append(numberOfBedrooms);
        sb.append(",");
        sb.append(hasGarden ? 1 : 0);
        sb.append(",");
        sb.append(isPetFriendly? 1 : 0);
        return sb.toString();
    }

    @Override
    public String toDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toDisplay());
        sb.append(String.format("Number of bedrooms: %d\n", numberOfBedrooms));
        sb.append(String.format("Has garden: %s\n", hasGarden ? "Yes" : "No"));
        sb.append(String.format("Pet friendly: %s\n", isPetFriendly ? "Yes" : "No"));
        return sb.toString();
    }

    @Override
    public void fromString(String line) {
        super.fromString(line);
        String[] parts = line.split(",");
        this.numberOfBedrooms = Integer.parseInt(parts[5]);
        this.hasGarden = Integer.parseInt(parts[6]) == 1;
        this.isPetFriendly = Integer.parseInt(parts[7]) == 1;
    }
}
