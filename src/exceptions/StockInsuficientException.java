package exceptions;

/**@author Sergi Valenzuela García
 * M03-UF4 
 * 10 mar 2023
 */
public class StockInsuficientException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String message;
    
    public StockInsuficientException() {
        this("No hay suficiente Stock.");
    }
    
    public StockInsuficientException(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return this.message;
    }
}
