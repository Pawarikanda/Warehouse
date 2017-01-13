package models;
import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Warehouse extends Model{
    @Id
    @Column(unique = true)
    public Long id;

    @OneToOne
    public Address address;

    public String name;

    @OneToMany(mappedBy = "warehouse")
    public List<StockItem> stock = new ArrayList<>();

    public String toString() {
        return name;
    }

}
