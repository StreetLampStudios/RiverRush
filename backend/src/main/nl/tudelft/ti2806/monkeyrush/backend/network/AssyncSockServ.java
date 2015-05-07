package nl.tudelft.ti2806.monkeyrush.backend.network;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;

/**
 * Handles incomming connection requests over TCP
 * in an asynchronous way.
 */
public class AssyncSockServ {
    private final int port;
    private final AsynchronousServerSocketChannel serverChannel;

    public AssyncSockServ(int port) {
        this.port = port;

        final SocketAddress localAddress = new InetSocketAddress(port)
        this.serverChannel = AsynchronousServerSocketChannel.open().bind()
    }
}
