package com.joeynebula.socketecho;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class SocketService implements ISocketService {
    private OkHttpClient client;
    private WebSocketListener listener;
    private Request request;
    private WebSocket socket;
    private final int  NORMAL_CLOSURE_STATUS = 1000;

    @Override
    public void start(WebSocketListener listener) {
        if(client == null){
            client = new OkHttpClient();
        }

        //check it the socket is already init, if it is use the return value from send to see if it is closed.
        if(socket != null && socket.send("Socket is not closed")){
            return;
        }

        this.listener = listener;

        request = new Request.Builder().url("ws://demos.kaazing.com/echo").build();

        socket = client.newWebSocket(request, listener);
    }

    @Override
    public boolean send(String message) {
        return socket.send(message);
    }

    @Override
    public void close() {
        socket.close(NORMAL_CLOSURE_STATUS, "Close button pressed");
        //client.dispatcher().executorService().shutdownNow();
    }
}
