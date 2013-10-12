package promepe.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import promepe.entidade.Alergia;
import promepe.entidade.Usuario;
import promepe.negocio.AlergiaBO;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
@ManagedBean
@SessionScoped
public class AlergiaController {
    private static final long serialVersionUID = 1L;

    private FacesContext fc = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
    private Alergia novaAlergia = new Alergia();
    private Alergia alergiaSelecionada = new Alergia();
    private Alergia alergiaSelecionadaEA = new Alergia();
    private AlergiaBO alergiaBO = new AlergiaBO();
    private List<Alergia> alergias;
    private Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    private Alergia alergiaAlterar = new Alergia();

    public AlergiaController() {
    }

    public void iniciarTela() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./alergia.jsf");
        novaAlergia = new Alergia();
        try {
            alergias = alergiaBO.obterTodasAlergiasUsuarioId(usuarioLogado.getId());
        } catch (Exception e) {
        }
    }

    public void atualizarTabela() {
        novaAlergia = new Alergia();

        try {
            alergias = alergiaBO.obterTodasAlergiasUsuarioId(usuarioLogado.getId());
        } catch (Exception e) {}
    }

    public void adicionarAlergia() {
        try {
            novaAlergia.setUsuario(usuarioLogado);
            novaAlergia.setDataAdd(new Date());

            alergiaBO.adicionarAlergia(novaAlergia);
            String nomeAlergia = novaAlergia.getNome();
            novaAlergia = new Alergia();

            atualizarTabela();

            FacesMessage msgSucesso = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alergia '" + nomeAlergia + "' adicionada com sucesso!", "sucesso");
            FacesContext.getCurrentInstance().addMessage(null, msgSucesso);
        } catch (Exception e) {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }
    }

    public void excluirAlergia() {
        try {
            String nomeAlergia = alergiaSelecionadaEA.getNome();

            alergiaBO.excluirAlergia(alergiaSelecionadaEA);

            atualizarTabela();

            FacesMessage msgSucesso = new FacesMessage("Alergia '" + nomeAlergia + "' excluída com sucesso.");
            FacesContext.getCurrentInstance().addMessage(null, msgSucesso);
        } catch (Exception e) {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }
    }

    public void alterarAlergia() {
        try {
            alergiaBO.alterarAlergia(alergiaAlterar);
            
            atualizarTabela();
            
            FacesMessage msgSucesso = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alergia alterada com sucesso!", "Sucesso");
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

    public Alergia getNovaAlergia() {
        return novaAlergia;
    }

    public List<Alergia> getAlergias() {
        return alergias;
    }

    public Alergia getAlergiaSelecionada() {
        return alergiaSelecionada;
    }

    public Alergia getAlergiaSelecionadaEA() {
        return alergiaSelecionadaEA;
    }

    public Alergia getAlergiaAlterar() {
        return alergiaAlterar;
    }

    
    //SETTER
    public void setNovaAlergia(Alergia novaAlergia) {
        this.novaAlergia = novaAlergia;
    }

    public void setAlergias(List<Alergia> alergias) {
        this.alergias = alergias;
    }

    public void setAlergiaSelecionada(Alergia alergiaSelecionada) {
        this.alergiaSelecionada = alergiaSelecionada;
    }

    public void setAlergiaSelecionadaEA(Alergia alergiaSelecionadaEA) {
        this.alergiaSelecionadaEA = alergiaSelecionadaEA;
    }

    public void setAlergiaAlterar(Alergia alergiaAlterar) {
        this.alergiaAlterar = alergiaAlterar;
    }
    
}
