package Managers;

import Entities.Person.Host;
import Entities.Person.Owner;
import Entities.Person.Person;

import java.util.ArrayList;
import java.util.List;
import DAO.*;
import Entities.Person.Tenant;

public class PersonManager implements Manager<Person> {
    private DAO<Host> hostDAO;
    private DAO<Tenant> tenantDAO;
    private DAO<Owner> ownerDAO;

    public PersonManager(DAO<Host> hostDAO, DAO<Tenant> tenantDAO, DAO<Owner> ownerDAO) {
        this.hostDAO = hostDAO;
        this.tenantDAO = tenantDAO;
        this.ownerDAO = ownerDAO;
    }

    public DAO getDAO(Object obj) {
        if (obj instanceof Host) {
            return hostDAO;
        } else if (obj instanceof Tenant) {
            return tenantDAO;
        } else if (obj instanceof Owner) {
            return ownerDAO;
        }
        return null;
    }


    @Override
    public Response add(Person person) {
        DAO<Person> dao = getDAO(person);
        Person old = dao.getOne(person.getId());
        if (old != null) {
            return new Response(false, "Person already exists");
        }
        List<String> errors = person.validate();
        if (errors.isEmpty()) {
            dao.add(person);
            return new Response(true, "Person added successfully");
        }
        else{
            return new Response(false, errors);
        }
    }

    @Override
    public Response update(Person person) {
        DAO<Person> dao = getDAO(person);
        Person old = dao.getOne(person.getId());
        if (old == null) {
            return new Response(false, "Person Id does not exist");
        }
        if(!person.validate().isEmpty()){
            return new Response(false, person.validate());
        }
        dao.update(person);
        return new Response(true, "Person updated successfully");
    }

    @Override
    public Response delete(Person person) {
        DAO<Person> dao = getDAO(person);
        Person old = dao.getOne(person.getId());
        if (old == null) {
            return new Response(false, "Person Id does not exist");
        }
        dao.delete(old);
        return new Response(true, "Person deleted successfully");
    }

    @Override
    public Person getOne(String ID) {
        List<Person> personList = new ArrayList<>();
        personList.addAll(hostDAO.getAll());
        personList.addAll(tenantDAO.getAll());
        personList.addAll(ownerDAO.getAll());
        for(Person person: personList){
            if(person.getId().equals(ID)){
                return person;
            }
        }
        return null;
    }

    @Override
    public List<Person> getAll() {
        List<Person> personList = new ArrayList<>();
        personList.addAll(hostDAO.getAll());
        personList.addAll(tenantDAO.getAll());
        personList.addAll(ownerDAO.getAll());
        return personList;
    }

    public List<Host> getAllHosts() {
        return hostDAO.getAll();
    }
    public List<Tenant> getAllTenants() {
        return tenantDAO.getAll();
    }
    public List<Owner> getAllOwners() {
        return ownerDAO.getAll();
    }
}
