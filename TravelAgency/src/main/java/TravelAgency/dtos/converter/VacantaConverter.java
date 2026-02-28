package TravelAgency.dtos.converter;

import TravelAgency.TravelAgency.dtos.VacantaDTO;
import TravelAgency.TravelAgency.entities.Vacanta;
import lombok.Builder;

import java.util.List;

@Builder
public class VacantaConverter {

    private VacantaConverter() {
    }

    public static VacantaDTO vacantaToVacantaDTO(Vacanta vacanta) {
        return VacantaDTO.builder()
                .withId(vacanta.getId())
                .withTipVacanta(vacanta.getTipVacanta())
                .withDestinatie(vacanta.getDestinatie())
                .withDurataZile(vacanta.getDurataZile())
                .withPret(vacanta.getPret())
                .withDescriere(vacanta.getDescriere())
                .withUser(vacanta.getUser())
                .withLocuriDisponibile(vacanta.getLocuriDisponibile())
                .build();
    }

    public static Vacanta vacantaDTOTOVacanta(VacantaDTO vacantaDTO) {
        return Vacanta.builder()
                .withId(vacantaDTO.getId())
                .withTipVacanta(vacantaDTO.getTipVacanta())
                .withDestinatie(vacantaDTO.getDestinatie())
                .withDurataZile(vacantaDTO.getDurataZile())
                .withPret(vacantaDTO.getPret())
                .withDescriere(vacantaDTO.getDescriere())
                .withUser(vacantaDTO.getUser())
                .withLocuriDisponibile(vacantaDTO.getLocuriDisponibile())
                .build();
    }

    public static List<VacantaDTO> entityListToDtoList(List<Vacanta> vacante) {
        return vacante.stream()
                .map(VacantaConverter::vacantaToVacantaDTO)
                .toList();
    }

    public static List<Vacanta> dtoListToEntityList(List<VacantaDTO> vacante) {
        return vacante.stream()
                .map(VacantaConverter::vacantaDTOTOVacanta)
                .toList();
    }
}