package de.tum.i13.reactive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Iterable;
import java.util.Iterator;

/**
 * Created by chris on 12.01.15.
 */
public class ReadlineIterator implements Iterable<String> {
    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public String next() {
                System.out.println("Say hello to whom?");
                BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
                String line= null;
                try {
                    line = buffer.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return line;
            }
        };
    }
}
