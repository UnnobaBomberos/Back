package ancap.demo.Exception;

public class SolicitudException extends RuntimeException {
    
    private final int status;
    
    public SolicitudException(String message, int status) {
        super(message);
        this.status = status;
    }
    
    public int getStatus() {
        return status;
    }
}


