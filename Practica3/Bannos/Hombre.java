/**
 * Clase que simula un hombre
 */
public class Hombre extends Participante{
    
    /**
     * Constructor 
     * @param banno al que quiere entrar
     */
    public Hombre(Banno banno){
        super(banno);
    }

    /**
     * El hombre entra al baño
     */
    @Override
    public void entraBanno() throws InterruptedException {
        this.banno.entraHombre();
    }

    /**
     * El hombre sale del baño
     */
    @Override
    public void salBanno() throws InterruptedException {
        this.banno.salHombre();
    }
}
