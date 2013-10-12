package promepe.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import promepe.entidade.DoencaCronica;
import promepe.entidade.Usuario;
import promepe.negocio.DoencaCronicaBO;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
@ManagedBean
@SessionScoped
public class DoencaCronicaController {
    private static final long serialVersionUID = 1L;

    private FacesContext fc = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
    private Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    private DoencaCronica novaDC = new DoencaCronica();
    private DoencaCronica dcSelecionada = new DoencaCronica();
    private DoencaCronica dcSelecionadaEA = new DoencaCronica();
    private DoencaCronicaBO doencaCronicaBO = new DoencaCronicaBO();
    private List<DoencaCronica> doencaCronicas;
    private DoencaCronica dcAlterar = new DoencaCronica();
    
    public DoencaCronicaController() {
    }
    
    public void iniciarTela() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("./doencaCronica.jsf");
        usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        novaDC = new DoencaCronica();
        try{
            doencaCronicas = doencaCronicaBO.obterTodasDoencasCronicasUsuarioId(usuarioLogado.getId());
        }catch(Exception e){
        }
    }
    
    public void atualizarTabela(){
        try{
            doencaCronicas = doencaCronicaBO.obterTodasDoencasCronicasUsuarioId(usuarioLogado.getId());
            novaDC = new DoencaCronica();
        }catch(Exception e){}
    }
    
    public void adicionarDC(){
        try{
            novaDC.setUsuario(usuarioLogado);
            novaDC.setDataAdd(new Date());
            
            doencaCronicaBO.adicionarDoencaCronica(novaDC);
            String nomeDC = novaDC.getNome();
            novaDC = new DoencaCronica();
            
            atualizarTabela();
            
            FacesMessage msgSucesso = new FacesMessage(FacesMessage.SEVERITY_INFO, "Doença "+nomeDC+" adicionada com sucesso!", "sucesso");
            FacesContext.getCurrentInstance().addMessage(null, msgSucesso);
        }catch(Exception e){
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }
    }
    
    public void excluirDC(){
        try{
          String nomeDC = dcSelecionadaEA.getNome();
        
            doencaCronicaBO.excluirDoencaCronica(dcSelecionadaEA);
        
            atualizarTabela();
        
            FacesMessage msgSucesso = new FacesMessage("Doenca crônica '"+ nomeDC + "' excluída com sucesso.");
            FacesContext.getCurrentInstance().addMessage(null, msgSucesso);
        }catch(Exception e){
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),"erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);      
        }
    }
    
    public void alterarDC(){
        try {
            doencaCronicaBO.alterarDoencaCronica(dcAlterar);
            
            FacesMessage msgSucesso = new FacesMessage(FacesMessage.SEVERITY_INFO, "Doença Crônica alterada com sucesso!", "Sucesso");
            FacesContext.getCurrentInstance().addMessage(null, msgSucesso);                                
            
            atualizarTabela();
        } catch (Exception e) {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);            
        }
    }
    
    //GETTERS
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public DoencaCronica getNovaDC() {
        return novaDC;
    }

    public List<DoencaCronica> getDoencaCronicas() {
        return doencaCronicas;
    }

    public DoencaCronica getDcSelecionada() {
        return dcSelecionada;
    }

    public DoencaCronica getDcSelecionadaEA() {
        return dcSelecionadaEA;
    }    

    public DoencaCronica getDcAlterar() {
        return dcAlterar;
    }
    
    
    //SETTER
    public void setNovaDC(DoencaCronica novaDC) {
        this.novaDC = novaDC;
    }

    public void setDoencaCronicas(List<DoencaCronica> doencaCronicas) {
        this.doencaCronicas = doencaCronicas;
    }

    public void setDcSelecionada(DoencaCronica dcSelecionada) {
        this.dcSelecionada = dcSelecionada;
    }

    public void setDcSelecionadaEA(DoencaCronica dcSelecionadaEA) {
        this.dcSelecionadaEA = dcSelecionadaEA;
    }     

    public void setDcAlterar(DoencaCronica dcAlterar) {
        this.dcAlterar = dcAlterar;
    }
   
    
}
