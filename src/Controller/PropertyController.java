package Controller;
import Entities.Base.Entity;
import Entities.Person.Host;
import Entities.Person.Owner;
import Entities.Property.CommercialProperty;
import Entities.Property.Property;
import Entities.Property.ResidentialProperty;
import Entities.Property.Status;
import Main.Main;
import Managers.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PropertyController implements BaseController<Property> {
    private PropertyManager propertyManager ;
    private PersonManager personController ;

    public PropertyController(PropertyManager propertyManager, PersonManager personController) {
        this.propertyManager = propertyManager;
        this.personController = personController;
    }

    @Override
    public void HomePage() {
        int choice = -1;
        while(choice != 5){
            System.out.println("=== Welcome to the Property Management System ===");
            System.out.println("1. Add Property");
            System.out.println("2. View Property");
            System.out.println("3. Update Property");
            System.out.println("4. Delete Property");
            System.out.println("5. Back to main menu");
            System.out.print("Enter your choice: ");
            choice = Main.scanner.nextInt();
            switch (choice){
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
                    break;
                default:
                    System.out.println("### Invalid choice ###");
                    break;
            }
        }
    }

    @Override
    public void SortPage(List<Property> properties) {
        System.out.println("Do you want sort the list?");
        System.out.println("1. Sort by ID");
        System.out.println("2. Sort by address");
        System.out.println("3. Sort by pricing");
        System.out.println("4. Sort by status");
        System.out.println("0. No sort");
        System.out.print("Enter your choice: ");
        int choice = Main.scanner.nextInt();
        if(choice < 0 || choice > 4){
            System.out.println("### Invalid choice ###");
            return;
        }
        switch (choice){
            case 1:
                properties.sort(Comparator.comparing(Property::getPropertyId));
                break;
            case 2:
                properties.sort(Comparator.comparing(Property::getAddress));
                break;
            case 3:
                properties.sort(Comparator.comparingDouble(Property::getPricing));
                break;
            case 4:
                properties.sort(Comparator.comparing(Property::getStatus));
                break;
            case 0:
                break;
        }
    }

    @Override
    public Entity InputPage() {
        System.out.println("Which type of property do you want to add?");
        System.out.println("1. Property");
        System.out.println("2. Commercial Property");
        System.out.println("3. Residential Property");
        System.out.println("4. Back to main menu");
        System.out.print("Enter your choice: ");
        int choice = Main.scanner.nextInt();
        if(choice < 1 || choice > 4){
            System.out.println("### Invalid choice ###");
            return null;
        }
        if(choice == 4){
            return null;
        }
        Main.scanner.nextLine();
        System.out.print("Enter ID: ");
        String id = Main.scanner.nextLine();
        System.out.print("Enter the address: ");
        String address = Main.scanner.nextLine();
        System.out.print("Enter the pricing: ");
        double pricing = Main.scanner.nextDouble();
        System.out.println("Status: ");
        System.out.println("1. Available");
        System.out.println("2. Rented");
        System.out.println("3. Under Maintenance");
        System.out.print("Enter the status: ");
        int status = Main.scanner.nextInt();
        Status stt = Status.AVAILABLE;
        switch (choice){
            case 1:
                stt = Status.AVAILABLE;
                break;
            case 2:
                stt = Status.RENTED;
                break;
            case 3:
                stt = Status.UNDER_MAINTENANCE;
                break;
            default:
                System.out.println("### Invalid choice ###");
                return null;
        }
        System.out.println("=== Owner List ===");
        for (Owner o: personController.getAllOwners()){
            System.out.println("\t" + o.toString());
        }
        Main.scanner.nextLine();
        System.out.print("Enter the id of owner:");
        String ownerId = Main.scanner.nextLine();
        Owner owner = (Owner)personController.getOne(ownerId);
        if(owner == null){
            System.out.println("### Owner not found ###");
            return null;
        }
        Property p;
        switch (choice){
            case 1:
                p = new Property(id, address, pricing, stt);
                break;
            case 2:
                Main.scanner.nextLine();
                System.out.print("Enter the business type: ");
                String businessType = Main.scanner.nextLine();
                System.out.print("Enter the number of parking spaces: ");
                int parkingSpaces = Main.scanner.nextInt();
                System.out.print("Enter the square footage: ");
                double squareFootage = Main.scanner.nextDouble();
                p = new CommercialProperty(id, address, pricing, stt, businessType, parkingSpaces, squareFootage);
                break;
            case 3:
                System.out.print("Enter the number of bedrooms: ");
                int bedrooms = Main.scanner.nextInt();
                System.out.print("Is property have garden? (1/0): ");
                boolean hasGarden = Main.scanner.nextInt() == 1;
                System.out.print("Is property have pet friendly? (1/0): ");
                boolean petFriendly = Main.scanner.nextInt() == 1;
                p = new ResidentialProperty(id, address, pricing, stt, bedrooms, hasGarden, petFriendly);
                break;
            default:
                p = new Property(id, address, pricing, stt);
                return p;
        }
        owner.addProperty(p);
        //Add property for hosts
        System.out.println("=== Hosts List ===");
        for (Host o: personController.getAllHosts()){
            System.out.println("\t" + o.toString());
        }
        Main.scanner.nextLine();
        System.out.print("Enter the id of hosts (split by ,):");
        String hostIds = Main.scanner.nextLine();
        String[] ids = hostIds.split(",");
        for(String hostId: ids){
            Host host = (Host)personController.getOne(hostId);
            if(host != null){
                host.addProperty(p);
            }
        }
        return p;
    }

    @Override
    public void AddPage() {
        if(personController.getAll().isEmpty()){
            System.out.println("### Please add a person first ###");
            return;
        }
        Property p = (Property) InputPage();
        Response response = propertyManager.add(p);
        for(String msg: response.messages){
            System.out.println(msg);
        }
    }

    @Override
    public void ViewPage() {
        System.out.println("Which list do you want to view?");
        System.out.println("1. All properties");
        System.out.println("2. Available properties");
        System.out.println("3. Rented properties");
        System.out.println("4. Under maintenance properties");
        System.out.println("0. Back to main menu");
        System.out.print("Enter your choice: ");
        int choice = Main.scanner.nextInt();
        if(choice < 0 || choice > 4){
            System.out.println("### Invalid choice ###");
            return;
        }
        List<Property> properties = new ArrayList<>();
        switch (choice){
            case 1:
                properties = propertyManager.getAll();
                break;
            case 2:
                for(Property p: propertyManager.getAll()){
                    if(p.getStatus() == Status.AVAILABLE){
                        properties.add(p);
                    }
                }
                break;
            case 3:
                for(Property p: propertyManager.getAll()){
                    if(p.getStatus() == Status.RENTED){
                        properties.add(p);
                    }
                }
                break;
            case 4:
                for(Property p: propertyManager.getAll()){
                    if(p.getStatus() == Status.UNDER_MAINTENANCE){
                        properties.add(p);
                    }
                }
                break;
            case 0:
                return;
        }
        SortPage(properties);
        System.out.println("=== Property List ===");
        for(Property p: properties){
            System.out.println(p.toDisplay());
        }
    }

    @Override
    public void UpdatePage() {
        Main.scanner.nextLine();
        System.out.print("Enter the ID of the property you want to update: ");
        String id = Main.scanner.nextLine();
        Property p = propertyManager.getOne(id);
        if(p == null){
            System.out.println("### Property not found ###");
            return;
        }
        System.out.println("Which field do you want to update?");
        System.out.println("1. Address");
        System.out.println("2. Pricing");
        System.out.println("3. Status");
        System.out.println("4. Back to main menu");
        System.out.print("Enter your choice: ");
        int choice = Main.scanner.nextInt();
        if(choice < 1 || choice > 4){
            System.out.println("### Invalid choice ###");
            return;
        }
        Main.scanner.nextLine();
        switch (choice){
            case 1:
                System.out.print("Enter new address: ");
                String address = Main.scanner.nextLine();
                p.setAddress(address);
                break;
            case 2:
                System.out.print("Enter new pricing: ");
                double pricing = Main.scanner.nextDouble();
                p.setPricing(pricing);
                break;
            case 3:
                System.out.println("1. Available");
                System.out.println("2. Rented");
                System.out.println("3. Under Maintenance");
                System.out.print("Enter new status: ");
                int status = Main.scanner.nextInt();
                Status stt = Status.AVAILABLE;
                switch (status){
                    case 1:
                        stt = Status.AVAILABLE;
                        break;
                    case 2:
                        stt = Status.RENTED;
                        break;
                    case 3:
                        stt = Status.UNDER_MAINTENANCE;
                        break;
                    default:
                        System.out.println("### Invalid choice ###");
                        return;
                }
                p.setStatus(stt);
                break;
            case 4:
                return;
        }
        Response response = propertyManager.update(p);
        for(String msg: response.messages){
            System.out.println(msg);
        }
    }

    @Override
    public void DeletePage() {
        Main.scanner.nextLine();
        System.out.print("Enter the ID of the property you want to delete: ");
        String id = Main.scanner.nextLine();
        Property p = propertyManager.getOne(id);
        if(p == null){
            System.out.println("### Property not found ###");
            return;
        }
        Response response = propertyManager.delete(p);
        for(String msg: response.messages){
            System.out.println(msg);
        }
    }

    @Override
    public void SearchPage() {
        Main.scanner.nextLine();
        System.out.print("Enter the ID of the property you want to search: ");
        String id = Main.scanner.nextLine();
        Property p = propertyManager.getOne(id);
        if(p == null){
            System.out.println("### Property not found ###");
            return;
        }
        System.out.println(p.toDisplay());
    }
}
