package tasks;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalTime;

public class ClientTask implements Runnable {
	Socket socket;

	public ClientTask(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try (Socket socket = this.socket) {
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			ObjectInputStream ois = new ObjectInputStream(inputStream);
			while (true) {
				String message = ois.readObject().toString();
				System.out.println(message);
				String response = message + " " + LocalTime.now();
				oos.writeObject(response);
			}
		} catch (Exception e) {
			System.out.println("Client: " + socket.getInetAddress() + ":" + socket.getPort() + " connection closed");
		}

	}

}