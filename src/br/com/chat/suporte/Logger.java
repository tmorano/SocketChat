package br.com.chat.suporte;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import br.com.chat.definicoes.TipoLog;


public class Logger {

	/**
	 * TipoLog -> server ou client
	 * Mensagem -> string que ser� gravada mais data/hora
	 * 
	 * @param destino
	 * @param mensagem
	 */
	public static void write(TipoLog tipoLog, String mensagem) {

		try {
			FileWriter writer = new FileWriter(tipoLog.toString().toLowerCase() + ".log", true);			
			Date data = new Date();
			writer.write(data + ": " + mensagem + "\r\n");
			writer.close();			
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
