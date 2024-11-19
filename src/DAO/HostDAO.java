package DAO;
import Entities.Person.Host;

import java.util.ArrayList;
import java.util.List;

public class HostDAO implements DAO<Host>{
    private List<Host> hosts;
    public HostDAO() {
        hosts = FileDAO.readAllHosts();
    }
    @Override
    public void add(Host host) {
        hosts.add(host);
    }

    @Override
    public void update(Host host) {
        for (int i = 0; i < hosts.size(); i++) {
            if (hosts.get(i).getId().equals(host.getId())) {
                hosts.set(i, host);
                return;
            }
        }
    }

    @Override
    public void delete(Host host) {
        hosts.remove(host);
    }

    @Override
    public Host getOne(String id) {
        for(Host h : hosts){
            if(h.getId().equals(id)) return h;
        }
        return null;
    }

    @Override
    public List<Host> getAll() {
        return hosts;
    }

    @Override
    public void save() {
        List<String> lines = new ArrayList<>();
        for (Host host : hosts) {
            lines.add(host.toFile());
        }
        FileDAO.writeLines("Person.txt", lines);
    }
}
