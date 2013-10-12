package promepe.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import promepe.entidade.Cirurgia;
import promepe.entidade.Usuario;
import promepe.negocio.CirurgiaBO;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
@ManagedBean
@SessionScoped
public class CirurgiaController {
    private static final long serialVersionUID = 1L;

    private FacesContext fc = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
    private Cirurgia novaCirurgia = new Cirurgia();
    private Cirurgia cirurgiaSelecionada = new Cirurgia();
    private Cirurgia cirurgiaSelecionadaEA = new Cirurgia();
    private CirurgiaBO cirurgiaBO = new CirurgiaBO();
    private List<Cirurgia> cirurgias;
    private Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    private Cirurgia cirurgiaAlterar = new Cirurgia();

    public CirurgiaController() {
    }

    public void iniciarTela() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./cirurgia.jsf");
        novaCirurgia = new Cirurgia();
        try {
            cirurgias = cirurgiaBO.obterTodasCirurgiasUsuarioId(usuarioLogado.getId());
        } catch (Exception e) {
        }
    }

    public void atualizarTabela() {
        novaCirurgia = new Cirurgia();

        try {
            cirurgias = cirurgiaBO.obterTodasCirurgiasUsuarioId(usuarioLogado.getId());
        } catch (Exception e) {}
    }

    public void adicionarCirurgia() {
        try {
            novaCirurgia.setUsuario(usuarioLogado);
            novaCirurgia.setDataAdd(new Date());

            cirurgiaBO.adicionarCirurgia(novaCirurgia);
            String nomeCirurgia = novaCirurgia.getLocalOperado();
            novaCirurgia = new Cirurgia();

            atualizarTabela();

            FacesMessage msgSucesso = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cirurgia em '" + nomeCirurgia + "' adicionada com sucesso!", "sucesso");
            FacesContext.getCurrentInstance().addMessage(null, msgSucesso);
        } catch (Exception e) {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }
    }

    public void excluirCirurgia() {
        try {
            String nomeCirurgia = cirurgiaSelecionadaEA.getLocalOperado();

            cirurgiaBO.excluirCirurgia(cirurgiaSelecionadaEA);

            atualizarTabela();

            FacesMessage msgSucesso = new FacesMessage("Cirurgia em'" + nomeCirurgia + "' excluída com sucesso.");
            FacesContext.getCurrentInstance().addMessage(null, msgSucesso);
        } catch (Exception e) {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }
    }

    public void alterarCirurgia() {
        try {
            cirurgiaBO.alterarCirurgia(cirurgiaAlterar);
            
            atualizarTabela();
            
            FacesMessage msgSucesso = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cirurgia alterada com sucesso!", "Sucesso");
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

    public Cirurgia getNovaCirurgia() {
        return novaCirurgia;
    }

    public List<Cirurgia> getCirurgias() {
        return cirurgias;
    }

    public Cirurgia getCirurgiaSelecionada() {
        return cirurgiaSelecionada;
    }

    public Cirurgia getCirurgiaSelecionadaEA() {
        return cirurgiaSelecionadaEA;
    }

    public Cirurgia getCirurgiaAlterar() {
        return cirurgiaAlterar;
    }

    
    //SETTER
    public void setNovaCirurgia(Cirurgia novaCirurgia) {
        this.novaCirurgia = novaCirurgia;
    }

    public void setCirurgias(List<Cirurgia> cirurgias) {
        this.cirurgias = cirurgias;
    }

    public void setCirurgiaSelecionada(Cirurgia cirurgiaSelecionada) {
        this.cirurgiaSelecionada = cirurgiaSelecionada;
    }

    public void setCirurgiaSelecionadaEA(Cirurgia cirurgiaSelecionadaEA) {
        this.cirurgiaSelecionadaEA = cirurgiaSelecionadaEA;
    }

    public void setCirurgiaAlterar(Cirurgia cirurgiaAlterar) {
        this.cirurgiaAlterar = cirurgiaAlterar;
    }
    
    
}
