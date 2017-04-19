import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ignacio on 19/04/2017.
 */
@Path("/json")
@Singleton
public class REST {
    protected EtakemonManager cache;
    public REST() {

    }

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> getList() {
        List<Usuario> a = new ArrayList<Usuario>();
        a=cache.getInstance().listar();
        return a;
    }
    @POST
    @Path("/setUser")
    @Produces(MediaType.APPLICATION_JSON)
    public void setUser(Usuario user){
        cache.getInstance().addUser(user);
    }
    @POST
    @Path("/setUser")
    @Produces(MediaType.APPLICATION_JSON)
    public void modificarUser(Usuario user){
        cache.getInstance().modificarUser(user.getName(),user);
    }
    @GET
    @Path("/getUser/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario getUser(@PathParam("name") String name){
        return cache.getInstance().getUser(name);
    }
    @GET
    @Path("/getEtackemons/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Etackemons> getEtackemons(@PathParam("name") String name){
        return cache.getInstance().getUser(name).getMios();
    }
    @POST
    @Path("/setEtackemon/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public void modificarUser(@PathParam("name") String name, Etackemons etackemon){
        cache.getInstance().addEtackemon(name,etackemon);
    }
}
