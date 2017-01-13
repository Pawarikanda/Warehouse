package models;

import com.avaje.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class StockItem extends Model{
    @Id
    @Column(unique = true)
    public Long id;

    @ManyToOne
    public Warehouse warehouse;

    @ManyToOne
    public Product product;
    public Long quantity;

    public static Finder<Long, StockItem> find (){
        return new Finder<>(StockItem.class);
    }

    public static StockItem findByProductId(String pid){

        return find().where().eq("product_id",pid).findUnique();
    }

    public String toString() {
        return String.format("StockItem %d - %dx product %s",
                id, quantity, product == null ? null : product.id);
    }


}
