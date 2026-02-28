package TravelAgency.constante;

public interface UserConstante {
    //Validate
    String EROARE_NUME = "Campul de NUME nu trebuie sa fie gol.";
    String EROARE_PRENUME = "Campul de PRENUME nu trebuie sa fie gol.";
    String EROARE_EMAIL = "Formatul emailului este invalid!";
    String EROARE_EMAIL_BLANK = "Emailul nu trebuie sa fie gol!";
    String EROARE_PAROLA = "Parola trebuie sa contina minim 8 caractere.";
    String EROARE_PAROLA_BLANK = "Parola trebuie sa nu fie goala.";

    //Logger
    String EROARE_ID = "User-ul cu id {} nu a fost gasit in baza de date";
    String INSERT = "User-ul cu id {} a fost inserat in baza de date";
    String UPDATE = "User-ul cu id {}a fost modificat";
    String DELETE = "User-ul cu id {} a fost sters";
}
