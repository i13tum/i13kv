package de.tum.i13.server.nio;

import java.nio.channels.SelectionKey;

public class ChangeRequest {

    public SelectionKey selectionKey;
    public int ops;

    public ChangeRequest(SelectionKey selectionKey, int ops) {
        this.selectionKey = selectionKey;
        this.ops = ops;
    }
}