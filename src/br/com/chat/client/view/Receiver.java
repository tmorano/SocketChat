package br.com.chat.client.view;

import javax.swing.JTextArea;

import br.com.chat.definicoes.Acoes;
import br.com.chat.suporte.Conexao;

public class Receiver implements Runnable{
	private final Conexao conexao;
	private final JTextArea txtMensagens;
	private final JTextArea txtListaUsuarios;


	public Receiver(Conexao conexao, JTextArea txpHistorico, JTextArea txpListaUsuarios) {
		this.conexao = conexao;
		this.txtMensagens = txpHistorico;
		this.txtListaUsuarios = txpListaUsuarios;
	}
	
	public Receiver(Conexao conexao) {
		this(conexao, null, null);
	}
	
	public void read(boolean sendToTxt){
		processInput(this.conexao.receive(), sendToTxt);
	}
	
	public void processInput(String mensagemRecebida, boolean sendToTxt){
		String[] partesDaMensagem = mensagemRecebida.split(":", 2);
		
		switch (Acoes.valueOf(partesDaMensagem[0])) {
			case ENVIA_MENSAGEM:
				if (sendToTxt) {
					StringBuffer mensagensAnteriores = new StringBuffer(txtMensagens.getText());
					mensagensAnteriores.append("\n");
					mensagensAnteriores.append(partesDaMensagem[1]);
					txtMensagens.setText(mensagensAnteriores.toString());
				}else{
					System.out.println(partesDaMensagem[1]);
				}
			break;
			case LISTA_USUARIO:
				if (sendToTxt) {
					StringBuffer usuarios = new StringBuffer();
					
					for (String nomeUsuario : partesDaMensagem[1].split(";")) {						
						usuarios.append(nomeUsuario);
						usuarios.append("\n");
						txtListaUsuarios.setText(usuarios.toString());
					}
				}else{
					for (String nomeUsuario : partesDaMensagem[1].split(";")) {						
						System.out.println(nomeUsuario);
					}
				}
			break;
			default:
			break;
		}
	}
	
	@Override
	public void run() {
		while(this.conexao.isRegistered())
			this.read(true);
		
	}

}
