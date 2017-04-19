import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ignacio on 19/04/2017.
 */
public class Usuario {
    private String name;
    private List<Etackemons> mios=new ArrayList<Etackemons>();

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Etackemons> getMios() {
        return mios;
    }
    public void setMios(List<Etackemons> mios) {
        this.mios = mios;
    }
    public Etackemons getEtackemon(String name){
    for(int i=0;i<mios.size();i++){
        if(mios.get(i).getName()==name)
            return mios.get(i);
        }return null;
}   public void addEtaackemon(Etackemons etackemon){
        mios.add(etackemon);}
}