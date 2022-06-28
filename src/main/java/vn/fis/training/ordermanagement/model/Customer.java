package vn.fis.training.ordermanagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tbl_customer")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name", length = 100,nullable = false)
    private String name;

    @Column(name="mobile", length = 10,nullable = false)
    private String mobile;

    @Column(name="address", length = 100)
    private String address;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    @JsonBackReference
    private List<Order> orders;

}
