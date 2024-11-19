package Entities.RentalAgreement;

import Entities.Base.Entity;
import Entities.Person.Host;
import Entities.Person.Owner;
import Entities.Person.Tenant;
import Entities.Property.Property;
import Utils.DateHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RentalAgreement implements Entity {
    private String agreementId; 
    private Owner owner;
    private Tenant mainTenant;
    private List<Tenant> subTents = new ArrayList<>();
    private List<Host> hosts = new ArrayList<>();
    private Property propertyLeased;
    private Date rentalStartDate;
    private Date rentalEndDate;
    private PeriodAgreement rentalPeriod;
    private double rentalFee;
    private StatusAgreement status;

    public RentalAgreement() {
        this.agreementId = "Default";
        this.owner = null;
        this.mainTenant = null;
        this.propertyLeased = null;
        this.rentalStartDate = null;
        this.rentalEndDate = null;
        this.rentalPeriod = null;
        this.rentalFee = 0;
        this.status = null;
    }

    public RentalAgreement(String agreementId, Owner owner, Tenant mainTenant, List<Tenant> subTents, List<Host> hosts, Property propertyLeased, Date rentalStartDate, Date rentalEndDate, PeriodAgreement rentalPeriod, double rentalFee, StatusAgreement status) {
        this.agreementId = agreementId;
        this.owner = owner;
        this.mainTenant = mainTenant;
        this.subTents = subTents;
        this.hosts = hosts;
        this.propertyLeased = propertyLeased;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
        this.rentalPeriod = rentalPeriod;
        this.rentalFee = rentalFee;
        this.status = status;
    }

    public Owner getOwner() {
        return owner;
    }

    public Tenant getMainTenant() {
        return mainTenant;
    }

    public List<Tenant> getSubTents() {
        return subTents;
    }

    public List<Host> getHosts() {
        return hosts;
    }
    public void addHost(Host host){
        if(this.hosts == null){
            this.hosts = new ArrayList<>();
        }
        this.hosts.add(host);
    }

    public Property getPropertyLeased() {
        return propertyLeased;
    }

    public String getAgreementId() {
        return agreementId;
    }

    public Date getRentalStartDate() {
        return rentalStartDate;
    }

    public Date getRentalEndDate() {
        return rentalEndDate;
    }

    public PeriodAgreement getRentalPeriod() {
        return rentalPeriod;
    }

    public double getRentalFee() {
        return rentalFee;
    }

    public StatusAgreement getStatus() {
        return status;
    }

    public void setAgreementId(String agreementId) {
        this.agreementId = agreementId;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setMainTenant(Tenant mainTenant) {
        this.mainTenant = mainTenant;
    }

    public void setSubTents(List<Tenant> subTents) {
        this.subTents = subTents;
    }
    public void addSubTenant(Tenant tenant){
        if(this.subTents == null){
            this.subTents = new ArrayList<>();
        }
        this.subTents.add(tenant);
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }

    public void setPropertyLeased(Property propertyLeased) {
        this.propertyLeased = propertyLeased;
    }

    public void setRentalStartDate(Date rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public void setRentalEndDate(Date rentalEndDate) {
        this.rentalEndDate = rentalEndDate;
    }

    public void setRentalPeriod(PeriodAgreement rentalPeriod) {
        this.rentalPeriod = rentalPeriod;
    }

    public void setRentalFee(double rentalFee) {
        this.rentalFee = rentalFee;
    }

    public void setStatus(StatusAgreement status) {
        this.status = status;
    }

    @Override
    public void fromString(String line) {
        String[] data = line.split(",");
        this.agreementId = data[0];
        this.propertyLeased = new Property();
        this.propertyLeased.setPropertyId(data[1]);
        this.rentalStartDate = DateHandler.parseDate(data[2]);
        this.rentalEndDate = DateHandler.parseDate(data[3]);
        this.rentalPeriod = p(data[4]);
        this.rentalFee = Double.parseDouble(data[5]);
        this.status = s(data[6]);
    }

    public PeriodAgreement p(String period) {
        for (PeriodAgreement p : PeriodAgreement.values()) {
            if (p.getValue().equalsIgnoreCase(period)) {
                return p;
            }
        }
        throw new IllegalArgumentException("No enum constant for period: " + period);
    }

    public StatusAgreement s(String status) {
        for (StatusAgreement s : StatusAgreement.values()) {
            if (s.getValue().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("No enum constant for status: " + status);
    }

    @Override
    public String toDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Agreement ID: %s\n", agreementId));
        sb.append(String.format("Owner: %s\n", owner.getFullName()));
        sb.append(String.format("Main Tenant: %s\n", mainTenant.getFullName()));
        sb.append("Sub Tenants:\n");
        for (Tenant tenant : subTents) {
            sb.append(String.format("\t - %s\n", tenant.getFullName()));
        }
        sb.append("Hosts:\n");
        for (Host host : hosts) {
            sb.append(String.format("\t - %s\n", host.getFullName()));
        }
        sb.append(String.format("Property Leased: %s - %s\n", propertyLeased.getPropertyId(), propertyLeased.getAddress()));
        sb.append(String.format("Rental Time: %s - %s \n", DateHandler.formatDate(rentalStartDate), DateHandler.formatDate(rentalEndDate)));
        sb.append(String.format("Rental Period: %s\n", rentalPeriod.toString()));
        sb.append(String.format("Rental Fee: %s\n", rentalFee));
        sb.append(String.format("Status: %s\n", status.toString()));
        return sb.toString();
    }

    @Override
    public String toFile() {
        return String.format("%s,%s,%s,%s,%s,%s,%s",
                agreementId, propertyLeased.getPropertyId(),
                DateHandler.formatDate(rentalStartDate), DateHandler.formatDate(rentalEndDate), rentalPeriod.toString(), rentalFee, status.toString());
    }

    private String toRentalAgreementMember(String type, String idPerson){
        return String.format("%s,%s,%s", idPerson, agreementId,type);
    }
    public List<String> toRentalAgreementMember(){
        List<String> result = new ArrayList<>();
        for(Tenant tenant : subTents){
            result.add(toRentalAgreementMember("subtenant", tenant.getId()));
        }
        for(Host host : hosts){
            result.add(toRentalAgreementMember("host", host.getId()));
        }
        result.add(toRentalAgreementMember("mainTenant", mainTenant.getId()));
        result.add(toRentalAgreementMember("owner", owner.getId()));
        return result;
    }

    @Override
    public List<String> validate() {
        List<String> errors = new ArrayList<>();
        if (agreementId == null || agreementId.isEmpty()) {
            errors.add("Agreement ID is required");
        }
        if (owner == null) {
            errors.add("Owner is required");
        }
        if (mainTenant == null) {
            errors.add("Main Tenant is required");
        }
        if (propertyLeased == null) {
            errors.add("Property Leased is required");
        }
        if (rentalStartDate == null) {
            errors.add("Rental Start Date is required");
        }
        if (rentalEndDate == null) {
            errors.add("Rental End Date is required");
        }
        if (rentalPeriod == null) {
            errors.add("Rental Period is required");
        }
        if (rentalFee < 0) {
            errors.add("Rental Fee must be positive");
        }
        if (status == null) {
            errors.add("Status is required");
        }
        return errors;
    }

    @Override
    public String toString() {
        return String.format("Agreement ID: %s\tOwner: %s\tMain Tenant: %s\tProperty Leased: %s\tRental Time: %s - %s\tRental Period: %s\tRental Fee: %s\tStatus: %s",
                agreementId, owner.getFullName(), mainTenant.getFullName(), propertyLeased.getAddress(), DateHandler.formatDate(rentalStartDate), DateHandler.formatDate(rentalEndDate), rentalPeriod.toString(), rentalFee, status.toString());
    }
}
