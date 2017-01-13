package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.*;

@Entity
public class Tag extends Model{
    @Id
    @Column(unique = true)
    public Long Tid;
    @Constraints.Required
    public String name;

    @ManyToMany(mappedBy = "tags")
    public List<Product> products;
    private static List<Tag> tags = new LinkedList<Tag>();

    public static Finder<Long, Tag> find (){
        return new Finder<>(Tag.class);
    }



    public Tag(){
// Left empty
    }
    public Tag(Long id, String name, Collection<Product> products) {
        this.Tid = id;
        this.name = name;
        this.products = new LinkedList<Product>(products);
        for (Product product : products) {
            product.tags.add(this);
        }
    }

    /*static {
        tags.add(new Tag(1L, "lightweight",
                Product.findByName("paperclips 1")));
        tags.add(new Tag(2L, "metal",
                Product.findByName("paperclips")));
        tags.add(new Tag(3L, "plastic",
                Product.findByName("paperclips")));
    }*/
    public static Tag findById(Long id) {
        return find().byId(id);


    }


    }
