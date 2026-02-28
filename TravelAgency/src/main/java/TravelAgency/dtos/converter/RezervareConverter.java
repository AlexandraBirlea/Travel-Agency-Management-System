package TravelAgency.dtos.converter;

import TravelAgency.TravelAgency.dtos.RezervareDTO;
import TravelAgency.TravelAgency.entities.Rezervare;

import java.util.List;

public class RezervareConverter {

    private RezervareConverter() {
    }

    public static RezervareDTO rezervareToRezervareDTO(Rezervare rezervare) {
        return RezervareDTO.builder()
                .withId(rezervare.getId())
                .withUtilizator(rezervare.getUtilizator())
                .withVacantaDTO(VacantaConverter.vacantaToVacantaDTO(rezervare.getVacanta()))
                .withDataRezervare(rezervare.getDataRezervare())
                .withNumarPersoane(rezervare.getNumarPersoane())
                .build();
    }

    public static Rezervare rezervareDTOToRezervare(RezervareDTO rezervareDTO) {
        return Rezervare.builder()
                .withId(rezervareDTO.getId())
                .withUtilizator(rezervareDTO.getUtilizator())
                .withVacanta(VacantaConverter.vacantaDTOTOVacanta(rezervareDTO.getVacantaDTO()))
                .withDataRezervare(rezervareDTO.getDataRezervare())
                .withNumarPersoane(rezervareDTO.getNumarPersoane())
                .build();
    }

    public static List<RezervareDTO> entityToDtoList(List<Rezervare> rezervari) {
        return rezervari.stream()
                .map(RezervareConverter::rezervareToRezervareDTO)
                .toList();
    }
}
