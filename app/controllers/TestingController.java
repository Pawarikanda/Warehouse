package controllers;


import akka.NotUsed;
import akka.actor.Status;
import akka.stream.OverflowStrategy;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import play.api.libs.Comet;
import play.mvc.Http;
import play.mvc.Result;

import java.util.Arrays;

import static play.mvc.Results.ok;

public class TestingController {

    public Result index() {
       /* // Prepare a chunked text stream
        Source<ByteString, ?> source = Source.<ByteString>actorRef(256, OverflowStrategy.dropNew())
                .mapMaterializedValue(sourceActor -> {
                    sourceActor.tell(ByteString.fromString("kiki"), null);
                    sourceActor.tell(ByteString.fromString("foo"), null);
                    sourceActor.tell(ByteString.fromString("bar"), null);
                    sourceActor.tell(new Status.Success(NotUsed.getInstance()), null);
                    return null;
                });
        // Serves this stream with 200 OK
        return ok().chunked(source);*/
       return ok();
    }
    public Result index1() {
        final Source source = Source.from(Arrays.asList("kiki", "foo", "bar"));
        return ok().chunked(source.via(Comet.string("parent.cometMessage"))).as(Http.MimeTypes.HTML);
    }
}
