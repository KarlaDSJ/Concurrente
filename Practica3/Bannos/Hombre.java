public class Hombre extends Participante{
    
    public Hombre(Banno banno){
        super(banno);
    }

    @Override
    public void entraBanno() throws InterruptedException {
        this.banno.entraHombre();
    }

    @Override
    public void salBanno() throws InterruptedException {
        this.banno.salHombre();
    }
}
