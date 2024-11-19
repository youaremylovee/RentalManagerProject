package DAO;
import Config.PathConfig;
import Entities.Payment.Payment;
import Entities.Person.Host;
import Entities.Person.Owner;
import Entities.Person.Tenant;
import Entities.Property.CommercialProperty;
import Entities.Property.Property;
import Entities.Property.ResidentialProperty;
import Entities.RentalAgreement.RentalAgreement;

import java.io.*;
import java.util.*;

public class FileDAO {
    public static List<String> readLines(String filename){
        String filePath = "Resources/" + filename;
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                FileDAO.class.getClassLoader().getResourceAsStream(filePath)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if(line.strip().isEmpty()){
                    continue;
                }
                lines.add(line);
            }
        } catch (IOException | NullPointerException e) {
            System.err.println("Lỗi khi đọc file: " + e.getMessage());
        }
        return lines;
    }
    public static List<Tenant> readAllTenants() {
        List<String> lines = readLines("Person.txt");
        List<Tenant> result = new ArrayList<>();
        for (String line : lines) {
            String type = line.split(",")[4];
            if (type.equals("TENANT")) {
                Tenant tenant = new Tenant();
                tenant.fromString(line);
                result.add(tenant);
            }
        }
        return result;
    }
    public static List<Host> readAllHosts() {
        List<String> lines = readLines("Person.txt");
        List<Host> result = new ArrayList<>();
        for (String line : lines) {
            String type = line.split(",")[4];
            if (type.equals("HOST")) {
                Host host = new Host();
                host.fromString(line);
                result.add(host);
            }
        }
        return result;
    }
    public static List<Property> readAllProperties() {
        List<String> lines = readLines("Property.txt");
        List<Property> properties = new ArrayList<>();
        for (String line : lines) {
            String type = line.split(",")[0];
            switch (type) {
                case "ResidentialProperty":
                    ResidentialProperty residentialProperty = new ResidentialProperty();
                    residentialProperty.fromString(line);
                    properties.add(residentialProperty);
                    break;
                case "CommercialProperty":
                    CommercialProperty commercialProperty = new CommercialProperty();
                    commercialProperty.fromString(line);
                    properties.add(commercialProperty);
                    break;
                case "Property":
                    Property property = new Property();
                    property.fromString(line);
                    properties.add(property);
                    break;
            }
        }
        return properties;
    }
    public static List<RentalAgreement> readAllRentalAgreements() {
        List<RentalAgreement> rentalAgreements = new ArrayList<>();
        List<String> lines = readLines("RentalAgreement.txt");
        for (String line : lines) {
            RentalAgreement rentalAgreement = new RentalAgreement();
            rentalAgreement.fromString(line);
            rentalAgreements.add(rentalAgreement);
        }
        return rentalAgreements;
    }
    public static List<Payment> readAllPayments(){
        List<String> lines = readLines("Payment.txt");
        List<Payment> payments = new ArrayList<>();
        for (String line : lines) {
            Payment payment = new Payment();
            payment.fromString(line);
            payments.add(payment);
        }
        return payments;
    }
    public static List<Owner> readAllOwners() {
        List<String> lines = readLines("Person.txt");
        List<Owner> result = new ArrayList<>();
        for (String line : lines) {
            String type = line.split(",")[4];
            if (type.equals("OWNER")) {
                Owner owner = new Owner();
                owner.fromString(line);
                result.add(owner);
            }
        }
        return result;
    }
    public static void removeFile(String filename){
        String filePath = PathConfig.OUT_DIR + filename;
        File file = new File(filePath);
        file.delete();
    }
    public static void writeLines(String filename, List<String> lines) {
        String filePath = PathConfig.OUT_DIR + filename;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi vào file: " + e.getMessage());
        }
    }
}
