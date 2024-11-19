package Utils;
import Entities.Property.Status;

import java.util.*;

public class RandomGenerator {
    public static<T> T randomItem (List<T> items){
        return items.get((int) (Math.random() * items.size()));
    }
    public static <T> List<T> randomItem (List<T> items, int n){
        List<T> result = new ArrayList<>();
        for (int i = 0; i < n; i++){
            result.add(randomItem(items));
        }
        return result;
    }
    public static String randomPhoneNumber() {
        int base = 100000000;
        return "0" + (int) (Math.random() * (base * 9) + base - 1);
    }
    public static String randomFullName(){
        String[] firstNames = {"John", "Alex", "Jane", "Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace"};
        String[] lastNames = {"Doe", "Smith", "Johnson", "Brown", "Wilson", "Lee", "Nguyen", "Kim", "Garcia", "Martinez"};
        String firstName = firstNames[(int) (Math.random() * firstNames.length)];
        String lastName = lastNames[(int) (Math.random() * lastNames.length)];
        return firstName + " " + lastName;
    }
    public static int randomInt(int min, int max){
        return (int) (Math.random() * (max - min + 1) + min);
    }
    public static Date randomDate(){
        int year = randomInt(1990, 2001);
        int month = randomInt(1, 12);
        int day = randomInt(1, 28);
        return DateHandler.getDate(year, month, day);
    }
    public static String randomAddress(){
        String[] streets = {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Philadelphia", "San Antonio", "San Diego", "Dallas", "San Jose"};
        String[] districts = {"District 1", "District 2", "District 3", "District 4", "District 5", "District 6", "District 7", "District 8", "District 9", "District 10"};
        String street = streets[(int) (Math.random() * streets.length)];
        String district = districts[(int) (Math.random() * districts.length)];
        return street + ", " + district;
    }
    public static Status randomStatusProperty(){
        int status = randomInt(1, 3);
        switch (status){
            case 1:
                return Status.AVAILABLE;
            case 2:
                return Status.RENTED;
            case 3:
                return Status.UNDER_MAINTENANCE;
            default:
                return Status.RENTED;
        }
    }
}
