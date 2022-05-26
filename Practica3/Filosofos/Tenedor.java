package Filosofos;

/**
 * Interfaz que simula un tenedor
 */
public interface Tenedor {
    
    /**
     * Metodo que nos sirve para tomar un tenedor
     */
    public void tomar();

    /**
     * Metodo que nos sirve para soltar un tenedor
     */
    public void soltar();

    /**
     * Metodo que nos regresa el ID
     * @return El ID del tenedor
     */
    public int getId();

    /**
     * Metodo que nos regresa el numero de veces tomado
     * @return El numero de veces tomado
     */
    public int getVecesTomado();
}
