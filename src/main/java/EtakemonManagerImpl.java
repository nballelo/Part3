import java.util.List;

/**
 * Created by Ignacio on 19/04/2017.
 */
public interface EtakemonManagerImpl<Usuario> {
    List<Usuario> listar();
    boolean addUser(Usuario obj);
    boolean modificarUser(String name, Usuario obj);
    Usuario getUser(String name);
    List<Etackemons> getEtackemons(String name);
    boolean addEtackemon(String name, Etackemons etackemon);

}
