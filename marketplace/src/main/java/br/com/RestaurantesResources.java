package br.com;

import br.com.model.Prato;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantesResources {

    @GET()
    @Path("/{id_restaurante}/pratos")
    public Uni<List<Prato>> getPratos(@PathParam("id_restaurante") final Long idRestaurante) {
        return Prato.finPratosByIdRestaurante(idRestaurante);
    }
}
