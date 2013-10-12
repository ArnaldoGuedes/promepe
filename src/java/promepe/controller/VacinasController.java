package promepe.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import promepe.entidade.Usuario;
import promepe.entidade.Vacina;
import promepe.negocio.VacinaBO;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
@ManagedBean
@SessionScoped
public class VacinasController {
    private static final long serialVersionUID = 1L;

    private FacesContext fc = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
    private Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    private Vacina novaVacina = new Vacina();
    private Vacina vacinaSelecionada = new Vacina();
    private Vacina vacinaSelecionadaEA = new Vacina(); //Vacina Seleciona para Exclusão/Alteração
    private VacinaBO vacinaBO = new VacinaBO();
    private List<Vacina> vacinas;
    private Vacina vacinaAlteVacina = new Vacina();
    
    public VacinasController() {
        try{
            vacinas = vacinaBO.obterTodasVacinasUsuarioId(usuarioLogado.getId());
        }catch(Exception e){
        }
    }
        
    public void iniciarTela() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("./vacinas.jsf");    
        novaVacina = new Vacina();
        try{
        vacinas = vacinaBO.obterTodasVacinasUsuarioId(usuarioLogado.getId());
        }catch(Exception e){}
    }
    
    public void adicionarVacina(){
        try{
            novaVacina.setDataAdd(new Date());
            novaVacina.setUsuario(usuarioLogado);
            
            vacinaBO.adicionarVacina(novaVacina);
            String nomeVacina = novaVacina.getNome();
            novaVacina = new Vacina();
            
            atualizarTabela();
            
            FacesMessage msgSucesso = new FacesMessage(FacesMessage.SEVERITY_INFO, "Vacina "+nomeVacina+" registrada com sucesso!", "Sucesso");
            FacesContext.getCurrentInstance().addMessage(null, msgSucesso);
                
        }catch(Exception e){
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(),"erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }
        
    }    
    
    public void alterarVacina(){
        try{            
            vacinaBO.alterarVacina(vacinaAlteVacina);
            
            atualizarTabela();

            FacesMessage msgSucesso = new FacesMessage(FacesMessage.SEVERITY_INFO, "Vacina alterada com sucesso!", "Sucesso");
            FacesContext.getCurrentInstance().addMessage(null, msgSucesso);                    
            
        }catch(Exception e){            
            FacesMessage msgSucesso = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Erro");
            FacesContext.getCurrentInstance().addMessage(null, msgSucesso);
            atualizarTabela();
        }
    }
    
    public void excluirVacina(){
        try{
            String nomeVacina = vacinaSelecionadaEA.getNome();
            
            vacinaBO.excluirVacina(vacinaSelecionadaEA);
            
            atualizarTabela();
            
            FacesMessage msgSucesso = new FacesMessage(FacesMessage.SEVERITY_INFO, "Vacina "+nomeVacina+" excluída.", "Sucesso");
            FacesContext.getCurrentInstance().addMessage(null, msgSucesso);                    
                        
        }catch(Exception e){
            FacesMessage msgSucesso = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Erro");
            FacesContext.getCurrentInstance().addMessage(null, msgSucesso);        
        }
        
    }
    
    public void atualizarTabela(){
        novaVacina = new Vacina();
        try{
        vacinas = vacinaBO.obterTodasVacinasUsuarioId(usuarioLogado.getId());
        }catch(Exception e){}
    }
    
    //GETTER
    public Vacina getNovaVacina() {
        return novaVacina;
    }

    public List<Vacina> getVacinas() {
        return vacinas;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public Vacina getVacinaSelecionada() {
        return vacinaSelecionada;
    }

    public Vacina getVacinaSelecionadaEA() {
        return vacinaSelecionadaEA;
    }

    public Vacina getVacinaAlteVacina() {
        return vacinaAlteVacina;
    }

    
    
    
    //SETTER
    public void setNovaVacina(Vacina vacina) {
        this.novaVacina = vacina;
    }

    public void setVacinas(List<Vacina> vacinas) {
        this.vacinas = vacinas;
    }

    public void setVacinaSelecionada(Vacina vacinaSelecionada) {
        this.vacinaSelecionada = vacinaSelecionada;
    }

    public void setVacinaSelecionadaEA(Vacina vacinaSelecionadaEA) {
        this.vacinaSelecionadaEA = vacinaSelecionadaEA;
    }

    public void setVacinaAlteVacina(Vacina vacinaAlteVacina) {
        this.vacinaAlteVacina = vacinaAlteVacina;
    }
    
}
