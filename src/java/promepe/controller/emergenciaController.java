package promepe.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import promepe.entidade.Alergia;
import promepe.entidade.Cirurgia;
import promepe.entidade.ConsultaCodEmergencia;
import promepe.entidade.ContatoEmergencia;
import promepe.entidade.DoencaCronica;
import promepe.entidade.MedicamentoControlado;
import promepe.entidade.Usuario;
import promepe.entidade.Vacina;
import promepe.negocio.AlergiaBO;
import promepe.negocio.CirurgiaBO;
import promepe.negocio.ConsultaCodEmergenciaBO;
import promepe.negocio.ContatoEmergenciaBO;
import promepe.negocio.DoencaCronicaBO;
import promepe.negocio.MedicamentoControladoBO;
import promepe.negocio.UsuarioBO;
import promepe.negocio.VacinaBO;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
@ManagedBean
@SessionScoped
public class emergenciaController {

    private static final long serialVersionUID = 1L;
    private String codEmergencia = "";
    private Usuario usuario = new Usuario();
    private UsuarioBO usuarioBO = new UsuarioBO();
    
    private AlergiaBO alergiaBO = new AlergiaBO();
    private Alergia alergiaSeleciona = new Alergia();
    private List<Alergia> alergias;
    
    private DoencaCronicaBO DcBO = new DoencaCronicaBO();
    private DoencaCronica Dc = new DoencaCronica();
    private List<DoencaCronica> doencaCronicas;
    
    private ContatoEmergenciaBO contatoEmergenciaBO = new ContatoEmergenciaBO();
    private ContatoEmergencia contatoEmergencia = new ContatoEmergencia();
    private List<ContatoEmergencia> contatoEmergencias;
    
    private MedicamentoControladoBO medicamentoControladoBO = new MedicamentoControladoBO();
    private MedicamentoControlado medicamentoControlado = new MedicamentoControlado();
    private List<MedicamentoControlado> medicamentoControlados;

    private VacinaBO vacinaBO = new VacinaBO();
    private Vacina vacina = new Vacina();
    private List<Vacina> vacinas;

    private CirurgiaBO cirurgiaBO = new CirurgiaBO();
    private Cirurgia cirurgia = new Cirurgia();
    private List<Cirurgia> cirurgias;
    /**
     * Creates a new instance of emergenciaController
     */
    public emergenciaController() {
    }

    public void iniciarTela() throws IOException {
        atualizarTabelas();
        FacesContext.getCurrentInstance().getExternalContext().redirect("./emergencia.jsf");
    }

    public void atualizarTabelas() {
        try {
            alergias = alergiaBO.obterTodasAlergiasUsuarioId(usuario.getId());
            doencaCronicas = DcBO.obterTodasDoencasCronicasUsuarioId(usuario.getId());
            contatoEmergencias = contatoEmergenciaBO.obterTodasContatoEmergenciasUsuarioId(usuario.getId());
            medicamentoControlados = medicamentoControladoBO.obterTodosMedicamentoControladosUsuarioId(usuario.getId());
            vacinas = vacinaBO.obterTodasVacinasUsuarioId(usuario.getId());
            cirurgias = cirurgiaBO.obterTodasCirurgiasUsuarioId(usuario.getId());
        } catch (Exception e) {
            //Não Fazer nada.
        }
    }

    public void consultarEmergencia() {
        try {
            ConsultaCodEmergencia consultaCodEme = new ConsultaCodEmergencia();
            ConsultaCodEmergenciaBO codEmergenciaBO = new ConsultaCodEmergenciaBO();
            //Verifica se o codigo existe, e se o usuario esta ativo.
            usuario = usuarioBO.obterUsuarioCodEmergencia(codEmergencia);
            
            //Adiciona uma consulta para que o usuario saiba que houve consulta ao seu codigo.
            consultaCodEme.setCodEmergencia(codEmergencia);
            consultaCodEme.setCodUsuario(usuario.getId());
            consultaCodEme.setDataConsulta(new Date());
            codEmergenciaBO.adicionarConsultaCodEmergencia(consultaCodEme);
            
            //O que fazer se tudo estiver correto.            
            iniciarTela();
            codEmergencia = "";
        } catch (Exception e) {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }
    }

    //RELATORIO PDF ALERGIA
    public void relatorioAlergia() {
        if (!alergias.isEmpty()) {
            try {
                //Servelt Contex para o caminho
                ServletContext sconContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

                //Arquivo Relatorio
                String relatorio = sconContext.getRealPath("/resources/relatorios/emergenciaAlergia.jasper");

                //Fonte de Dados
                JRBeanCollectionDataSource fonteDados = new JRBeanCollectionDataSource(alergias);
                HashMap map = new HashMap();
                map.put("usuarioNome", usuario.getNome());

                //Gerar Relatorio
                JasperPrint relatorioGerado =
                        JasperFillManager.fillReport(relatorio, map, fonteDados);


                byte[] b = JasperExportManager.exportReportToPdf(relatorioGerado);

                HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                res.setContentType("application/pdf");

                String nome = usuario.getNome().replace(" ", "_");

                //Código abaixo gerar o relatório e disponibiliza diretamente na página 
                res.setHeader("Content-disposition", "inline;filename=" + nome + "_Alergias.pdf");

                //Código abaixo gerar o relatório e disponibiliza para o cliente baixar ou salvar 
                res.getOutputStream().write(b);
                res.getCharacterEncoding();
                FacesContext.getCurrentInstance().responseComplete();

            } catch (Exception ex) {
                FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Gerar relatorio: " + ex.getMessage(), "erro");
                FacesContext.getCurrentInstance().addMessage(null, msgErro);
            }
        } else {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não há alergias a serem salvas ou impressas.", "erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }

    }

    //RELATORIO PDF CONTATO EMERGENCIA
    public void relatorioContatoEmergencia() {
        if (!contatoEmergencias.isEmpty()) {
            try {
                //Servelt Contex para o caminho
                ServletContext sconContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

                //Arquivo Relatorio
                String relatorio = sconContext.getRealPath("/resources/relatorios/emergenciaContato.jasper");


                //Fonte de Dados
                JRBeanCollectionDataSource fonteDados = new JRBeanCollectionDataSource(contatoEmergencias);
                HashMap map = new HashMap();
                map.put("usuarioNome", usuario.getNome());


                //Gerar Relatorio
                JasperPrint relatorioGerado =
                        JasperFillManager.fillReport(relatorio, map, fonteDados);


                byte[] b = JasperExportManager.exportReportToPdf(relatorioGerado);

                HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                res.setContentType("application/pdf");

                String nome = usuario.getNome().replace(" ", "_");

                //Código abaixo gerar o relatório e disponibiliza diretamente na página 
                res.setHeader("Content-disposition", "inline;filename=" + nome + "_Contatos_Emergenciais.pdf");

                //Código abaixo gerar o relatório e disponibiliza para o cliente baixar ou salvar 
                res.getOutputStream().write(b);
                res.getCharacterEncoding();
                FacesContext.getCurrentInstance().responseComplete();

            } catch (Exception ex) {
                FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Gerar relatorio: " + ex.getMessage(), "erro");
                FacesContext.getCurrentInstance().addMessage(null, msgErro);
            }
        } else {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não há contatos a serem salvos ou impressos.", "erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }

    }

    //RELATORIO PDF DOENCAS CRONICAS
    public void relatorioDoencasCronicas() {
        if (!doencaCronicas.isEmpty()) {
            try {
                //Servelt Contex para o caminho
                ServletContext sconContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

                //Arquivo Relatorio
                String relatorio = sconContext.getRealPath("/resources/relatorios/emergenciaDoencasCronicas.jasper");


                //Fonte de Dados
                JRBeanCollectionDataSource fonteDados = new JRBeanCollectionDataSource(doencaCronicas);
                HashMap map = new HashMap();
                map.put("usuarioNome", usuario.getNome());

                //Gerar Relatorio
                JasperPrint relatorioGerado =
                        JasperFillManager.fillReport(relatorio, map, fonteDados);


                byte[] b = JasperExportManager.exportReportToPdf(relatorioGerado);

                HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                res.setContentType("application/pdf");

                String nome = usuario.getNome().replace(" ", "_");

                //Código abaixo gerar o relatório e disponibiliza diretamente na página 
                res.setHeader("Content-disposition", "inline;filename=" + nome + "_Doencas_Cronicas.pdf");

                //Código abaixo gerar o relatório e disponibiliza para o cliente baixar ou salvar 
                res.getOutputStream().write(b);
                res.getCharacterEncoding();
                FacesContext.getCurrentInstance().responseComplete();

            } catch (Exception ex) {
                FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Gerar relatorio: " + ex.getMessage(), "erro");
                FacesContext.getCurrentInstance().addMessage(null, msgErro);
            }
        } else {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não há doenças crônicas a serem salvos ou impressos.", "erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }

    }

    //RELATORIO PDF VACINAS
    public void relatorioVacinas() {
        if (!vacinas.isEmpty()) {
            try {
                //Servelt Contex para o caminho
                ServletContext sconContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

                //Arquivo Relatorio
                String relatorio = sconContext.getRealPath("/resources/relatorios/emergenciaVacinas.jasper");

                //Fonte de Dados
                JRBeanCollectionDataSource fonteDados = new JRBeanCollectionDataSource(vacinas);
                HashMap map = new HashMap();
                map.put("usuarioNome", usuario.getNome());

                //Gerar Relatorio
                JasperPrint relatorioGerado =
                        JasperFillManager.fillReport(relatorio, map, fonteDados);


                byte[] b = JasperExportManager.exportReportToPdf(relatorioGerado);

                HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                res.setContentType("application/pdf");

                String nome = usuario.getNome().replace(" ", "_");

                //Código abaixo gerar o relatório e disponibiliza diretamente na página 
                res.setHeader("Content-disposition", "inline;filename=" + nome + "_Vacinas.pdf");

                //Código abaixo gerar o relatório e disponibiliza para o cliente baixar ou salvar 
                res.getOutputStream().write(b);
                res.getCharacterEncoding();
                FacesContext.getCurrentInstance().responseComplete();

            } catch (Exception ex) {
                FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Gerar relatorio: " + ex.getMessage(), "erro");
                FacesContext.getCurrentInstance().addMessage(null, msgErro);
            }
        } else {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não há vacinas a serem salvos ou impressos.", "erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }

    }    
    
    //RELATORIO PDF MEDICAMENTOS CONTROLADOS
    public void relatorioMedicamentoControlado() {
        if (!medicamentoControlados.isEmpty()) {
            try {
                //Servelt Contex para o caminho
                ServletContext sconContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

                //Arquivo Relatorio
                String relatorio = sconContext.getRealPath("/resources/relatorios/emergenciaMedicamentos.jasper");

                //Fonte de Dados
                JRBeanCollectionDataSource fonteDados = new JRBeanCollectionDataSource(medicamentoControlados);
                HashMap map = new HashMap();
                map.put("usuarioNome", usuario.getNome());

                //Gerar Relatorio
                JasperPrint relatorioGerado =
                        JasperFillManager.fillReport(relatorio, map, fonteDados);


                byte[] b = JasperExportManager.exportReportToPdf(relatorioGerado);

                HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                res.setContentType("application/pdf");

                String nome = usuario.getNome().replace(" ", "_");

                //Código abaixo gerar o relatório e disponibiliza diretamente na página 
                res.setHeader("Content-disposition", "inline;filename=" + nome + "_Medicamentos_Controlados.pdf");

                //Código abaixo gerar o relatório e disponibiliza para o cliente baixar ou salvar 
                res.getOutputStream().write(b);
                res.getCharacterEncoding();
                FacesContext.getCurrentInstance().responseComplete();

            } catch (Exception ex) {
                FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Gerar relatorio: " + ex.getMessage(), "erro");
                FacesContext.getCurrentInstance().addMessage(null, msgErro);
            }
        } else {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não há medicamentos controlados a serem salvos ou impressos.", "erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }

    }

    //RELATORIO CIRURGIAS
    public void relatorioCirurgias() {
        if (!cirurgias.isEmpty()) {
            try {
                //Servelt Contex para o caminho
                ServletContext sconContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

                //Arquivo Relatorio
                String relatorio = sconContext.getRealPath("/resources/relatorios/emergenciaCirurgia.jasper");

                //Fonte de Dados
                JRBeanCollectionDataSource fonteDados = new JRBeanCollectionDataSource(cirurgias);
                HashMap map = new HashMap();
                map.put("usuarioNome", usuario.getNome());

                //Gerar Relatorio
                JasperPrint relatorioGerado =
                        JasperFillManager.fillReport(relatorio, map, fonteDados);


                byte[] b = JasperExportManager.exportReportToPdf(relatorioGerado);

                HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                res.setContentType("application/pdf");

                String nome = usuario.getNome().replace(" ", "_");

                //Código abaixo gerar o relatório e disponibiliza diretamente na página 
                res.setHeader("Content-disposition", "inline;filename=" + nome + "_Cirurgias.pdf");

                //Código abaixo gerar o relatório e disponibiliza para o cliente baixar ou salvar 
                res.getOutputStream().write(b);
                res.getCharacterEncoding();
                FacesContext.getCurrentInstance().responseComplete();

            } catch (Exception ex) {
                FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Gerar relatorio: " + ex.getMessage(), "erro");
                FacesContext.getCurrentInstance().addMessage(null, msgErro);
            }
        } else {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não há cirurgias a serem salvas ou impressas.", "erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }

    }    
    
    //RELATORIO PDF INFORMAÇOES PESSOAIS
    public void relatorioInfoPessoais() {
        try {
            //Servelt Contex para o caminho
            ServletContext sconContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

            //Arquivo Relatorio
            String relatorio = sconContext.getRealPath("/resources/relatorios/emergenciaInfoPessoal.jasper");

            //Fonte de Dados
            ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
            usuarios.add(usuario);
            JRBeanCollectionDataSource fonteDados = new JRBeanCollectionDataSource(usuarios);

            //Gerar Relatorio
            JasperPrint relatorioGerado =
                    JasperFillManager.fillReport(relatorio, null, fonteDados);


            byte[] b = JasperExportManager.exportReportToPdf(relatorioGerado);

            HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            res.setContentType("application/pdf");

            String nome = usuario.getNome().replace(" ", "_");

            //Código abaixo gerar o relatório e disponibiliza diretamente na página 
            res.setHeader("Content-disposition", "inline;filename=" + nome + "_InfoPessoal.pdf");

            //Código abaixo gerar o relatório e disponibiliza para o cliente baixar ou salvar 
            res.getOutputStream().write(b);
            res.getCharacterEncoding();
            FacesContext.getCurrentInstance().responseComplete();

        } catch (Exception ex) {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Gerar relatorio: " + ex.getMessage(), "erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }

    }
    
    //RELATORIO QUE CONTEM TODOS OS DEMAIS RELATORIOS
    public void relatorioGeral() {
        //Lista com todos Relatorios.
        List todosRelatorios = new ArrayList();

        //Parametro com nome de usuario
        HashMap map = new HashMap();
        map.put("usuarioNome", usuario.getNome());

        //Servelt Contex para o caminho
        ServletContext sconContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        try {
            //------------------- RELATORIO INFO PESSOAL ----------------
            //Arquivo Relatorio
            String infoPessoal = sconContext.getRealPath("/resources/relatorios/emergenciaInfoPessoal.jasper");

            //Fonte de Dados
            ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
            usuarios.add(usuario);
            JRBeanCollectionDataSource fonteDadosGambiarra = new JRBeanCollectionDataSource(usuarios);

            //Gerar Relatorio
            JasperPrint relatorioInfoPessoal =
                    JasperFillManager.fillReport(infoPessoal, null, fonteDadosGambiarra);

            //Adiciona o relatório a lista
            todosRelatorios.add(relatorioInfoPessoal);

            //----------------- RELATORIO CONTATOS EMERGENCIAL ---------------------
            if (!contatoEmergencias.isEmpty()) {
                //Arquivo Relatorio
                String relatorio = sconContext.getRealPath("/resources/relatorios/emergenciaContato.jasper");

                //Fonte de Dados
                JRBeanCollectionDataSource fonteDados = new JRBeanCollectionDataSource(contatoEmergencias);

                //Gerar Relatorio
                JasperPrint relatorioGerado =
                        JasperFillManager.fillReport(relatorio, map, fonteDados);

                //Adiciona o relatório a lista
                todosRelatorios.add(relatorioGerado);

            }

            //---------------------- RELATORIO ALERGIAS ---------------------------
            if (!alergias.isEmpty()) {
                //Arquivo Relatorio
                String relatorio = sconContext.getRealPath("/resources/relatorios/emergenciaAlergia.jasper");

                //Fonte de Dados
                JRBeanCollectionDataSource fonteDados = new JRBeanCollectionDataSource(alergias);

                //Gerar Relatorio
                JasperPrint relatorioGerado =
                        JasperFillManager.fillReport(relatorio, map, fonteDados);

                //Adiciona o relatório a lista
                todosRelatorios.add(relatorioGerado);
            }

            //------------------------- RELATORIO DOENCA CRONICA ----------------------
            if (!doencaCronicas.isEmpty()) {
                //Arquivo Relatorio
                String relatorio = sconContext.getRealPath("/resources/relatorios/emergenciaDoencasCronicas.jasper");

                //Fonte de Dados
                JRBeanCollectionDataSource fonteDados = new JRBeanCollectionDataSource(doencaCronicas);

                //Gerar Relatorio
                JasperPrint relatorioGerado =
                        JasperFillManager.fillReport(relatorio, map, fonteDados);

                //Adiciona o relatório a lista
                todosRelatorios.add(relatorioGerado);

            }

            //--------------------- RELATORIO MEDICAMENTO CONTROLADO -------------------
            if (!medicamentoControlados.isEmpty()) {
                //Arquivo Relatorio
                String relatorio = sconContext.getRealPath("/resources/relatorios/emergenciaMedicamentos.jasper");

                //Fonte de Dados
                JRBeanCollectionDataSource fonteDados = new JRBeanCollectionDataSource(medicamentoControlados);

                //Gerar Relatorio
                JasperPrint relatorioGerado =
                        JasperFillManager.fillReport(relatorio, map, fonteDados);

                //Adiciona o relatório a lista
                todosRelatorios.add(relatorioGerado);

            }

            //--------------------- RELATORIO VACINAS -------------------
            if (!vacinas.isEmpty()) {
                //Arquivo Relatorio
                String relatorio = sconContext.getRealPath("/resources/relatorios/emergenciaVacinas.jasper");

                //Fonte de Dados
                JRBeanCollectionDataSource fonteDados = new JRBeanCollectionDataSource(vacinas);

                //Gerar Relatorio
                JasperPrint relatorioGerado =
                        JasperFillManager.fillReport(relatorio, map, fonteDados);

                //Adiciona o relatório a lista
                todosRelatorios.add(relatorioGerado);

            }

            //--------------------- RELATORIO CIRURGIAS -------------------
            if (!cirurgias.isEmpty()) {
                //Arquivo Relatorio
                String relatorio = sconContext.getRealPath("/resources/relatorios/emergenciaCirurgia.jasper");

                //Fonte de Dados
                JRBeanCollectionDataSource fonteDados = new JRBeanCollectionDataSource(cirurgias);

                //Gerar Relatorio
                JasperPrint relatorioGerado =
                        JasperFillManager.fillReport(relatorio, map, fonteDados);

                //Adiciona o relatório a lista
                todosRelatorios.add(relatorioGerado);

            }
            
            
            //<><><><><><><> GERAR RELATORIO GERAL <><><><><><><><>
            JRPdfExporter exporter = new JRPdfExporter();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response =
                    (HttpServletResponse) fc.getExternalContext().getResponse();
            ByteArrayOutputStream retorno = new ByteArrayOutputStream();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, todosRelatorios);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, retorno);
            exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
            exporter.exportReport();

            byte[] retornoArray = retorno.toByteArray();
            String nome = usuario.getNome().replace(" ", "_");

            response.setHeader("Content-disposition", "inline;filename=" + nome + "_Emergencia.pdf");
            response.setContentType("application/pdf");
            response.setContentLength(retornoArray.length);

            OutputStream output = response.getOutputStream();
            output.write(retornoArray);
            output.flush();
            output.close();

        } catch (Exception ex) {
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Gerar relatorio: " + ex.getMessage(), "erro");
            FacesContext.getCurrentInstance().addMessage(null, msgErro);
        }
    }

    //GETTERS

    public String getCodEmergencia() {
        return codEmergencia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Alergia getAlergiaSeleciona() {
        return alergiaSeleciona;
    }

    public List<Alergia> getAlergias() {
        return alergias;
    }

    public DoencaCronica getDc() {
        return Dc;
    }

    public List<DoencaCronica> getDoencaCronicas() {
        return doencaCronicas;
    }

    public ContatoEmergencia getContatoEmergencia() {
        return contatoEmergencia;
    }

    public List<ContatoEmergencia> getContatoEmergencias() {
        return contatoEmergencias;
    }

    public MedicamentoControlado getMedicamentoControlado() {
        return medicamentoControlado;
    }

    public List<MedicamentoControlado> getMedicamentoControlados() {
        return medicamentoControlados;
    }

    public Vacina getVacina() {
        return vacina;
    }

    public List<Vacina> getVacinas() {
        return vacinas;
    }

    public Cirurgia getCirurgia() {
        return cirurgia;
    }

    public List<Cirurgia> getCirurgias() {
        return cirurgias;
    }
    

    //SETTERS
    public void setCodEmergencia(String codEmergencia) {
        this.codEmergencia = codEmergencia;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setAlergiaSeleciona(Alergia alergiaSeleciona) {
        this.alergiaSeleciona = alergiaSeleciona;
    }

    public void setAlergias(List<Alergia> alergias) {
        this.alergias = alergias;
    }

    public void setDc(DoencaCronica Dc) {
        this.Dc = Dc;
    }

    public void setDoencaCronicas(List<DoencaCronica> doencaCronicas) {
        this.doencaCronicas = doencaCronicas;
    }

    public void setContatoEmergencia(ContatoEmergencia contatoEmergencia) {
        this.contatoEmergencia = contatoEmergencia;
    }

    public void setContatoEmergencias(List<ContatoEmergencia> contatoEmergencias) {
        this.contatoEmergencias = contatoEmergencias;
    }

    public void setMedicamentoControlado(MedicamentoControlado medicamentoControlado) {
        this.medicamentoControlado = medicamentoControlado;
    }

    public void setMedicamentoControlados(List<MedicamentoControlado> medicamentoControlados) {
        this.medicamentoControlados = medicamentoControlados;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public void setVacinas(List<Vacina> vacinas) {
        this.vacinas = vacinas;
    }

    public void setCirurgia(Cirurgia cirurgia) {
        this.cirurgia = cirurgia;
    }

    public void setCirurgias(List<Cirurgia> cirurgias) {
        this.cirurgias = cirurgias;
    }
    
}
