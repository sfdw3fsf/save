package vn.vnptit.sapi.oneapp.service

import org.springframework.stereotype.Service

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeoutException

@Service
class ConsentWaitService {

    private final ConcurrentHashMap<String, CompletableFuture<Map>> waitMap = new ConcurrentHashMap<>()

    CompletableFuture<Map> register(String requestId) {
        CompletableFuture<Map> f = new CompletableFuture<>()
        waitMap.put(requestId, f)
        return f
    }

    void complete(String requestId, Map data) {
        CompletableFuture<Map> future = waitMap.remove(requestId)
        if (future && !future.isDone()) {
            future.complete(data)
        }
    }

    void remove(String requestId) {
        waitMap.remove(requestId)
    }

    void timeout(String requestId) {
        CompletableFuture<Map> f = waitMap.remove(requestId)
        if (f) {
            f.completeExceptionally(new TimeoutException())
        }
    }
}