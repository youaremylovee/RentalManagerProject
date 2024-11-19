package Controller;
import Entities.Base.Entity;
import Entities.Person.Host;
import Entities.Person.Owner;
import Entities.Person.Person;
import Entities.Person.Tenant;
import Main.Main;
import Managers.PersonManager;
import Managers.Response;
import Utils.DateHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class PersonController implements BaseController<Person> {
    private PersonManager personManager ;
    public PersonController(PersonManager personManager){
        this.personManager = personManager;
    }
    public void HomePage(){
        int choice = -1;
        while(choice != 5){
            System.out.println("=== Welcome to the Person Management System ===");
            System.out.println("1. Add Person");
            System.out.println("2. View Person");
            System.out.println("3. Update Person");
            System.out.println("4. Delete Person");
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
    public void SortPage(List<Person> personList) {
        int choice = 1;
        System.out.println("Do you want sort the list?");
        System.out.println("1. Sort by ID");
        System.out.println("2. Sort by name");
        System.out.println("3. Sort by birth date");
        System.out.println("4. Sort by contact info");
        System.out.println("0. No sort");
        System.out.print("Enter your choice: ");
        choice = Main.scanner.nextInt();
        switch (choice){
            case 1:
                personList.sort(Comparator.comparing(Person::getId));
                break;
            case 2:
                personList.sort(Comparator.comparing(Person::getFullName));
                break;
            case 3:
                personList.sort(Comparator.comparing(Person::getDateOfBirth));
                break;
            case 4:
                personList.sort(Comparator.comparing(Person::getContactInfo));
                break;
            default:
                break;
        }
    }

    @Override
    public Entity InputPage() {
        System.out.println("Which type of person do you want to modifier?");
        System.out.println("1. Tenant");
        System.out.println("2. Owner");
        System.out.println("3. Host");
        System.out.println("0. Back to main menu");
        System.out.print("Enter your choice: ");
        int choice = Main.scanner.nextInt();
        if(choice == 0) return null;
        if(Main.scanner.hasNextLine()){
            Main.scanner.nextLine();
        }
        System.out.print("Enter ID: ");
        String id = Main.scanner.nextLine();
        System.out.print("Enter full name: ");
        String fullName = Main.scanner.nextLine();
        System.out.print("Enter birth (dd/MM/yyyy): ");
        String dateStr = Main.scanner.nextLine();
        Date date = DateHandler.parseDate(dateStr);
        System.out.print("Enter contact info: ");
        String contactInfo = Main.scanner.nextLine();
        Person person = null;
        switch (choice){
            case 1:
                person = new Tenant(id, fullName, date, contactInfo, new ArrayList<>(), new ArrayList<>());
                break;
            case 2:
                person = new Owner(id, fullName, date, contactInfo, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                break;
            case 3:
                person = new Host(id, fullName, date, contactInfo, new ArrayList<>(), new ArrayList<>());
                System.out.print("Enter owner manage this host (optional):");
                String ownerId = Main.scanner.nextLine();
                Owner owner = null;
                for(Person p: personManager.getAllOwners()){
                    if(p.getId().equals(ownerId)){
                        owner = (Owner) p;
                        break;
                    }
                }
                if(owner != null){
                    owner.addManagingHost((Host) person);
                }
                break;
            default:
                break;
        }
        return person;
    }

    @Override
    public void AddPage() {
        Person person = (Person) InputPage();
        if(person == null) return;
        Response response = personManager.add(person);
        for(String msg: response.messages){
            System.out.println(msg);
        }
    }

    @Override
    public void ViewPage() {
        String personType = "";
        System.out.println("Which list do you want to view?");
        System.out.println("1. Tenant list");
        System.out.println("2. Owner list");
        System.out.println("3. Host list");
        System.out.println("0. Back to main menu");
        System.out.print("Enter your choice: ");
        int choice = Main.scanner.nextInt();
        List<Person> personList = new ArrayList<>();
        switch (choice){
            case 1:
                personType = "Tenant";
                personList.addAll(personManager.getAllTenants());
                break;
            case 2:
                personType = "Owner";
                personList.addAll(personManager.getAllOwners());
                break;
            case 3:
                personType = "Host";
                personList.addAll(personManager.getAllHosts());
                break;
            default:
                return;
        }
        SortPage(personList);
        System.out.println(String.format("=== List of %s ===", personType));
        for(Person p: personList){
            System.out.println(p.toDisplay());
        }
    }

    @Override
    public void UpdatePage() {
        if(Main.scanner.hasNextLine()){
            Main.scanner.nextLine();
        }
        System.out.println("Enter ID of person you want to update: ");
        String id = Main.scanner.nextLine();
        Person person = personManager.getOne(id);
        if(person == null){
            System.out.println("==== PERSON NOT FOUND ====");
            return;
        }
        System.out.println("Enter new full name: ");
        person.setFullName(Main.scanner.nextLine());
        System.out.println("Enter new birth date (dd/MM/yyyy): ");
        person.setDateOfBirth(DateHandler.parseDate(Main.scanner.nextLine()));
        System.out.println("Enter new contact info: ");
        person.setContactInfo(Main.scanner.nextLine());
        Response response = personManager.update(person);
        for(String msg: response.messages){
            System.out.println(msg);
        }
    }

    @Override
    public void DeletePage() {
        if(Main.scanner.hasNextLine()){
            Main.scanner.nextLine();
        }
        System.out.println("Enter ID of person you want to delete: ");
        String id = Main.scanner.nextLine();
        Person person = personManager.getOne(id);
        Response response = personManager.delete(person);
        for(String msg: response.messages){
            System.out.println(msg);
        }
    }

    @Override
    public void SearchPage() {
        if(Main.scanner.hasNextLine()){
            Main.scanner.nextLine();
        }
        System.out.println("Enter ID of person you want search: ");
        String id = Main.scanner.nextLine();
        Person person = personManager.getOne(id);
        if(person == null){
            System.out.println("Person not found");
            return;
        }
        else{
            System.out.println(person.toDisplay());
        }
    }
}
