package org.example.projekt;

import javafx.scene.text.Text;

/** Klasa tworząca obiekt auta*/
public class auta {
    private int id;
    private int mechanicyid;
    private String make;
    private String model;
    private String engine;
    private String manufacture;
    private String fault;

/** Konstruktor pobierający parametry id, mechanicyid, make, model, engine, manufacture, fault
 * @param id opisuje id auta
 * @param mechanicyid opisuje id mechanika, który zajmuje się naprawą auta
 * @param make opisuje markę auta
 * @param model opisuje model auta
 * @param engine opisuje typ silnika
 * @param manufacture opisuje datę produkcji auta
 * @param fault opisuje usterki w aucie*/
    public auta(int id, int mechanicyid, String make, String model, String engine, String manufacture, String fault) {
        this.id = id;
        this.mechanicyid = mechanicyid;
        this.make = make;
        this.model = model;
        this.engine = engine;
        this.manufacture = manufacture;
        this.fault = fault;
    }

/** Zwraca ID auta
 * @return zwraca numer id auta*/
    public int getId() {
        return id;
    }

/** Zwraca ID mechanika, który naprawia auto
 * @return zwraca numer id mechanika naprawiającego auto*/
    public int getMechanicyid() {
        return mechanicyid;
    }

/** Zwraca markę auta
 * @return zwraca markę auta*/
    public String getMake() {
        return make;
    }

/** Zwraca model auta
 * @return zwraca model auta*/
    public String getModel() {
        return model;
    }

/** Zwraca typ silnika auta
 * @return zwraca typ silnika auta*/
    public String getEngine() {
        return engine;
    }

/** Zwraca rok produkcji auta
 * @return zwraca rok produkcji auta*/
    public String getManufacture() {
        return manufacture;
    }

/** Zwraca opis usterki w aucie
 * @return zwraca usterki w aucie*/
    public String getFault() {
        return fault;
    }

/** Ustawia ID auta.
 * @param id ustawia numer id auta*/
    public void setId(int id) {
        this.id = id;
    }

/** /** Ustawia IDmechanika, który naprawia dane auto.
 * @param mechanicyid ustawia numer id mechanika naprawiającego auto*/
    public void setMechanicyid(int mechanicyid) {
        this.mechanicyid = mechanicyid;
    }

/** Ustawia markę auta.
 * @param make ustawia markę auta*/
    public void setMake(String make) {
        this.make = make;
    }

/** Ustawia model auta.
 * @param model ustawia model auta*/
    public void setModel(String model) {
        this.model = model;
    }

/** Ustawia typ silnika auta.
 * @param engine ustawia typ silnika auta*/
    public void setEngine(String engine) {
        this.engine = engine;
    }

/** Ustawia rok produkcji auta.
 * @param manufacture ustawia rok produkcji auta*/
    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

/** Ustawia opis usterki w aucie.
 * @param fault ustawia opis usterki w aucie*/
    public void setFault(String fault) {
        this.fault = fault;
    }
}
