package DAO;

import java.util.ArrayList;
import java.util.List;
import Entities.Property.Property;
public class PropertyDAO implements DAO<Property> {
    private List<Property> properties;

    public PropertyDAO() {
        properties = FileDAO.readAllProperties();
    }
    @Override
    public void add(Property property) {
        properties.add(property);
    }
    @Override
    public void update(Property property) {
        for (int i = 0; i < properties.size(); i++) {
            if (properties.get(i).getPropertyId().equals(property.getPropertyId())) {
                properties.set(i, property);
                return;
            }
        }
    }
    @Override
    public void delete(Property property) {
        properties.remove(property);
    }
    @Override
    public List<Property> getAll() {
        return properties;
    }

    @Override
    public Property getOne(String id) {
        for (Property property : properties) {
            if (property.getPropertyId().equals(id)) {
                return property;
            }
        }
        return null;
    }

    @Override
    public void save() {
        List<String> lines = new ArrayList<>();
        for (Property property : properties) {
            lines.add(property.toFile());
        }
        FileDAO.writeLines("Property.txt", lines);
    }
}
