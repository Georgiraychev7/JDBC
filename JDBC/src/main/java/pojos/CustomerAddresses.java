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
public class CustomerAddresses {

    @Column(name ="address_id")
    int address_id;
    @Column(name ="address")
    String address;
    @Column(name ="city")
    String city;
    @Column(name ="province")
    String province;
    @Column(name ="state")
    String state;
    @Column(name ="postal_code")
    int postal_code;
    @Column(name ="country")
    String country;
}
