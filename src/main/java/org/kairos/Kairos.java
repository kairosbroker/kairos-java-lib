package org.kairos;

import org.kairos.protocol.MessageType;
import org.kairos.protocol.generic.Message;
import org.kairos.protocol.message.Consume;
import org.kairos.protocol.message.Control;
import org.kairos.protocol.message.JoinTopic;
import org.kairos.protocol.message.Publish;
import org.kairos.protocol.message.Ping;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.SocketException;

public class Kairos {
    static public Kairos connect(String ip, int port, boolean publisher, String topic) {
        Kairos handle;
        try {
            handle = new Kairos(ip, port, publisher);
        } catch (IOException e) {
            return null;
        }

        handle.reply(new Control(handle.isPublisher() ? MessageType.MSG_ID_PUBLISHER : MessageType.MSG_ID_CONSUMER));
        Message response = handle.waitForResponse();

        if (response.getType() != MessageType.MSG_OK) {
            System.err.println("Did not get confirmation message, something went wrong.");
            return null;
        }

        if (!handle.reply(new JoinTopic(topic))) {
            System.err.println("Unable to join topic.");
            handle.close();
            return null;
        }

        System.out.println("You have successfully connected as a " + (handle.isPublisher() ? "Publisher" : "Consumer"));

        return handle;
    }

    public boolean isPublisher() {
        return isPublisher;
    }

    public boolean publish(String message) {
        if (!isPublisher) {
            return false;
        }

        try {
            Message msg = new Publish(message);
            out.write(msg.serialize());
            out.flush();
            System.out.println("Replied message " + message + " to server (" + socket.getPort() + ")");
            return true;
        } catch (IOException e) {
            System.err.println("Unable to reply to the server - " + e);
            return false;
        }
    }

    public String consume() {
        if (isPublisher) {
            return "You are not a consumer";
        }

        Message newData;

        while (true) {
            newData = waitForResponse();

            if (newData.getType() == MessageType.MSG_PING) {
                continue;
            }

            if (newData.getType() == MessageType.MSG_CONSUME) {
                return ((Consume) newData).getMessage();
            }
        }
    }

    public void close() {
        if (isPublisher) {
            reply(new Control(MessageType.MSG_DISCONNECT));
        }

        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e + " - Something went wrong while closing the connection");
        }
    }


    /*
            Implementation class constructor,
             attributes and methods
     */

    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
    private final boolean isPublisher;

    private Kairos(String address, int port, boolean isPublisher) throws IOException {
        this.socket = new Socket(address, port);
        this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.isPublisher = isPublisher;
    }

    private boolean reply(Message message) {
        try {
            out.write(message.serialize());
            out.flush();
            System.out.println("Replied message " + message + " to server (" + socket.getPort() + ")");
            return true;
        } catch (IOException e) {
            System.err.println("Unable to reply to the server - " + e);
            return false;
        }
    }

    private Message waitForResponse() {
        try {
            return Message.deserialize(in.readUnsignedByte(), in);
        } catch (IOException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            if (e.getClass() == SocketException.class) {
                System.err.println("Connection was closed abruptly - " + e);
            }
            throw new RuntimeException(e);
        }
    }
}
