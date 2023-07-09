package br.com.repository;

import br.com.model.Restaurante;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSessionOnDemand;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@WithSessionOnDemand
@ApplicationScoped
public class RestauranteRepository implements PanacheRepository<Restaurante> {

    @Transactional
    public Uni<Restaurante> save(Restaurante restaurante) {
        return Panache.withTransaction(() -> persist(restaurante));
    }
}
