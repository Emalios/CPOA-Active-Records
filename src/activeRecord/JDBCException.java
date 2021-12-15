package activeRecord;

/**
 * @author condenseau, bergerat
 */
public class JDBCException extends Exception {

    public JDBCException(Exception msg) {
        super(msg);
    }
}