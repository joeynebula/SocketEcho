package com.joeynebula.socketecho;

import okhttp3.WebSocketListener;

public interface ISocketService {
    void start(WebSocketListener listener);
    boolean send(String message);
    void close();
}
