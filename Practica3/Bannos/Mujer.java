public class Mujer extends Participante{
    
    public Mujer(Banno banno){
        super(banno);
    }

    @Override
    public void entraBanno() throws InterruptedException {
        this.banno.entraMujer();
        
    }

    @Override
    public void salBanno() throws InterruptedException {
        this.banno.salMujer();
    }
}
