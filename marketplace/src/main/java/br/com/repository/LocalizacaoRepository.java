package br.com.repository;

import br.com.model.Localizacao;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSessionOnDemand;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@WithSessionOnDemand
@ApplicationScoped
public class LocalizacaoRepository implements PanacheRepository<Localizacao> {

    public Uni<Localizacao> save(Localizacao localizacao) {
        return Panache.withTransaction(() -> persist(localizacao));
    }
}
