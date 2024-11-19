package Entities.Property;

public enum Status {
    AVAILABLE("Available"),
    RENTED("Rented"),
    UNDER_MAINTENANCE("Under_Maintenance");

    private final String value;

    // Constructor để gán giá trị chuỗi cho mỗi enum constant
    Status(String value) {
        this.value = value;
    }

    // Getter để lấy giá trị chuỗi của enum constant
    public String getValue() {
        return value;
    }
}
