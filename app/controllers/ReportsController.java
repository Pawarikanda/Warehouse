package controllers;
import models.Report;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import play.libs.concurrent.Futures;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import javax.inject.Inject;

public class ReportsController extends Controller{
    @Inject HttpExecutionContext ec;
    public CompletionStage<Result> index() {
        CompletionStage<Report> promiseOfKPIReport = CompletableFuture.supplyAsync(this::intensiveKPIReport);

        CompletionStage<Report> promiseOfETAReport = CompletableFuture.supplyAsync(this::intensiveETAReport);

        CompletionStage<List<Report>> promises = Futures.sequence(promiseOfKPIReport,promiseOfETAReport);


        return promises.thenApplyAsync(reports->ok(report.render(reports)), ec.current());

    }
        public Report intensiveKPIReport() {
            Report r = new Report("KPI report");
            r.execute();
            return r;
        }

        public Report intensiveETAReport() {
        Report r = new Report("ETA report");
        r.execute();
        return r;
    }


}
