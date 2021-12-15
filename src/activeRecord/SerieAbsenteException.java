package activeRecord;

public class SerieAbsenteException extends Exception {

    public SerieAbsenteException() {
        super("Serie n'existe pas.");
    }

}
