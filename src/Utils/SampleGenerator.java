package Utils;

import java.util.*;

import Entities.Payment.Payment;
import Entities.Person.Host;
import Entities.Person.Owner;
import Entities.Person.Person;
import Entities.Person.Tenant;
import Entities.Property.CommercialProperty;
import Entities.Property.Property;
import Entities.Property.ResidentialProperty;
import Entities.Property.Status;

public class SampleGenerator {
    private Person generatePerson(String id) {
        int type = RandomGenerator.randomInt(1, 3);
        Person p;
        switch (type) {
            case 1:
                p = new Host(id, RandomGenerator.randomFullName(), RandomGenerator.randomDate(),
                        RandomGenerator.randomPhoneNumber(),
                        new ArrayList<>(),
                        new ArrayList<>());
                break;
            case 2:
                p = new Owner(id, RandomGenerator.randomFullName(), RandomGenerator.randomDate(),
                        RandomGenerator.randomPhoneNumber(),
                        new ArrayList<>(),
                        new ArrayList<>(), new ArrayList<>());
                break;
            case 3:
                p = new Tenant(id, RandomGenerator.randomFullName(), RandomGenerator.randomDate(),
                        RandomGenerator.randomPhoneNumber(),
                        new ArrayList<>(),
                        new ArrayList<>());
                break;
            default:
                p = new Host(id, RandomGenerator.randomFullName(), RandomGenerator.randomDate(),
                        RandomGenerator.randomPhoneNumber(),
                        new ArrayList<>(),
                        new ArrayList<>());
        }
        return p;
    }
    public List<Person> generateSamplePerson(int startId, int endId) {
        List<Person> personList = new ArrayList<>();
        for (int i = startId; i <= endId; i++) {
            String id = "Person_" + i;
            personList.add(generatePerson(id));
        }
        return personList;
    }
    private Property generateProperty(String id) {
        int type = RandomGenerator.randomInt(1, 3);
        Property p;
        String bussinessType = RandomGenerator.randomItem(new ArrayList<>(Arrays.asList("Restaurant", "Hotel", "Office", "Store", "Warehouse")));
        switch (type) {
            case 1:
                p = new CommercialProperty(id, RandomGenerator.randomAddress(), RandomGenerator.randomInt(15, 2000),
                        RandomGenerator.randomStatusProperty(),bussinessType, RandomGenerator.randomInt(0, 10), RandomGenerator.randomInt(20, 100));
                break;
            case 2:
                boolean hasGarden = RandomGenerator.randomInt(0, 1) != 0;
                boolean isPetFriendly = RandomGenerator.randomInt(0, 1) != 0;
                p = new ResidentialProperty(id, RandomGenerator.randomAddress(), RandomGenerator.randomInt(15, 2000),
                        RandomGenerator.randomStatusProperty(), RandomGenerator.randomInt(1,4),
                        hasGarden,isPetFriendly);
                break;
            default:
                p = new Property(id, RandomGenerator.randomAddress(), RandomGenerator.randomInt(15, 2000),
                        RandomGenerator.randomStatusProperty());
        }
        return p;
    }
    public List<Property> generateSampleProperty(int startId, int endId) {
        List<Property> propertyList = new ArrayList<>();
        for (int i = startId; i <= endId; i++) {
            String id = "Property_" + i;
            Property p =
                    new Property(id, RandomGenerator.randomAddress(), RandomGenerator.randomInt(15, 2000),
                            RandomGenerator.randomStatusProperty());
        }
        return propertyList;
    }
}
