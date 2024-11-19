package Entities.RentalAgreement;

public enum PeriodAgreement {
    DAILY("Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly"),
    FORTNIGHTLY("Fortnightly");

    private final String value;

    // Constructor để gán giá trị cho mỗi enum constant
    PeriodAgreement(String value) {
        this.value = value;
    }

    // Getter để lấy giá trị của enum
    public String getValue() {
        return value;
    }
}

