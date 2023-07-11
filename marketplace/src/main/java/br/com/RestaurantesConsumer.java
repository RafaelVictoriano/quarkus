package br.com;

import br.com.model.Restaurante;
import br.com.repository.LocalizacaoRepository;
import br.com.repository.RestauranteRepository;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class RestaurantesConsumer {

    Logger log = LoggerFactory.getLogger(RestaurantesConsumer.class);

    @Inject
    RestauranteRepository repository;

    @Inject
    LocalizacaoRepository localizacaoRepository;

    @Incoming("restaurantes")
    public void consume(JsonObject jsonObject) {
        final var restaurante = jsonObject.mapTo(Restaurante.class);
        log.info("Receveid message, restaurante:{}", restaurante);

        localizacaoRepository.save(restaurante.getLocalizacao()).subscribe()
                .with(l -> log.info("Saved localizacao: {}", l));

        repository.save(restaurante).subscribe()
                .with(r -> log.info("Saved restaurante:{}", r));
    }
}
