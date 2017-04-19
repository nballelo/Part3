import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ignacio on 19/04/2017.
 */
public class TestUser {
    EtakemonManager cache;
    List<Usuario> lista=new ArrayList<Usuario>();
    HashMap<String, Usuario>gurra=new HashMap<String, Usuario>();
    Usuario user=new Usuario();
    Usuario user1=new Usuario();
    List<Etackemons>mios=new ArrayList<Etackemons>();
    @Before
    public void setUP(){
        user.setName("Masmi");
        Etackemons etackemon=new Etackemons("Lobo",2);
        mios.add(etackemon);
        user.setMios(mios);
        lista.add(user);
        cache.getInstance().addUser(user);
        user1.setName("Masmi1");
        etackemon=new Etackemons("Lobo1",2);
        mios.add(etackemon);
        user1.setMios(mios);
        lista.add(user1);
        Assert.assertEquals(true,cache.getInstance().addUser(user1));

    }
    @Test
    public void listar(){
        List<Usuario>list=new ArrayList<Usuario>();
        list=cache.getInstance().listar();
    }
    @Test
    public void tearDown(){
        Etackemons e=new Etackemons("MIOS",3);
        mios=new ArrayList<Etackemons>();
        mios.add(e);
        cache.getInstance().addEtackemon(user.getName(),e);
        Assert.assertEquals(true,cache.getInstance().modificarUser(user.getName(),user));
        user1.setName("nombremalo");
        Assert.assertEquals(false,cache.getInstance().modificarUser(user1.getName(),user1));
    }
    @Test
    public void get(){
        Assert.assertEquals(user,cache.getInstance().getUser(user.getName()));
        Assert.assertEquals(user.getEtackemon("Lobo"),cache.getInstance().getUser(user.getName()).getEtackemon("Lobo"));
    }
}
