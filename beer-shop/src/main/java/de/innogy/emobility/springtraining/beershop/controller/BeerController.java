package de.innogy.emobility.springtraining.beershop.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.innogy.emobility.springtraining.beershop.exception.OutOfBeerException;
import de.innogy.emobility.springtraining.beershop.model.BeerItem;
import de.innogy.emobility.springtraining.beershop.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class BeerController {

    private SupplyService supplyService;

    private RestTemplate restTemplate;

    @Autowired
    public BeerController(SupplyService supplyService, RestTemplate restTemplate) {
        this.supplyService = supplyService;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/order")
    public DeliveryDTO orderBeer(@Valid @RequestBody OrderDTO orderDTO) throws OutOfBeerException {
        return supplyService.orderBeer(orderDTO);
    }

    @GetMapping("/menu")
    public List<BeerItem> getMenu() {
        return supplyService.provideMenu();
    }

    // Lesson 4.3
    @HystrixCommand(fallbackMethod = "provideFallbackList")
    @GetMapping("/supplier/beer")
    public List<BeerItem> getSupplierBeer() {
        BeerItem[] beerArray = restTemplate.getForObject("//beersupplier/supply/all", BeerItem[].class);
        if (beerArray != null && beerArray.length > 0) {
            return Arrays.asList(beerArray);
        }
        return null;
    }

    private List<BeerItem> provideFallbackList() {
        List<BeerItem> beerItems = new ArrayList<>();
        beerItems.add(BeerItem.builder().alcoholVol(0.5).name("Kölsch").build());
        beerItems.add(BeerItem.builder().alcoholVol(4.8).name("Oettinger").build());
        return beerItems;
    }
}
