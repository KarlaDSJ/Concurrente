public class Mujer extends Participante{
    
     
    /**
     * Constructor, crea un baño para mujeres 
     * @param banno (objeto baño)
     */
    public Mujer(Banno banno){
        super(banno);
    }

    /**
     * Mujer entra al baño
     */
    @Override
    public void entraBanno() throws InterruptedException {
        this.banno.entraMujer();
    }

    /**
     * Mujer sale del baño
     */
    @Override
    public void salBanno() throws InterruptedException {
        this.banno.salMujer();
    }
}
