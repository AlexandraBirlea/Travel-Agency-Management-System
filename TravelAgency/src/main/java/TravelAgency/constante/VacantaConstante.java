package TravelAgency.constante;

public interface VacantaConstante {
    //Validate
    String EROARE_DESTINATIE = "Este nevoie de numele destinatiei!";
    String EROARE_DURATA = "Durata trebuie sa fie de minim o zi!";
    String EROARE_PRET = "Pretul vacantei trebuie sa fie minim 100!";
    String EROARE_DESCRIERE = "Vacanta are nevoie de o descriere!";

    //Logger
    String EROARE_ID = "Vacanta cu id {} nu a fost gasita in baza de date";
    String INSERT = "Vacanta cu id {} a fost inserata in baza de date";
    String UPDATE = "Vacanta cu id {} a fost inserata in baza de date";
    String DELETE = "Vacanta cu id {} a fost stearsa";
}
