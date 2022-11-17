package com.scaffold.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

@Slf4j
public class SocketClientRunner {
    public static void main(String[] args) throws IOException {
        Socket s = null;
        OutputStream os = null;
        try {
            s = new Socket("127.0.0.1", 9000);
            os = s.getOutputStream();
            log.info("befor write to server.");
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF("hello from client");
            dos.flush();
            log.info("begin to read from server");
            DataInputStream dis = new DataInputStream(s.getInputStream());
            final String response = dis.readUTF();
            log.info(response);
        } catch (Exception e) {
            log.error("socket client req error", e);
        } finally {
            if (os != null) {
                os.close();
            }
            if (s != null) {
                s.close();
            }
        }
    }
}
