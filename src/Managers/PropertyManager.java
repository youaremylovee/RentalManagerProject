package Managers;

import java.util.ArrayList;
import java.util.List;
import Entities.Property.Property;
import DAO.*;

public class PropertyManager implements Manager<Property> {
    private DAO<Property> dao;

    public PropertyManager(DAO<Property> dao) {
        this.dao = dao;
    }

    @Override
    public Response add(Property property) {
        Property old = dao.getOne(property.getPropertyId());
        List<String> messages = new ArrayList<>();
        if (old != null) {
            messages.add("Property Id already exists");
            return new Response(false, messages);
        }
        if(!property.validate().isEmpty()){
            return new Response(false, property.validate());
        }
        dao.add(property);
        messages.add("Property added successfully");
        return new Response(true, messages);
    }

    @Override
    public Response update(Property property) {
        Property old = dao.getOne(property.getPropertyId());
        List<String> messages = new ArrayList<>();
        if (old == null) {
            messages.add("Property Id does not exist");
            return new Response(false, messages);
        }
        if(!property.validate().isEmpty()){
            return new Response(false, property.validate());
        }
        dao.update(property);
        messages.add("Property updated successfully");
        return new Response(true, messages);
    }

    @Override
    public Response delete(Property property) {
        Property old = dao.getOne(property.getPropertyId());
        List<String> messages = new ArrayList<>();
        if (old == null) {
            messages.add("Property Id does not exist");
            return new Response(false, messages);
        }
        dao.delete(property);
        messages.add("Property deleted successfully");
        return new Response(true, messages);
    }

    @Override
    public Property getOne(String ID) {
        return dao.getOne(ID);
    }

    @Override
    public List<Property> getAll() {
        return dao.getAll();
    }
}
