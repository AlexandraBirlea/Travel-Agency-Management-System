package TravelAgency.entities;

import lombok.Getter;

@Getter
public enum TipVacanta {
    SEJUR("Sejur"),
    EXCURSIE("Excursie"),
    CROAZIERA("Croaziera");

    private final String nume;

    TipVacanta(String nume) {
        this.nume = nume;
    }

}
