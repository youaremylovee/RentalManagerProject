package DAO;
import Entities.Person.Host;
import Entities.Person.Tenant;

import java.util.ArrayList;
import java.util.List;

public class TenantDAO implements DAO<Tenant>{

    private List<Tenant> tenants;
    public TenantDAO() {
        tenants = FileDAO.readAllTenants();
    }
    @Override
    public void add(Tenant tenant) {
        tenants.add(tenant);
    }

    @Override
    public void update(Tenant tenant) {
        for (int i = 0; i < tenants.size(); i++) {
            if (tenants.get(i).getId().equals(tenant.getId())) {
                tenants.set(i, tenant);
                return;
            }
        }
    }

    @Override
    public void delete(Tenant tenant) {
        tenants.remove(tenant);
    }

    @Override
    public Tenant getOne(String id) {
        for (Tenant tenant : tenants) {
            if (tenant.getId().equals(id)) {
                return tenant;
            }
        }
        return null;
    }

    @Override
    public List<Tenant> getAll() {
        return tenants;
    }

    @Override
    public void save() {
        List<String> lines = new ArrayList<>();
        for (Tenant tenant : tenants) {
            lines.add(tenant.toFile());
        }
        FileDAO.writeLines("Person.txt", lines);
    }
}
