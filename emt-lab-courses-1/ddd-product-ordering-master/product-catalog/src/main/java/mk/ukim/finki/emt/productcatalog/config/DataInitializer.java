package mk.ukim.finki.emt.productcatalog.config;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.productcatalog.domain.models.Product;
import mk.ukim.finki.emt.productcatalog.domain.models.ProductType;
import mk.ukim.finki.emt.productcatalog.domain.repository.ProductRepository;
import mk.ukim.finki.emt.productcatalog.xport.rest.ProductResource;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class DataInitializer {

    private final ProductRepository productRepository;

    @PostConstruct
    public void initData() {
        Product p1 = Product.build("GIVENCHY Gentleman","parfimirana voda",ProductType.maski, Money.valueOf(Currency.MKD,4290), 10);
        Product p2 = Product.build("DIOR Mis Dior", "toaletna voda", ProductType.zenski,Money.valueOf(Currency.MKD,5360), 5);
        Product p3 = Product.build("Ariana Grande Cloud", "gift set", ProductType.zenski,Money.valueOf(Currency.MKD,100), 5);
        if (productRepository.findAll().isEmpty()) {
            productRepository.saveAll(Arrays.asList(p1,p2,p3));
        }
    }
}
