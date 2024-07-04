package org.example.projekt;

/** Klasa tworząca obiekt mechanicy*/
public class mechanicy {

/** Ustawia ID mechanika.
 * @param id ustawia id mechanika*/
    public void setId(int id) {
        this.id = id;
    }

/** Ustawia imie mechanika.
 * @param name ustawia imie mechanika*/
    public void setName(String name) {
        this.name = name;
    }

/** Ustawia nazwisko mechanika.
 * @param surname ustawia nazwisko mechanika*/
    public void setSurname(String surname) {
        this.surname = surname;
    }

/** Ustawia datę zatrudnienia mechanika.
 * @param employmentdate ustawia datę zatrudnienia mechanika*/
    public void setEmploymentdate(String employmentdate) {
        this.employmentdate = employmentdate;
    }

/** Ustawia specjalizację mechanika.
 * @param specialisation ustawia specjalizację mechanika*/
    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    private Integer id;
    private String name;
    private String surname;
    private String employmentdate;
    private String specialisation;

/** Konstruktor pobierający parametry id, mechanicyid, make, model, engine, manufacture, fault
 * @param id opisuje id mechanika
 * @param name opisuje imie mechanika
 * @param surname opisuje nazwisko mechanika
 * @param employmentdate opisuje datę zatrudnienia mechanika
 * @param specialisation opisuje specjalizację mechanika*/
    public mechanicy(int id, String name, String surname, String employmentdate, String specialisation) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.employmentdate = employmentdate;
        this.specialisation = specialisation;
    }

/** Zwraca ID mechanika
 * @return zwraca numer id mechanika*/
    public int getId() {
        return id;
    }
/** Zwraca imie mechanika.
 * @return zwraca imie mechanika*/
    public String getName() {
        return name;
    }
/** Zwraca nazwisko mechanika.
 * @return zwraca nazwisko mechanika*/
    public String getSurname() {
        return surname;
    }
/** Zwraca datę zatrudnienia mechanika.
 * @return zwraca datę zatrudnienia mechanika*/
    public String getEmploymentdate() {
        return employmentdate;
    }
/** Zwraca specjalizację mechanika.
 * @return zwraca specjalizację mechanika*/
    public String getSpecialisation() {
        return specialisation;
    }
}
