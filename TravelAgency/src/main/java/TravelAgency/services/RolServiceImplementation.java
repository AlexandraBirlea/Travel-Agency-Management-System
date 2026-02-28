package TravelAgency.services;

import TravelAgency.TravelAgency.entities.Rol;
import TravelAgency.TravelAgency.repositories.RolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImplementation implements RolService{

    private final RolRepository rolRepository;

    public RolServiceImplementation(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public Rol findById(Long id) {
        Optional <Rol> optionalRol = rolRepository.findById(id);

        return optionalRol.orElse(null);
    }

    @Override
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    @Override
    public void create(Rol rol) {
        rolRepository.save(rol);
    }

    @Override
    public void update(Rol rol) {
        Optional<Rol> existingRol = rolRepository.findById(rol.getId());

        if(existingRol.isEmpty()){
            return;
        }

        existingRol.get().setId(rol.getId());
        existingRol.get().setNume(rol.getNume());
    }

    @Override
    public void delete(Long id) {
        if(rolRepository.existsById(id))
            rolRepository.deleteById(id);
    }

    @Override
    public Rol findByName(String name) {
        return rolRepository.findByNume(name);
    }
}
