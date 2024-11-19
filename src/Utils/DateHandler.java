package Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHandler {
    public static Date  parseDate(String input) {
        SimpleDateFormat inputFormatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return inputFormatter.parse(input);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String formatDate(Date date) {
        SimpleDateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
        return outputFormatter.format(date);
    }

    public static Date getDate(int year, int month, int day) {
        return new Date(year - 1900, month - 1, day);
    }
}
