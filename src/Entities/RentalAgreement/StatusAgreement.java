package Entities.RentalAgreement;

public enum StatusAgreement {
    NEW("New"),
    ACTIVE("Active"),
    COMPLETED("Completed");

    private final String value;

    // Constructor để gán giá trị chuỗi cho mỗi enum constant
    StatusAgreement(String value) {
        this.value = value;
    }

    // Getter để lấy giá trị chuỗi của enum constant
    public String getValue() {
        return value;
    }
}
