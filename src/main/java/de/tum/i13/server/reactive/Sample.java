package de.tum.i13.server.reactive;

import io.reactivex.rxjava3.core.Observable;

import java.io.IOException;

/**
 * Created by chris on 12.01.15.
 */
public class Sample {

    public static void main(String[] args) throws IOException {

        Observable.fromIterable(new ReadlineIterator()).subscribe( (nextstring) -> {
            System.out.println("Hello " + nextstring + "!");
        });

    }
}
