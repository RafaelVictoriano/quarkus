package com.br;

import com.br.dto.*;
import com.br.model.Prato;
import com.br.model.Restaurante;
import com.br.repository.PratoRepository;
import com.br.repository.RestauranteRepository;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.quarkus.panache.common.Page;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.util.List;
import java.util.stream.Collectors;


@Path("/restaurantes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("proprietario")
public class RestauranteResource {

    @Inject
    RestauranteRepository repository;

    @Inject
    PratoRepository pratoRepository;

    @Inject
    RestauranteMapper restauranteMapper;


    @Inject
    @Channel("restaurantes")
    Emitter<Restaurante> restauranteEmitter;

    @Inject
    PratoMapper pratoMapper;

    @Counted(value = "Restaurante  Numero de vezes que o endpoint foi chamado")
    @Timed(value = "Tempo completo de busca")
    @GET
    public List<Restaurante> get(@QueryParam("page") int page, @QueryParam("page_size") @DefaultValue("10") Integer size) {
        final var restaurantes = repository.findAll();
        return restaurantes.page(Page.of(page, size)).list();
    }


    @POST
    @Transactional
    public void post(@Valid AdicionarRestauranteDTO dto) {
        final var restaurante = restauranteMapper.toRestaurante(dto);
        repository.persist(restaurante);
        restauranteEmitter.send(restaurante);
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void put(@PathParam("id") Long id, AtualizarRestauranteDTO dto) {
        final Restaurante restaurante = repository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Not found"));
        restaurante.nome = dto.nomeFantasia;
        repository.persist(restaurante);
    }


    @DELETE
    @Path("{idRestaurante}")
    @Transactional
    public void deleteRestaurante(@PathParam("idRestaurante") Long idRestaurante) {
        repository.findByIdOptional(idRestaurante)
                .ifPresentOrElse(repository::delete, () -> {
                    throw new NotFoundException();
                });
    }

    @GET
    @Path("{idRestaurante}/pratos")
    @Tag(name = "prato")
    public List<PratoDTO> buscarPratos(@PathParam("idRestaurante") Long idRestaurante) {
        final Restaurante restaurante = repository.findByIdOptional(idRestaurante)
                .orElseThrow(() -> new NotFoundException("Not found"));
        var pratos = pratoRepository.stream("restaurante", restaurante);
        return pratos.map(p -> pratoMapper.toDTO(p)).collect(Collectors.toList());
    }

    @POST
    @Path("/{idRestaurante}/pratos")
    @Transactional
    @Tag(name = "prato")
    public Response adicionarPrato(@PathParam("idRestaurante") Long idRestaurante, AdicionarPratoDTO dto) {
        final var restaurante = repository.findByIdOptional(idRestaurante)
                .orElseThrow(() -> new NotFoundException("Not found"));
        Prato prato = pratoMapper.toPrato(dto);
        prato.restaurante = restaurante;
        pratoRepository.persist(prato);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{idRestaurante}/pratos/{id}")
    @Transactional
    @Tag(name = "prato")
    public void atualizar(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id, AtualizarPratoDTO dto) {
        repository.findByIdOptional(idRestaurante)
                .orElseThrow(() -> new NotFoundException("Not found"));

        final var prato = pratoRepository.findByIdOptional(id).orElseThrow(() -> new NotFoundException("Not found"));
        pratoMapper.toPrato(dto, prato);
        pratoRepository.persist(prato);
    }

    @DELETE
    @Path("{idRestaurante}/pratos/{id}")
    @Transactional
    @Tag(name = "prato")
    public void delete(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id) {
        repository.findByIdOptional(idRestaurante)
                .orElseThrow(() -> new NotFoundException("Not found"));

        pratoRepository.findByIdOptional(id).ifPresentOrElse(pratoRepository::delete,
                () -> {
                    throw new NotFoundException();
                });
    }
}
