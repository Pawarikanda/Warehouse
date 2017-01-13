package models;
import java.util.List;
import java.util.ArrayList;
import java.util.*;

import com.avaje.ebean.PagedList;
import play.data.validation.Constraints;
import play.mvc.PathBindable;
import play.mvc.QueryStringBindable;
import javax.persistence.*;
import com.avaje.ebean.Model;


@Entity
public class Product extends Model implements PathBindable<Product>,QueryStringBindable<Product>{

    @Id
    @Column(unique = true)
    public Long id;
    @Constraints.Required public String ean;
    @Constraints.Required public String name;
    public String description;

    @ManyToMany
    public List<Tag> tags = new LinkedList<>();

    @OneToMany(mappedBy = "product")
    public List<StockItem> stockItems;

    public static Finder<Long, Product> find = new Finder<>(Product.class);


    @Override
    public Product bind (String key, String value) {
        return findByEan(value);
    }
    @Override
    public String unbind(String key) {
        return this.ean;
    }
    @Override
    public String javascriptUnbind() {
        return this.ean;
    }

 public  Optional<Product> bind(String key, Map<String,String[]>data){
        return Optional.of(findByEan(data.get("ean")[0]));
 }



    public Product(){}
    public Product(String ean, String name, String description){
        this.description=description;
        this.ean=ean;
        this.name=name;
    }

    @Override
    public String toString(){
        return String.format("%s-%s",ean,name);
    }


    private static List<Product> products;
    /*static {
        products = new ArrayList<>();
        products.add(new Product("1111111111111",
                "Paperclips 1",
                "Paperclips description 1"));
        products.add(new Product("2222222222222",
                "Paperclips 2",
                "Paperclips description "));
        products.add(new Product("3333333333333",
                "Paperclips 3",
                "Paperclips description 3"));
        products.add(new Product("4444444444444",
                "Paperclips 4",
                "Paperclips description 4"));
        products.add(new Product("5555555555555",
                "Paperclips 5",
                "Paperclips description 5"));
    }*/
    public static List<Product> findAll() {
        return new ArrayList<>(products);
    }

    public static Product findByEan(String ean) {
        /*for (Product candidate : products) {
            if (candidate.ean.equals(ean)) {
                return candidate;
            }
        }
        return null;*/
        return find.where().eq("ean", ean).findUnique();

    }

    public static List<Product> findByName(String term) {
        final List<Product> results = new ArrayList<>();
        for (Product candidate : products) {
            if (candidate.name.toLowerCase().contains(term.toLowerCase())) {
                results.add(candidate);
            }
        }
        return results;
    }

    public static PagedList<Product> find(int page,int size){
        return find.where().orderBy("id asc").findPagedList(page,size);
    }
    /*
    public static boolean remove(Product product)
     {
        return products.remove(product);
    }*/

    /*public void save() {
        products.remove(findByEan(this.ean));
        products.add(this);
    }*/

}

