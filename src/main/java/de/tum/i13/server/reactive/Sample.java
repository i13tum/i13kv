package de.tum.i13.server.reactive;

import rx.Observable;
import rx.functions.Action1;

import java.io.IOException;

/**
 * Created by chris on 12.01.15.
 */
public class Sample {

    public static void main(String[] args) throws IOException {

        Observable.from(new ReadlineIterator()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("Hello " + s + "!");
            }
        });

    }
}
