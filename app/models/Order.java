package models;

import java.math.BigInteger;
import java.util.Date;
import java.util.Random;

/**
 * Created by exalt on 1/10/17.
 */
public class Order {
    public String toString(){
        return "Order" + nextId() + "date" + new Date();
    }

    private static String nextId(){
        Random random=new Random();
        return new BigInteger(30,random).toString(9);
    }

}
