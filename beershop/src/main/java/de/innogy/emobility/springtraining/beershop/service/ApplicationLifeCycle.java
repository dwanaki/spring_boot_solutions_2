package de.innogy.emobility.springtraining.beershop.service;

import de.innogy.emobility.springtraining.beershop.model.BeerItem;
import de.innogy.emobility.springtraining.beershop.repository.BeerItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;

@Slf4j
@Component
public class ApplicationLifeCycle implements SmartLifecycle {

    private RestTemplate restTemplate;

    private BeerItemRepository beerItemRepository;

    @Autowired
    public ApplicationLifeCycle(RestTemplate restTemplate, BeerItemRepository beerItemRepository) {
        this.restTemplate = restTemplate;
        this.beerItemRepository = beerItemRepository;
    }

    @Override
    public boolean isAutoStartup() {
        return false;
    }

    @Override
    public void stop(Runnable callback) {
        log.info("Shutdown...");
        //Continue shutdown.
        callback.run();
    }

    @Override
    public void start() {
        log.info("Application started, getting beer from provider.");
        BeerItem[] beerItems = restTemplate
                .getForObject(UriComponentsBuilder.fromUriString("//beersupplier/supply/all").build().toUri(),
                              BeerItem[].class);
        for (BeerItem beerItem : beerItems) {
            beerItem.setStock(100);
        }
        beerItemRepository.saveAll(Arrays.asList(beerItems));
    }

    @Override
    public void stop() {
        log.info("Application stopped");
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public int getPhase() {
        return Integer.MAX_VALUE;
    }
}

