package DAO;

import Entities.Person.Host;
import Entities.Person.Owner;

import java.util.ArrayList;
import java.util.List;

public class OwnerDAO implements DAO<Owner> {
    private List<Owner> owners;
    public OwnerDAO() {
        this.owners = FileDAO.readAllOwners();
    }
    @Override
    public void add(Owner owner) {
        owners.add(owner);
    }

    @Override
    public void update(Owner owner) {
        for (int i = 0; i < owners.size(); i++) {
            if (owners.get(i).getId().equals(owner.getId())) {
                owners.set(i, owner);
                return;
            }
        }
    }

    @Override
    public void delete(Owner owner) {
        owners.remove(owner);
    }

    @Override
    public Owner getOne(String ID) {
        for(Owner o : owners){
            if(o.getId().equals(ID)) return o;
        }
        return null;
    }

    @Override
    public List<Owner> getAll() {
        return owners;
    }

    @Override
    public void save() {
        List<String> lines = new ArrayList<>();
        for (Owner owner : owners) {
            lines.add(owner.toFile());
        }
        FileDAO.writeLines("Person.txt", lines);
    }
}
