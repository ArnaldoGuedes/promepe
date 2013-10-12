package promepe.controller;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import promepe.entidade.Usuario;
import promepe.negocio.UsuarioBO;
import promepe.utilitarios.CryptographyTripleDES;
import promepe.utilitarios.GeradorCodigoAlfaNum;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
@ManagedBean
@SessionScoped
public class InicioController implements Serializable {

    private static final long serialVersionUID = 1L;
    private String email = "";
    private String senha = "";
    private String emailRecuperar = "";
    private boolean redirecionar = true;
    private UsuarioBO usuarioBO = new UsuarioBO();

    public InicioController() {
    }

    public void iniciarTela() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./inicio.jsf");
        email = "";
        senha = "";

    }

    public void logar() {
        FacesContext fc = FacesContext.getCurrentInstance();

        if (email.isEmpty() || senha.isEmpty()) {
            FacesMessage mensagemErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-mail e senha devem ser preenchidos.", "falha");
            fc.addMessage(null, mensagemErro);
        } else {
            try {
                CryptographyTripleDES criptografia = CryptographyTripleDES.newInstance();

                //Criptografando a senha e preocurando o usuario no banco.
                senha = criptografia.encrypt(senha);
                Usuario usuario = usuarioBO.obterUsuario(email);
                //Autenticando o usuario.
                if ((usuario.getEmail().equals(email)) && (usuario.getSenha().equals(senha))) {
                    if (usuario.isContaAtiva()) {

                        //Recuperando Sessão
                        HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);

                        //Armazenar Usuario na Sessão
                        session.setAttribute("usuarioLogado", usuario);

                        if (redirecionar) {
                            //Apos logado fazer
                            homeController home = new homeController();
                            home.iniciarTela();
                        }
                    } else {
                        FacesMessage mensagemErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conta desativada!", "Error");
                        fc.addMessage(null, mensagemErro);
                    }
                } else {
                    FacesMessage mensagemErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, "usuario ou senha incorreto.", "falha");
                    fc.addMessage(null, mensagemErro);
                }
            } catch (Exception e) {
                FacesMessage mensagemErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Excpetion");
                fc.addMessage(null, mensagemErro);
            }
        }
    }

    public void recuperarSenha() {
        //Verifica se o usuario realmente existe.
        
        //------------------- CORRIGIR ENVIO DE E-MAIL ----------------------------
        Usuario usuarioObtido = usuarioBO.obterUsuario(emailRecuperar);

        if (usuarioObtido.getEmail().equals(emailRecuperar)) {
            try {
                Email emailEnviado = new SimpleEmail(); //Classe que envia o E-mail
                GeradorCodigoAlfaNum gerador = new GeradorCodigoAlfaNum(); //Classe que Gera nova senha
                CryptographyTripleDES criptografia = CryptographyTripleDES.newInstance(); //Classe que cryptografa a senha
                String novaSenha = gerador.gerarCodigoMaiusculasMinusculas(7); //Nova Senha
                
                emailEnviado.setHostName("smtp.gmail.com");
                emailEnviado.setSmtpPort(587);
                emailEnviado.setAuthentication("promepe@gmail.com", "Januaria");
                emailEnviado.setSSLOnConnect(true);
                emailEnviado.setFrom("promepe@gmail.com");
                emailEnviado.addTo(usuarioObtido.getEmail());
                emailEnviado.setSubject("PROMEPE: Recuperação de Senha");
                emailEnviado.setMsg("Ola "+usuarioObtido.getNome()+" sua nova senha e: "+novaSenha+".");
                emailEnviado.send();
                
                usuarioObtido.setSenha(criptografia.encrypt(novaSenha));
                usuarioBO.alterarInformacoesUsuario(usuarioObtido);
                
                FacesMessage mensagemErro = new FacesMessage("Nova senha enviada com sucesso para '"+usuarioObtido.getEmail()+"'.");
                FacesContext.getCurrentInstance().addMessage(null, mensagemErro);            
            } catch (Exception e) {
                FacesMessage mensagemErro = new FacesMessage(FacesMessage.SEVERITY_ERROR,"O servidor provavelmente está sem acesso a internet. "+ e.getMessage(), "Excpetion");
                FacesContext.getCurrentInstance().addMessage(null, mensagemErro);
            }
        } else {
            FacesMessage mensagemErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-mail não vinculado a nenhuma conta.", "falha");
            FacesContext.getCurrentInstance().addMessage(null, mensagemErro);

        }

    }

    //GETTER
    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public boolean isRedirecionar() {
        return redirecionar;
    }

    public String getEmailRecuperar() {
        return emailRecuperar;
    }

    //SETTER
    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setRedirecionar(boolean redirecionar) {
        this.redirecionar = redirecionar;
    }

    public void setEmailRecuperar(String emailRecuperar) {
        this.emailRecuperar = emailRecuperar;
    }
}
