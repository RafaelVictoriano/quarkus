package br.com;

import br.com.model.Prato;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/pratos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PratoResource {

    @GET
    public Uni<List<Prato>> get() {
        return Prato.findAllPratos();
    }


    @POST
    public Uni<Response> post() {
        Prato.persist(() -> null);
        return null;
    }
}
