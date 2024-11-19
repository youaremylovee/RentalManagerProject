package Entities.Person;

import Entities.Base.Entity;
import Utils.DateHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Person implements Entity {
    private String id;
    private String fullName;
    private Date dateOfBirth;
    private String contactInfo;

    public Person() {
        this.id = "";
        this.fullName = "";
        this.dateOfBirth = null;
        this.contactInfo = "";
    }
    public Person(String id, String fullName, Date dateOfBirth, String contactInfo) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.contactInfo = contactInfo;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
    public void fromString(String line){
        String[] data = line.split(",");
        this.id = data[0];
        this.fullName = data[1];
        this.dateOfBirth = DateHandler.parseDate(data[2]);
        this.contactInfo = data[3];
    }
    public String toDisplay() {
        return String.format("ID: %s\nFullname: %s\nBirth: %s\nContact: %s", id, fullName, DateHandler.formatDate(dateOfBirth), contactInfo);
    }

    @Override
    public String toString(){
        return String.format("ID: %s\t - FullName: %s", id, fullName);
    }

    @Override
    public String toFile() {
        return String.format("%s,%s,%s,%s", id, fullName, DateHandler.formatDate(dateOfBirth), contactInfo);
    }

    @Override
    public List<String> validate() {
        List<String> errors = new ArrayList<>();
        if (id == null || id.isEmpty()) {
            errors.add("ID is required");
        }
        if (fullName == null || fullName.isEmpty()) {
            errors.add("FullName is required");
        }
        if (dateOfBirth == null) {
            errors.add("Date of birth is required");
        }
        if (contactInfo == null || contactInfo.isEmpty()) {
            errors.add("Contact info is required");
        }
        return errors;
    }
}
