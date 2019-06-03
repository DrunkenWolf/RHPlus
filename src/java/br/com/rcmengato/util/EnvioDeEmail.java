package br.com.rcmengato.util;

import java.util.Arrays;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;


/**
 *
 * @author Erick
 */
public class EnvioDeEmail {

    private static final String EMAIL = "recuperacao.secapee@gmail.com";
    private static final String EMAIL_SENHA = "recupera";
    private static final String EMAIL_NOME = "RH PLUS";
    private static final String HOST_NAME = "smtp.gmail.com";
    private static final int SMTP_PORT = 465;
    
    /**
     * Envia um email com apenas texto utilizando o jar commons-email da apache.
     * @param assunto Assunto do email
     * @param corpoEmail Mensagem que irá aparecer no corpo do email
     * @param emailDestino O email de quem irá receber a mensagem
     * @return Retorna 1 se o email foi enviado corretamente e 0 se houve algum problema
     */
    public static int enviarEmail(String assunto, String corpoEmail, String emailDestino) {

        try {
            Email email = new SimpleEmail();
            email.setHostName(HOST_NAME);
            email.setSmtpPort(SMTP_PORT);
            email.setSSLOnConnect(true);
            email.setAuthentication(EMAIL, EMAIL_SENHA);
            email.setFrom(EMAIL, EMAIL_NOME);
            email.setSubject(assunto);
            email.setMsg(corpoEmail);
            email.addTo(emailDestino);
            email.send();
            
            Thread.sleep(1000);
            
            return 1;
        } catch (EmailException | InterruptedException e) {
            System.out.println("Erro enviarEmail: " + Arrays.toString(e.getStackTrace()));
            
            return 0;
        }
    }
}
