package promepe.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import promepe.entidade.MedicamentoControlado;
import promepe.entidade.Usuario;
import promepe.negocio.MedicamentoControladoBO;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
@ManagedBean
@SessionScoped
public class MedicamentoControladoController {
    private static final long serialVersionUID = 1L;

    private FacesContext fc = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
    private MedicamentoControlado novoMedicamentoControlado = new MedicamentoControlado();
    private MedicamentoControlado medicamentoControladoSelecionada = new MedicamentoControlado();
    private MedicamentoControlado medicamentoControladoSelecionadaEA = new MedicamentoControlado();
    private MedicamentoControladoBO medicamentoControladoBO = new MedicamentoControladoBO();
    private List<MedicamentoControlado> medicamentoControlados;
    private Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    private MedicamentoControlado medicamentoControladoAlterar = new MedicamentoControlado();

    public MedicamentoControladoController() {
        
    }

    public void iniciarTela() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./medicamentoControlado.jsf");
        novoMedicamentoControlado = new MedicamentoControlado();
        try {
            medicamentoControlados = medicamentoControladoBO.obterTodosMedicamentoControladosUsuarioId(usuarioLogado.getId());
        } catch (Exception e) {
        }
    }

    public void atualizarTabela() {
        novoMedicamentoControlado = new MedicamentoControlado();

        try {
            medicamentoControlados = medicamentoControladoBO.obterTodosMedicamentoControladosUsuarioId(usuarioLogado.getId());
        } catch (Exception e) {}
    }

    public void adicionarMedicamentoControlado() {
        try {
            novoMedicamentoControlado.setUsuario(usuarioLogado);
            novoMedicamentoControlado.setDataAdd(new Date());

            medicamentoControladoBO.adicionarMedicamentoControlado(novoMedicamentoControlado);
            String nomeMedicamentoControlado = novoMedicamentoControlado.getNome();
            novoMedicamentoControlado = new MedicamentoControlado();

            atualizarTabela();

            FacesMessage msgSucesso = new FacesMessage(FacesMessage.SEVERITY_INFO, "Medicamento controlado '" + nomeMedicamentoControlado + "' adicionado com sucesso!", "sucesso");
            FacesContext.getCurrentInstance().addMessage(null, msgSucesso);
        } catch (Exception e) {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }
    }

    public void excluirMedicamentoControlado() {
        try {
            String nomeMedicamentoControlado = medicamentoControladoSelecionadaEA.getNome();

            medicamentoControladoBO.excluirMedicamentoControlado(medicamentoControladoSelecionadaEA);

            atualizarTabela();

            FacesMessage msgSucesso = new FacesMessage("Medicamento controlado '" + nomeMedicamentoControlado + "' excluído com sucesso.");
            FacesContext.getCurrentInstance().addMessage(null, msgSucesso);
        } catch (Exception e) {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }
    }

    public void alterarMedicamentoControlado() {
        try {
            medicamentoControladoBO.alterarMedicamentoControlado(medicamentoControladoAlterar);
            
            atualizarTabela();
            
            FacesMessage msgSucesso = new FacesMessage(FacesMessage.SEVERITY_INFO, "Medicamento controlado alterado com sucesso!", "Sucesso");
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

    public MedicamentoControlado getNovoMedicamentoControlado() {
        return novoMedicamentoControlado;
    }

    public List<MedicamentoControlado> getMedicamentoControlados() {
        return medicamentoControlados;
    }

    public MedicamentoControlado getMedicamentoControladoSelecionada() {
        return medicamentoControladoSelecionada;
    }

    public MedicamentoControlado getMedicamentoControladoSelecionadaEA() {
        return medicamentoControladoSelecionadaEA;
    }

    public MedicamentoControlado getMedicamentoControladoAlterar() {
        return medicamentoControladoAlterar;
    }

    
    //SETTER
    public void setNovoMedicamentoControlado(MedicamentoControlado novoMedicamentoControlado) {
        this.novoMedicamentoControlado = novoMedicamentoControlado;
    }

    public void setMedicamentoControlados(List<MedicamentoControlado> medicamentoControlados) {
        this.medicamentoControlados = medicamentoControlados;
    }

    public void setMedicamentoControladoSelecionada(MedicamentoControlado medicamentoControladoSelecionada) {
        this.medicamentoControladoSelecionada = medicamentoControladoSelecionada;
    }

    public void setMedicamentoControladoSelecionadaEA(MedicamentoControlado medicamentoControladoSelecionadaEA) {
        this.medicamentoControladoSelecionadaEA = medicamentoControladoSelecionadaEA;
    }

    public void setMedicamentoControladoAlterar(MedicamentoControlado medicamentoControladoAlterar) {
        this.medicamentoControladoAlterar = medicamentoControladoAlterar;
    }

}
