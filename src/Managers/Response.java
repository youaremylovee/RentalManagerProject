package Managers;

import java.util.ArrayList;
import java.util.List;
public class Response {
    public boolean isSuccess = true;
    public List<String> messages = new ArrayList<>();

    public Response(boolean isSuccess, List<String> messages) {
        this.isSuccess = isSuccess;
        this.messages = messages;
        for(int i = 0; i < messages.size(); i++){
            messages.set(i, String.format("====================================\n%s\n====================================", messages.get(i).toUpperCase()));
        }
    }
    public Response(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        message = String.format("====================================\n%s\n====================================", message.toUpperCase());
        this.messages.add(message);
    }
}
