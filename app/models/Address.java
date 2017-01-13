package models;


import com.avaje.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Address extends Model{
    @Id
    @Column(unique = true)
    public Long id;

    @OneToOne
    public Warehouse warehouse;
    public String street;
    public String number;
    public String postalCode;
    public String city;
    public String country;

}
