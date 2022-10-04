package pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Array;
import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Orders {

    @Column(name = "profile_name")
    String profile_name;
    @Column(name = "id")
    int id;
    @Column(name = "customer_id")
    int customer_id;
    @Column(name = "is_order_completed")
    boolean is_order_completed;
    @Column(name = "is_order_payed")
    boolean is_order_payed;
    @Column(name = "date_of_order")
    Timestamp date_of_order;
    @Column(name = "date_order_completed")
    Timestamp date_order_completed;
    @Column(name = "orderProductsQuantities")
    Array orderedProducts;
}
