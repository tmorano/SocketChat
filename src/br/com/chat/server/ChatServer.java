package br.com.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import br.com.chat.definicoes.TipoLog;
import br.com.chat.suporte.Logger;

public class ChatServer {
	public static void main(String[] args) throws IOException {

		ServerSocket serverSocket = null;
		ClientesConectados cc = new ClientesConectados();
		int porta = 4447;

		Logger.write(TipoLog.SERVER, "Conectando...");
		try {
			serverSocket = new ServerSocket(porta);
		} catch (IOException e) {
			Logger.write(TipoLog.SERVER, "Nao foi possivel ouvir a porta: " + porta);
			System.exit(1);
		}

		while(true) {
			Socket clientSocket = null;
			try {
				clientSocket = serverSocket.accept();
				ClientInstance ci = new ClientInstance(serverSocket, clientSocket, cc);
				new Thread(ci).start();
			} catch (IOException e) {
				Logger.write(TipoLog.SERVER, "Falha ao aceitar o cliente");
				System.exit(1);
			}
		}
	}
}
