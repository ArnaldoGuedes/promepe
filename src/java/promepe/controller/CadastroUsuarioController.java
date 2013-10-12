package promepe.controller;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import promepe.entidade.Usuario;
import promepe.negocio.UsuarioBO;
import promepe.utilitarios.CryptographyTripleDES;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
@ManagedBean
@SessionScoped
public class CadastroUsuarioController implements Serializable{
    private static final long serialVersionUID = 1L;

    private Usuario usuario = new Usuario();
    private UsuarioBO usuarioBO = new UsuarioBO();
    private boolean termo = false;
    
    public CadastroUsuarioController() {
    }

    public void iniciarTela() throws IOException {
        this.usuario = new Usuario();
        FacesContext.getCurrentInstance().getExternalContext().redirect("cadastroUsuario.jsf");   
    }
    
    //--- VALIDAÇÃO PERSONALIZADA ---
    /**
     * Metodo Criado para evitar que o usuario preencha o campo somente com espaços em branco.
     * @param contexto
     * @param componente
     * @param valor 
     */
    public void validarEspacoBranco (FacesContext contexto, UIComponent componente, Object valor){
        String valorString = (String) valor;
        if (valorString.trim().equals("")){
            ((UIInput) componente).setValid(false);
            String mensagem = "O campo "+ componente.getAttributes().get("label")+ " não pode ser preenchido somente com espaços.";
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagem);
            contexto.addMessage(componente.getClientId(contexto),facesMessage);
        }
    }    
    
    
    // --- CADASTRAR USUARIO ---
    public void cadastrarUsuario (){
      FacesContext fc = FacesContext.getCurrentInstance();
      if(termo){
            try{
                //Aciona Classe de criptografia
                CryptographyTripleDES criptografia = CryptographyTripleDES.newInstance();
                
                String senha = usuario.getSenha(); //Guardar a senha original, so para logar.                
                
                this.usuario.setSenha(criptografia.encrypt(this.usuario.getSenha()));
                usuarioBO.cadastrarUsuario(this.usuario);

                InicioController inicio = new InicioController();
                inicio.setEmail(usuario.getEmail());
                inicio.setSenha(senha);
                //faz com que não haja redirecionamento automatico ao logar
                inicio.setRedirecionar(false);
                
                inicio.logar();
                //Redireciona para a pagina de alteração de dados, para o termino do cadastro dos dados
                AlterarInformacoesPessoaisController alterarInformacoes = new AlterarInformacoesPessoaisController();
                alterarInformacoes.iniciarTela();                

                
                this.usuario = new Usuario();
                termo = false;
            }catch(Exception e){
                //Exibe mensagem de erro, gerada pela exeção.
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
                fc.addMessage(null, facesMessage);
            }
            
        }else{
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Para cadastrar você deve ler e aceitar o termo de uso." , "erro");
                fc.addMessage(null, facesMessage);          
      }
    }
    
    //GETTER
    public Usuario getUsuario() {
        return usuario;
    }

    public boolean isTermo() {
        return termo;
    }
    
    
    //SETTER
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setTermo(boolean termo) {
        this.termo = termo;
    }
    
    
}
