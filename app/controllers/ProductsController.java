package controllers;

import be.objectify.deadbolt.java.actions.Dynamic;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.With;
import views.html.*;
import models.Product;
import models.StockItem;
import models.Tag;
import java.util.*;


import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@With(CatchAction.class)
@Security.Authenticated(SecuredController.class)
public class ProductsController extends Controller{

/*
* Play supports generating two types of routers,
* one is a dependency injected router, the other is a static router.
* The default is the dependency injected router, and we recommend you use dependency-injected controllers
 * If you need to use static controllers you can switch to the static routes generator by adding the
 *configuration to your build.sbt and declaring each of your action methods as static.*/


    public static Form<Product> productForm;


@Inject
    public ProductsController(FormFactory formFactory){
        this.productForm=formFactory.form(Product.class);
    }

    public  Result index() {
        return redirect(routes.ProductsController.list(0));
    }

    public Result list(Integer page){
    com.avaje.ebean.PagedList<Product> products = Product.find(page,5);
    return ok(views.html.catalog.render(products));
}

public Result newProduct(){
    return ok(details.render(productForm));
}

public  Result details(Product product){
    //final Product product = Product.findByEan(ean);
    //if (product == null) {
   //     return notFound(String.format("Product %s does not exist.", ean));
    //}
    Form<Product> filledForm = productForm.fill(product);
    return ok(details.render(filledForm));
}

    @Dynamic(value = "foo")
    public CompletionStage<Result> save(){
    Form<Product> boundForm = productForm.bindFromRequest();
    if(boundForm.hasErrors()){
        flash("error","Please correct the form below.");
        return CompletableFuture.completedFuture(ok(details.render(boundForm)));
    }
    Product product = boundForm.get();
    List<Tag> tags = new ArrayList<Tag>();
    for (Tag tag : product.tags) {
        if (tag.Tid!= null) {
            tags.add(Tag.findById(tag.Tid));
        }
    }
    product.tags = tags;

    if (product.id == null) {
             product.save();
    } else {
        product.update();
    }


    StockItem item = new StockItem();
    item.quantity = 0L;
    item.product = product;


    product.save();
    item.save();
    flash("success",String.format("Successfully added product %s",product));
        return CompletableFuture.completedFuture(redirect(routes.ProductsController.list(1)));

}
    public Result delete(String ean) {
        final Product product = Product.findByEan(ean);
        final StockItem stockItem=StockItem.findByProductId(product.id.toString());
        if (stockItem !=null)stockItem.delete();
        if(product == null) {
            return notFound(String.format("Product %s does not exists.", ean));
        }
        product.delete();
        return redirect(routes.ProductsController.list(1));
    }


}
