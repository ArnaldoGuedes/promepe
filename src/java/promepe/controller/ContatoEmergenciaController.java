package promepe.controller;

import java.io.IOException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import promepe.entidade.ContatoEmergencia;
import promepe.entidade.Usuario;
import promepe.negocio.ContatoEmergenciaBO;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
@ManagedBean
@SessionScoped
public class ContatoEmergenciaController {
    private static final long serialVersionUID = 1L;

    private FacesContext fc = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
    private ContatoEmergencia novoContatoEmergencia = new ContatoEmergencia();
    private ContatoEmergencia contatoEmergenciaSelecionado = new ContatoEmergencia();
    private ContatoEmergencia contatoEmergenciaSelecionadoEA = new ContatoEmergencia();
    private ContatoEmergenciaBO contatoEmergenciaBO = new ContatoEmergenciaBO();
    private List<ContatoEmergencia> contatoEmergencias;
    private Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    private ContatoEmergencia contatoEmergenciaAlterar = new ContatoEmergencia();
    
    public ContatoEmergenciaController() {
    }
    
    public void iniciarTela() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./contatoEmergencia.jsf");
        novoContatoEmergencia = new ContatoEmergencia();
        try {
            contatoEmergencias = contatoEmergenciaBO.obterTodasContatoEmergenciasUsuarioId(usuarioLogado.getId());
        } catch (Exception e) {
        }
    }

    public void atualizarTabela() {
        novoContatoEmergencia = new ContatoEmergencia();

        try {
            contatoEmergencias = contatoEmergenciaBO.obterTodasContatoEmergenciasUsuarioId(usuarioLogado.getId());
        } catch (Exception e) {}
    }

    public void adicionarContatoEmergencia() {
        try {
            novoContatoEmergencia.setUsuario(usuarioLogado);

            contatoEmergenciaBO.adicionarContatoEmergencia(novoContatoEmergencia);
            String nomeContatoEmergencia = novoContatoEmergencia.getNome();
            novoContatoEmergencia = new ContatoEmergencia();

            atualizarTabela();

            FacesMessage msgSucesso = new FacesMessage(FacesMessage.SEVERITY_INFO, "Contato '" + nomeContatoEmergencia + "' adicionada com sucesso!", "sucesso");
            FacesContext.getCurrentInstance().addMessage(null, msgSucesso);
        } catch (Exception e) {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }
    }

    public void excluirContatoEmergencia() {
        try {
            String nomeContatoEmergencia = contatoEmergenciaSelecionadoEA.getNome();

            contatoEmergenciaBO.excluirContatoEmergencia(contatoEmergenciaSelecionadoEA);

            atualizarTabela();

            FacesMessage msgSucesso = new FacesMessage("Contato '" + nomeContatoEmergencia + "' excluído com sucesso.");
            FacesContext.getCurrentInstance().addMessage(null, msgSucesso);
        } catch (Exception e) {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }
    }

    public void alterarContatoEmergencia() {
        try {
            contatoEmergenciaBO.alterarContatoEmergencia(contatoEmergenciaAlterar);
            
            atualizarTabela();
            
            FacesMessage msgSucesso = new FacesMessage(FacesMessage.SEVERITY_INFO, "Contato alterado com sucesso!", "Sucesso");
            FacesContext.getCurrentInstance().addMessage(null, msgSucesso);                                
        } catch (Exception e) {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }
    }

    //GETTERS
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public ContatoEmergencia getNovaContatoEmergencia() {
        return novoContatoEmergencia;
    }

    public List<ContatoEmergencia> getContatoEmergencias() {
        return contatoEmergencias;
    }

    public ContatoEmergencia getContatoEmergenciaSelecionado() {
        return contatoEmergenciaSelecionado;
    }

    public ContatoEmergencia getContatoEmergenciaSelecionadoEA() {
        return contatoEmergenciaSelecionadoEA;
    }

    public ContatoEmergencia getContatoEmergenciaAlterar() {
        return contatoEmergenciaAlterar;
    }

    
    //SETTER
    public void setNovaContatoEmergencia(ContatoEmergencia novoContatoEmergencia) {
        this.novoContatoEmergencia = novoContatoEmergencia;
    }

    public void setContatoEmergencias(List<ContatoEmergencia> contatoEmergencias) {
        this.contatoEmergencias = contatoEmergencias;
    }

    public void setContatoEmergenciaSelecionado(ContatoEmergencia contatoEmergenciaSelecionado) {
        this.contatoEmergenciaSelecionado = contatoEmergenciaSelecionado;
    }

    public void setContatoEmergenciaSelecionadoEA(ContatoEmergencia contatoEmergenciaSelecionadoEA) {
        this.contatoEmergenciaSelecionadoEA = contatoEmergenciaSelecionadoEA;
    }

    public void setContatoEmergenciaAlterar(ContatoEmergencia contatoEmergenciaAlterar) {
        this.contatoEmergenciaAlterar = contatoEmergenciaAlterar;
    }
    

}
