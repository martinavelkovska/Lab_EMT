package mk.ukim.finki.emt.productcatalog.services.form;

import lombok.Data;
import mk.ukim.finki.emt.productcatalog.domain.models.ProductType;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;

@Data
public class ProductForm {

    private String productName;
    private String productOpis;
    private ProductType productType;
    private Money price;

    private int sales;
}
