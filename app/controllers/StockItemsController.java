package controllers;

import  play.mvc.*;
import models.StockItem;
import java.util.*;

public class StockItemsController extends Controller{
    public Result index() {
        List<StockItem> items = StockItem.find()
                .where()
                .ge("quantity", 300)
                .orderBy("quantity")
                .setMaxRows(10)
                .findList();

        return ok(items.toString());

    }

}
