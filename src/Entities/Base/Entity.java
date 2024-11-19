package Entities.Base;
import java.util.List;

public interface Entity{
    void fromString(String line);
    String toDisplay();
    String toFile();
    List<String> validate();
}
