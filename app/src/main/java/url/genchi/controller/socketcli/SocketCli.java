package url.genchi.controller.socketcli;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.google.common.base.Stopwatch;

@Component
public class SocketCli {

    @Async
    public Future<String> sendCmdAsync(String cmd) throws InterruptedException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Thread.sleep(500);
        stopwatch.elapsed(TimeUnit.MILLISECONDS);
        return new AsyncResult<String>(cmd);
    }

}