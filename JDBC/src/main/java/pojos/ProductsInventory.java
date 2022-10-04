package pojos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Builder
@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class ProductsInventory {

    @Column(name = "id")
    int id;
    @Column(name = "product_name")
    String product_name;
    @Column(name = "quantity")
    int quantity;
    @Column(name = "product_type")
    String product_type;
    @Column(name = "price_without_vat")
    int price_without_vat;
    @Column(name = "price_with_vat")
    int price_with_vat;
    @Column(name = "is_product_in_stock")
    boolean is_product_in_stock;
    @Column(name = "warehouse")
    String warehouse;
    @Column(name = "supplier_id")
    int supplier_id;
}
