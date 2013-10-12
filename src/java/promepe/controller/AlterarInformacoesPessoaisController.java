package promepe.controller;

import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import promepe.entidade.Usuario;
import promepe.negocio.UsuarioBO;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
@ManagedBean
@ViewScoped
public class AlterarInformacoesPessoaisController {
    private static final long serialVersionUID = 1L;

    private FacesContext faces = FacesContext.getCurrentInstance();
    private HttpSession sessao = (HttpSession) faces.getExternalContext().getSession(false);
    private Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

    public AlterarInformacoesPessoaisController() {
    }

    public void iniciarTela() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./pessoal/alterarInformacoesPessoais.jsf");
        
    }

    public void alterarInformações() {
        FacesContext fc = FacesContext.getCurrentInstance();
        try{
            UsuarioBO usuarioBO = new UsuarioBO();
            usuarioBO.alterarInformacoesUsuario(usuarioLogado);
            
            sessao.setAttribute("usuarioLogado", usuarioLogado);
            
            fc.getExternalContext().redirect("./home.jsf");
        }catch(Exception e){
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "erro");
            fc.addMessage(null, msgErro);
        }
    }

    //GETTER
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    //SETTER
    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
}
