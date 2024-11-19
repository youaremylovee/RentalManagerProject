package Controller;

import Entities.Base.Entity;
import Entities.Person.Host;
import Entities.Person.Owner;
import Entities.Person.Tenant;
import Entities.Property.Property;
import Entities.RentalAgreement.PeriodAgreement;
import Entities.RentalAgreement.RentalAgreement;
import Entities.RentalAgreement.StatusAgreement;
import Main.Main;
import Managers.PersonManager;
import Managers.PropertyManager;
import Managers.RentalManager;
import Managers.Response;
import Utils.DateHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RentalAgreementController implements BaseController<RentalAgreement> {
    private PersonManager personManager;
    private PropertyManager propertyManager;
    private RentalManager rentalManager;

    public RentalAgreementController(PersonManager personManager, PropertyManager propertyManager, RentalManager rentalManager) {
        this.personManager = personManager;
        this.propertyManager = propertyManager;
        this.rentalManager = rentalManager;
    }

    @Override
    public void HomePage() {
        int choice = -1;
        while(choice != 6){
            System.out.println("=== Welcome to the Rental Agreement Management System ===");
            System.out.println("\t1. Add Rental Agreement");
            System.out.println("\t2. View Rental Agreement");
            System.out.println("\t3. Update Rental Agreement");
            System.out.println("\t4. Delete Rental Agreement");
            System.out.println("\t5. Search Rental Agreement");
            System.out.println("\t6. Back To Main Menu");
            System.out.print("Please select an option: ");
            choice= Main.scanner.nextInt();
            switch(choice){
                case 1:
                    AddPage();
                    break;
                case 2:
                    ViewPage();
                    break;
                case 3:
                    UpdatePage();
                    break;
                case 4:
                    DeletePage();
                    break;
                case 5:
                    SearchPage();
                    break;
                case 6:
                    choice = 6;
                    break;
                default:
                    System.out.println("### Invalid choice, please try again ###");
                    break;
            }
        }
    }

    @Override
    public Entity InputPage() {
        Main.scanner.nextLine();
        //Input rental agreement
        System.out.println("=== Rental Agreement ===");
        System.out.println("Enter Rental Agreement ID: ");
        String rentalAgreementID = Main.scanner.nextLine();
        //Input main tenant
        System.out.println("List of Tenants: ");
        for(Tenant tenant : personManager.getAllTenants()){
            System.out.println("\t" + tenant.toString());
        }
        System.out.println("Enter Main Tenant ID: ");
        String mainTenantID = Main.scanner.nextLine();
        Tenant mainTenant = (Tenant)personManager.getOne(mainTenantID);
        //Input property
        System.out.println("List of Properties: ");
        for(Property property : propertyManager.getAll()){
            System.out.println("\t" + property.toString());
        }
        System.out.println("Enter Property ID: ");
        String propertyID = Main.scanner.nextLine();
        Property property = propertyManager.getOne(propertyID);
        //Input owner
        Owner owner = null;
        if(property != null){
            for(Owner o : personManager.getAllOwners()){
                for(Property p : o.getPropertiesOwned()){
                    if(p.getPropertyId().equals(propertyID)){
                        owner = o;
                        break;
                    }
                }
            }
        }
        // Input date
        System.out.println("Enter Start Date (dd/MM/yyyy): ");
        String startDate = Main.scanner.nextLine();
        System.out.println("Enter End Date (dd/MM/yyyy): ");
        String endDate = Main.scanner.nextLine();
        System.out.println("Enter Rent Amount: ");
        double rentAmount = Main.scanner.nextDouble();
        //Input period
        System.out.println("Which type of Period Agreement");
        System.out.println("\t1. Daily");
        System.out.println("\t2. Weekly");
        System.out.println("\t3. Monthly");
        System.out.println("\t4. Fortnightly");
        System.out.print("Enter your choice: ");
        int periodType = Main.scanner.nextInt();
        PeriodAgreement period ;
        switch (periodType){
            case 1:
                period = PeriodAgreement.DAILY;
                break;
            case 2:
                period = PeriodAgreement.WEEKLY;
                break;
            case 3:
                period = PeriodAgreement.MONTHLY;
                break;
            case 4:
                period = PeriodAgreement.FORTNIGHTLY;
                break;
            default:
                period = PeriodAgreement.DAILY;
                break;
        }
        // Input status
        System.out.println("Enter Status of Rental Agreement: ");
        System.out.println("\t1. New");
        System.out.println("\t2. Active");
        System.out.println("\t3. Completed");
        System.out.print("Enter your choice: ");
        int status = Main.scanner.nextInt();
        StatusAgreement statusAgreement;
        switch(status){
            case 1:
                statusAgreement = StatusAgreement.NEW;
                break;
            case 2:
                statusAgreement = StatusAgreement.ACTIVE;
                break;
            case 3:
                statusAgreement = StatusAgreement.COMPLETED;
                break;
            default:
                statusAgreement = StatusAgreement.NEW;
                break;
        }
        // Input list of tenants and hosts
        Main.scanner.nextLine();
        List<Host> listHost = personManager.getAllHosts();
        System.out.println("List of Hosts: ");
        for(Host host : listHost){
            System.out.println(host.toString());
        }
        System.out.println("Enter Host ID (Split by \",\" character): ");
        String hostIDs = Main.scanner.nextLine();
        String[] hostIDList = hostIDs.split(",");
        List<Host> hostsInRA = new ArrayList<>();
        for(String hostID : hostIDList){
            Host host = (Host)personManager.getOne(hostID);
            if(host != null){
                hostsInRA.add(host);
            }
        }
        List<Tenant> listTenant = personManager.getAllTenants();
        System.out.println("List of Tenants: ");
        for(Tenant tenant : listTenant){
            System.out.println(tenant.toString());
        }
        System.out.println("Enter List SubTenant ID (Split by \",\" character): ");
        String tenantIDs = Main.scanner.nextLine();
        String[] tenantIDList = tenantIDs.split(",");
        List<Tenant> tenantsInRA = new ArrayList<>();
        for(String tenantID : tenantIDList){
            Tenant tenant = (Tenant)personManager.getOne(tenantID);
            if(tenant != null){
                tenantsInRA.add(tenant);
            }
        }
        RentalAgreement rentalAgreement = new RentalAgreement(rentalAgreementID, owner, mainTenant,
                tenantsInRA, hostsInRA, property, DateHandler.parseDate(startDate), DateHandler.parseDate(endDate), period, rentAmount, statusAgreement);
        return rentalAgreement;
    }

    @Override
    public void AddPage() {
        RentalAgreement rentalAgreement = (RentalAgreement) InputPage();
        Response response = rentalManager.add(rentalAgreement);
        for(String msg: response.messages){
            System.out.println(msg);
        }
    }

    @Override
    public void ViewPage() {
        List<RentalAgreement> rentalAgreements = rentalManager.getAll();
        SortPage(rentalAgreements);
        System.out.println("=== List Rental Agreement ===");
        for(RentalAgreement ra : rentalAgreements){
            System.out.println(ra.toDisplay());
        }
    }

    @Override
    public void UpdatePage() {
        RentalAgreement rentalAgreement = (RentalAgreement) InputPage();
        Response response = rentalManager.update(rentalAgreement);
        System.out.println("=== Update Rental Agreement ===");
        for(String msg: response.messages){
            System.out.println(msg);
        }
    }

    @Override
    public void DeletePage() {
        ViewPage();
        System.out.println("Enter Rental Agreement ID to delete: ");
        String rentalAgreementID = Main.scanner.nextLine();
        RentalAgreement rentalAgreement = rentalManager.getOne(rentalAgreementID);
        if(rentalAgreement == null){
            System.out.println("Rental Agreement not found");
            return;
        }
        Response response = rentalManager.delete(rentalAgreement);
        System.out.println("=== Delete Rental Agreement ===");
        for(String msg: response.messages){
            System.out.println(msg);
        }
    }

    @Override
    public void SortPage(List<RentalAgreement> entities) {
        System.out.println("Which type do you want sort ?: ");
        System.out.println("\t1. By Id");
        System.out.println("\t2. By Start Date");
        System.out.println("\t3. By End Date");
        System.out.print("Enter your choice: ");
        int choice = Main.scanner.nextInt();
        switch (choice){
            case 1:
                entities.sort(Comparator.comparing(RentalAgreement::getAgreementId));
                break;
            case 2:
                entities.sort(Comparator.comparing(RentalAgreement::getRentalStartDate));
                break;
            case 3:
                entities.sort(Comparator.comparing(RentalAgreement::getRentalEndDate));
                break;
            default:
                entities.sort(Comparator.comparing(RentalAgreement::getAgreementId));
                break;
        }
    }

    @Override
    public void SearchPage() {
        Main.scanner.nextLine();
        System.out.println("Which type do you want search ?: ");
        System.out.println("\t1. By Owner Name");
        System.out.println("\t2. By Property Address");
        System.out.println("\t3. By Status");
        System.out.print("Enter your choice: ");
        int choice = Main.scanner.nextInt();
        switch (choice){
            case 1:
                Main.scanner.nextLine();
                System.out.println("Enter Owner Name: ");
                String ownerName = Main.scanner.nextLine();
                List<RentalAgreement> ra = rentalManager.getByOwnerName(ownerName);
                if(!ra.isEmpty()){
                    SortPage(ra);
                    System.out.print("=== Rental Agreement found ===");
                    for(RentalAgreement ra1 : ra){
                        System.out.println("\t" + ra1.toString());
                    }
                }else{
                    System.out.println("=== Rental Agreement not found ===");
                }
                break;
            case 2:
                Main.scanner.nextLine();
                System.out.println("Enter Property Address: ");
                String address = Main.scanner.nextLine();
                List<RentalAgreement> ra2 = rentalManager.getByPropertyAddress(address);
                if(!ra2.isEmpty()){
                    SortPage(ra2);
                    System.out.print("=== Rental Agreement found ===");
                    for(RentalAgreement ra1 : ra2){
                        System.out.println("\t" + ra1.toString());
                    }
                }else{
                    System.out.println("=== Rental Agreement not found ===");
                }
                break;
            case 3:
                System.out.println("Enter Status: ");
                System.out.println("\t1. New");
                System.out.println("\t2. Active");
                System.out.println("\t3. Completed");
                int status = Main.scanner.nextInt();
                StatusAgreement statusAgreement;
                switch(status){
                    case 1:
                        statusAgreement = StatusAgreement.NEW;
                        break;
                    case 2:
                        statusAgreement = StatusAgreement.ACTIVE;
                        break;
                    case 3:
                        statusAgreement = StatusAgreement.COMPLETED;
                        break;
                    default:
                        statusAgreement = StatusAgreement.NEW;
                        break;
                }
                List<RentalAgreement> result = rentalManager.getByStatus(statusAgreement);
                if(result.size() > 0){
                    SortPage(result);
                    System.out.print("=== Rental Agreement found ===");
                    for(RentalAgreement ra3 : result){
                        System.out.println(ra3.toString());
                    }
                }else{
                    System.out.println("=== Rental Agreement not found ===");
                }
                break;
            default:
                System.out.println("### Invalid choice ###");
                break;
        }
    }
}
