package promepe.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import promepe.entidade.ConsultaCodEmergencia;
import promepe.entidade.Usuario;
import promepe.negocio.ConsultaCodEmergenciaBO;
import promepe.negocio.UsuarioBO;
import promepe.utilitarios.CryptographyTripleDES;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
@ManagedBean
@SessionScoped
public class homeController implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext fc = FacesContext.getCurrentInstance();
    private HttpSession sessao = (HttpSession) fc.getExternalContext().getSession(false);
    private Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
    private UsuarioBO usuarioBO = new UsuarioBO();
    private String novaSenha = "";
    private String senhaAntiga = "";
    private String novoEmail = "";
    
    private Date dataConsulta = verificarCodFoiConsultado();
    private boolean codConsultado = seCodConsultado();

    public homeController() {
    }

    public void iniciarTela() throws IOException {
        fc.getExternalContext().redirect("./pessoal/home.jsf");
    }

    private Date verificarCodFoiConsultado() {
        //Verifica se o código de emergencia foi consultado
        try {
            ConsultaCodEmergenciaBO conCodEmeBO = new ConsultaCodEmergenciaBO();
            ConsultaCodEmergencia conCodEme;

            conCodEme = conCodEmeBO.buscarConCodEme(usuarioLogado.getId(), usuarioLogado.getCodEmergencia());
            codConsultado = true;
            return conCodEme.getDataConsulta();
        } catch (Exception e) {
            codConsultado = false;
            return null;
        }
    }

    private boolean seCodConsultado() {
        if (dataConsulta == null) {
            return false;
        } else {
            return codConsultado = true;
        }

    }

    public void iniciarTelaInterno() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./home.jsf");
    }

    public void deslogar() throws IOException {
        sessao.invalidate();

        FacesContext.getCurrentInstance().getExternalContext().redirect("../inicio.jsf");
    }

    public void alterarSenha() {
        try {
            CryptographyTripleDES criptografia = CryptographyTripleDES.newInstance();

            String SenhaAntigaCrip = criptografia.encrypt(senhaAntiga);
            String novaSenhaCrip = criptografia.encrypt(novaSenha);

            usuarioBO.alterarSenha(SenhaAntigaCrip, novaSenhaCrip, usuarioLogado);

            usuarioLogado.setSenha(novaSenhaCrip);

            sessao.setAttribute("usuarioLogado", usuarioLogado);

            senhaAntiga = "";
            novaSenha = "";

            FacesMessage msgSucesso = new FacesMessage("Senha alterada com sucesso.");
            FacesContext.getCurrentInstance().addMessage(null, msgSucesso);
        } catch (Exception e) {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);

            senhaAntiga = "";
            novaSenha = "";
        }

    }

    public void alterarEmail() {
        try {
            usuarioBO.alterarEmail(novoEmail, usuarioLogado);

            usuarioLogado.setEmail(novoEmail);

            sessao.setAttribute("usuarioLogado", usuarioLogado);

            novoEmail = "";

            FacesMessage msgSucesso = new FacesMessage("E-Mail alterado com sucesso.");
            FacesContext.getCurrentInstance().addMessage(null, msgSucesso);
        } catch (Exception e) {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }

    }

    public void alterarCodigoEmergencia() {
        try {
            usuarioLogado.setCodEmergencia(usuarioBO.gerarCodigoEmergencia());
            usuarioBO.alterarInformacoesUsuario(usuarioLogado);

            sessao.setAttribute("usuarioLogado", usuarioLogado);

            codConsultado = false;

            iniciarTelaInterno();
        } catch (Exception e) {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }
    }

    public void desativarConta() {
        try {
            usuarioLogado.setContaAtiva(false);
            usuarioBO.alterarInformacoesUsuario(usuarioLogado);

            sessao.invalidate();

            FacesContext.getCurrentInstance().getExternalContext().redirect("../inicio.jsf");
        } catch (Exception e) {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }
    }

    public void gerarCartao() {
        try {
            //Servelt Contex para o caminho
            ServletContext sconContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

            //Arquivo Relatorio
            String relatorio = sconContext.getRealPath("/resources/relatorios/cartao.jasper");


            //Fonte de Dados
            ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
            usuarios.add(usuarioLogado);

            JRBeanCollectionDataSource fonteDados = new JRBeanCollectionDataSource(usuarios);
            HashMap map = new HashMap();
            map.put("usuarioNome", usuarioLogado);


            //Gerar Relatorio
            JasperPrint relatorioGerado =
                    JasperFillManager.fillReport(relatorio, map, fonteDados);


            byte[] b = JasperExportManager.exportReportToPdf(relatorioGerado);

            HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            res.setContentType("application/pdf");

            String nome = usuarioLogado.getNome().replace(" ", "_");

            //Código abaixo gerar o relatório e disponibiliza diretamente na página 
            res.setHeader("Content-disposition", "inline;filename=" + nome + "_Cartao_Emergencia.pdf");

            //Código abaixo gerar o relatório e disponibiliza para o cliente baixar ou salvar 
            res.getOutputStream().write(b);
            res.getCharacterEncoding();
            FacesContext.getCurrentInstance().responseComplete();

        } catch (Exception ex) {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao gerar cartão: " + ex.getMessage(), "erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }


    }
    //GETTER

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public String getSenhaAntiga() {
        return senhaAntiga;
    }

    public String getNovoEmail() {
        return novoEmail;
    }

    public boolean isCodConsultado() {
        return codConsultado;
    }

    public Date getDataConsulta() {
        return dataConsulta;
    }

    //SETTER
    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public void setSenhaAntiga(String senhaAntiga) {
        this.senhaAntiga = senhaAntiga;
    }

    public void setNovoEmail(String novoEmail) {
        this.novoEmail = novoEmail;
    }

    public void setCodConsultado(boolean codConsultado) {
        this.codConsultado = codConsultado;
    }
}
