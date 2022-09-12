package controller;

import java.io.IOException;
// import java.io.InputStream;
// import java.io.ObjectInputStream;
// import java.io.ObjectOutputStream;
// import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
// import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import tasks.ClientTask;

public class ServerAppl {

	public static void main(String[] args) throws InterruptedException {
		int port = 9000;
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		try (ServerSocket serverSocket = new ServerSocket(port);) {
			while (true) {
				System.out.println("Server waiting...");
				Socket socket = serverSocket.accept();
				System.out.println("Connection established");
				System.out.println("Client address: " + socket.getInetAddress() + ":" + socket.getPort());
				ClientTask task = new ClientTask(socket);
				executorService.execute(task);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			executorService.shutdown();
			executorService.awaitTermination(1, TimeUnit.MINUTES);
		}

	}

}
