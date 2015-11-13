package de.tum.i13.server.kv;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

/**
 * Created by chris on 13.11.15.
 */
public class BackingMap {
    AbstractMap<String, String> store;
    Lock lock = new ReentrantLock();

    public BackingMap() {
        this.store = new HashMap<String, String>();
    }

    public String atomic(Function<AbstractMap<String,String>, String> mut) {
        lock.lock();
        String res = mut.apply(store);
        lock.unlock();
        return res;
    }
}

@FunctionalInterface
interface Mutate<T> {
    T mutate(AbstractMap<String, String> kv);
}


