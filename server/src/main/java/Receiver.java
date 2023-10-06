import com.google.gson.Gson;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Receiver extends Thread {

    private final int port;
    private final int buffSize;
    private final boolean isRunning;
    private final BlockingQueue<Msg> in;


    public Receiver(int port) {
        this.port = port;
        this.buffSize = 1024;
        this.isRunning = true;
        this.in = new LinkedBlockingDeque<>();
    }

    @Override
    public void run() {
        listen(); // listening for incoming messages, and storing them in queue
    }

    private void listen() {
        Gson gson = new Gson();
        try {
            try (DatagramSocket socket = new DatagramSocket(port)) {
                System.out.println("Server is running on port: " + port);

                byte[] receiveData = new byte[buffSize];

                while (isRunning) {
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    socket.receive(receivePacket);

                    String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    Msg msg = gson.fromJson(message, Msg.class);
                    in.put(msg);
                }
            }
        } catch (Exception e) {
            System.out.println("Receiver cracked :'(");
        }
    }
}
