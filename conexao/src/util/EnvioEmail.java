package util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class EnvioEmail {

	private SimpleEmail email = new SimpleEmail();

	static Logger logger = LogManager.getLogger(EnvioEmail.class.getName());

	public EnvioEmail() {

		try {
			email.setHostName("smtp.trt16.jus.br"); // SMTP para envio
			email.setSmtpPort(25);
			email.addTo("rpinto@trt16.jus.br", "Ra74185296"); // destinat�rio
			email.setFrom("spje@trt16.jus.br", "Setorpje3"); // remetente
			email.setAuthentication("spje@trt16.jus.br", "Setorpje3");
		} catch (EmailException e) {
			logger.error(e);
		}
	}

	public void setAssunto(String assunto) {
		this.email.setSubject(assunto);
	}

	public void setMensagem(String mensagem) {
		try {
			this.email.setMsg(mensagem); // conteudo do e-mail
		} catch (EmailException e) {
			logger.error(e);
		}
	}
	
	public void setAddTo(String destinatario) {
		try {
			this.email.addTo(destinatario); // destinatario do e-mail
		} catch (EmailException e) {
			logger.error(e);
		}
	}

	public void enviar() {
		try {
			this.email.send(); // envia o e-mail
		} catch (EmailException e) {
			logger.error(e);
		}
	}

}
