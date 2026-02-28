package TravelAgency.services;

import TravelAgency.TravelAgency.entities.Rol;

import java.util.List;

public interface RolService{
    Rol findById(Long id);

    List<Rol> findAll();

    void create(Rol rol);

    void update(Rol rol);

    void delete(Long id);

    Rol findByName(String name);
}
