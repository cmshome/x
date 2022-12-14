package com.lxk.jdk.socket.twoway;

import com.lxk.tool.util.ThreadUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * server
 * 先运行服务端，再运行客户端，就可以看到效果。
 *
 * @author LiXuekai on 2019/10/21
 */
public class TwoWaySocketServer {

    private static final int MAX_BUFFER_SIZE = 1024;

    @Getter
    @Setter
    private int port;


    private TwoWaySocketServer(int port) {
        this.port = port;
    }

    private void runServerSingle() throws IOException {
        ServerSocket server = new ServerSocket(this.port);

        System.out.println("base socket server started.");
        // the code will block here till the request come.
        Socket socket = server.accept();

        InputStream inputStream = socket.getInputStream();

        byte[] readBytes = new byte[MAX_BUFFER_SIZE];

        int msgLen;
        StringBuilder stringBuilder = new StringBuilder();

        while ((msgLen = inputStream.read(readBytes)) != -1) {
            stringBuilder.append(new String(readBytes, 0, msgLen, StandardCharsets.UTF_8));
        }

        System.out.println("get message from client:");
        System.out.println(stringBuilder);
        // 告诉客户端接收已经完毕，之后只能发送
        socket.shutdownInput();

        // write the receipt.
        OutputStream outputStream = socket.getOutputStream();
        ThreadUtils.sleep(59000);
        String receipt = "We received your message:[" + stringBuilder.toString() + "] and send it back.";
        outputStream.write(receipt.getBytes(StandardCharsets.UTF_8));

        inputStream.close();
        socket.close();
        server.close();
    }

    public static void main(String[] args) {
        TwoWaySocketServer bs = new TwoWaySocketServer(9799);
        try {
            bs.runServerSingle();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
