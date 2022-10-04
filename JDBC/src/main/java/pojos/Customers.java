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
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Customers {
    @Column(name ="Id")
    int id;
    @Column(name ="Profile_name")
    String profile_name;
    @Column(name = "Email")
    String email;
    @Column(name="Phone")
    String phone;
    @Column(name="Age")
    int age;
    @Column(name="Gdpr_consent")
    boolean Gdpr_consent;
    @Column(name="Is_customer_profile_active")
    boolean Is_customer_profile_active;
    @Column(name="Profile_created_at")
    Timestamp Profile_created_at;
    @Column(name="Profile_deactivated")
    Timestamp Profile_deactivated;
    @Column(name="reason_for_deactivation")
    String reason_for_deactivation;
    @Column(name="Notes")
    String notes;
    @Column(name ="Address_id")
    int address_id;
    @Column(name = "customerAddress")
    Array customerAddress;
    @Column(name = "customerOrders")
    Array customerOrders;
}
