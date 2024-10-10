package data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OrderLine")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idOrder;
    private Long idProduct;
    private String name;
    private Long quantity;
    private String price;
    private String note;
}
