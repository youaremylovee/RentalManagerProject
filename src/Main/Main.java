package Main;
import DAO.*;
import Entities.Payment.Payment;
import Entities.Person.Person;
import Entities.Property.Property;
import Managers.*;
import Controller.PaymentController;
import Controller.PersonController;
import Controller.PropertyController;
import Controller.RentalAgreementController;
import Utils.SampleGenerator;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    private static void MainProgram(){
        //Initiate DAOs
        HostDAO hostDAO = new HostDAO();
        OwnerDAO ownerDAO = new OwnerDAO();
        TenantDAO tenantDAO = new TenantDAO();
        PropertyDAO propertyDAO = new PropertyDAO();
        RentalAgreementDAO rentalAgreementDAO = new RentalAgreementDAO();
        PaymentDAO paymentDAO = new PaymentDAO();
        //Initiate Managers
        PersonManager personManager = new PersonManager(hostDAO, tenantDAO, ownerDAO);
        PropertyManager propertyManager = new PropertyManager(propertyDAO);
        RentalManager rentalAgreementManager = new RentalManager(rentalAgreementDAO);
        PaymentManager paymentManager = new PaymentManager(paymentDAO);
        // Mapping data
        MappingList.mapPaymentToTenant(paymentDAO, tenantDAO);
        MappingList.mapHostForOwner(ownerDAO, hostDAO);
        MappingList.mapPropertyForPerson(ownerDAO, hostDAO, propertyDAO);
        MappingList.mapMemberToRentalAgreement(personManager, rentalAgreementDAO);
        MappingList.mapPropertyToRentalAgreement(propertyDAO, rentalAgreementDAO);
        //Initiate UIs
        PersonController personUI = new PersonController(personManager);
        PropertyController propertyUI = new PropertyController(propertyManager, personManager);
        PaymentController paymentUI = new PaymentController(paymentManager, personManager);
        RentalAgreementController rentalAgreementUI = new RentalAgreementController(personManager, propertyManager,rentalAgreementManager);
        int choice = -1;
        while(choice != 5){
            System.out.println("=== Welcome to the Rental Management System ===");
            System.out.println("\t1. Person");
            System.out.println("\t2. Property");
            System.out.println("\t3. Rental Agreement");
            System.out.println("\t4. Payment");
            System.out.println("\t5. Exit application");
            System.out.print("Please select an option: ");
            choice = scanner.nextInt();
            switch (choice){
                case 1:
                    personUI.HomePage();
                    break;
                case 2:
                    propertyUI.HomePage();
                    break;
                case 3:
                    rentalAgreementUI.HomePage();
                    break;
                case 4:
                    paymentUI.HomePage();
                    break;
                case 5:
                    System.out.println("### Exiting application ###");
                    System.out.print("Do you want to save data before exit? (1/0): ");
                    int saveChoice = scanner.nextInt();
                    if(saveChoice == 1){
                        //Remove old data
                        FileDAO.removeFile("OwnerHost.txt");
                        FileDAO.removeFile("Payment.txt");
                        FileDAO.removeFile("Property.txt");
                        FileDAO.removeFile("RentalAgreement.txt");
                        FileDAO.removeFile("Person.txt");
                        FileDAO.removeFile("PropertyMember.txt");
                        FileDAO.removeFile("RentalAgreementMember.txt");
                        //

                        hostDAO.save();
                        ownerDAO.save();
                        tenantDAO.save();
                        propertyDAO.save();
                        rentalAgreementDAO.save();
                        paymentDAO.save();
                        WritingList.mapHostForOwner(ownerDAO, hostDAO);
                        WritingList.mapPropertyForPerson(ownerDAO, hostDAO, propertyDAO);
                        WritingList.mapMemberToRentalAgreement(personManager, rentalAgreementDAO);
                        System.out.println("### Data saved successfully ###");
                    }
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("### Invalid choice ###");
                    break;
            }
        }
    }
    public static void main(String[] args) {
       MainProgram();
    }
    public static void testSample(){
        SampleGenerator sg = new SampleGenerator();
        List<Person> persons = sg.generateSamplePerson(1, 20);
        List<Property> properties = sg.generateSampleProperty(1, 20);
        for (Person p : persons) {
            System.out.println(p.toDisplay());
        }
    }
}
