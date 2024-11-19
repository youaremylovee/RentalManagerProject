package Controller;

import Entities.Base.Entity;
import Entities.Payment.Payment;
import Entities.Person.Tenant;
import Main.Main;
import Managers.PaymentManager;
import Managers.PersonManager;
import Managers.Response;
import Utils.DateHandler;

import java.util.Comparator;
import java.util.List;

public class PaymentController implements BaseController<Payment> {
    private PaymentManager paymentManager;
    private PersonManager personManager;

    public PaymentController(PaymentManager paymentManager, PersonManager personManager) {
        this.paymentManager = paymentManager;
        this.personManager = personManager;
    }

    @Override
    public void HomePage() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("=== Payment Management ===");
            System.out.println("1. Add Payment");
            System.out.println("2. View Payment");
            System.out.println("3. Update Payment");
            System.out.println("4. Delete Payment");
            System.out.println("0. Back");
            System.out.println("Enter your choice: ");
            choice = Main.scanner.nextInt();
            switch (choice) {
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
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    @Override
    public void SortPage(List<Payment> list) {
        int choice = 1;
        System.out.println("Sort Payment by:");
        System.out.println("1. Payment ID");
        System.out.println("2. Payment Date");
        System.out.println("3. Payment Amount");
        System.out.println("Enter your choice: ");
        choice = Main.scanner.nextInt();
        switch (choice){
            case 1:
                list.sort(Comparator.comparing(Payment::getPaymentId));
                break;
            case 2:
                list.sort(Comparator.comparing(Payment::getPaymentDate));
                break;
            case 3:
                list.sort(Comparator.comparingDouble(Payment::getAmount));
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    @Override
    public Entity InputPage() {
        Main.scanner.nextLine();
        System.out.println("Enter Payment ID: ");
        String paymentId = Main.scanner.nextLine();
        System.out.println("Enter Payment Amount: ");
        double amount = Main.scanner.nextDouble();
        System.out.println("Enter Payment Date (dd/MM/yyyy): ");
        Main.scanner.nextLine();
        String paymentDate = Main.scanner.nextLine();
        System.out.println("Enter Payment Method: ");
        String paymentMethod = Main.scanner.nextLine();
        System.out.println("=== List of Tenants ===");
        for(Tenant tenant : personManager.getAllTenants()){
            System.out.println("\t" + tenant.toString());
        }
        System.out.println("Enter Tenant ID: ");
        String tenantId = Main.scanner.nextLine();
        Tenant tenant = (Tenant)personManager.getOne(tenantId);
        Payment payment = new Payment(paymentId, amount, DateHandler.parseDate(paymentDate), paymentMethod, tenantId);
        if(tenant != null){
            tenant.addPayment(payment);
        }
        return payment;
    }

    @Override
    public void AddPage() {
        System.out.println("=== Add Payment ===");
        Payment payment = (Payment) InputPage();
        Response response = paymentManager.add(payment);
        for(String message : response.messages){
            System.out.println("###   " + message + "   ###");
        }
    }

    @Override
    public void ViewPage() {
        List<Payment> payments = paymentManager.getAll();
        System.out.println("=== View Payment ===");
        SortPage(payments);
        for(Payment payment : payments){
            System.out.println(payment.toDisplay());
        }
    }

    @Override
    public void UpdatePage() {
        System.out.println("=== List of Payment ===");
        List<Payment> payments = paymentManager.getAll();
        for(Payment payment : payments){
            System.out.println("\t" + payment.toString());
        }
        Payment payment = (Payment) InputPage();
        Payment oldPayment = (Payment) paymentManager.getOne(payment.getPaymentId());
        Tenant oldTenant = (Tenant)personManager.getOne(oldPayment.getTenantId());
        Response response = paymentManager.update(payment);
        for(String message : response.messages){
            System.out.println("###   " + message + "   ###");
        }
        if(response.isSuccess){
            if(oldTenant != null && oldPayment != null){
                oldTenant.removePayment(oldPayment);
            }
            Tenant tenant = (Tenant)personManager.getOne(payment.getTenantId());
            if(tenant != null){
                tenant.addPayment(payment);
            }
        }
    }

    @Override
    public void DeletePage() {
        Payment payment = (Payment) InputPage();
        Response response = paymentManager.delete(payment);
        for(String message : response.messages){
            System.out.println("###   " + message + "   ###");
        }
    }

    @Override
    public void SearchPage() {

    }
}
