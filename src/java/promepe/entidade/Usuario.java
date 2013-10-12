package promepe.entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
@Entity
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true)
    private String codEmergencia = "";
    @Column(nullable = false)
    private String nome = "";
    @Column(nullable = false)
    private String email = "";
    @Column(nullable = false)
    private String senha = "";
    @Column(nullable = false)
    private char sexo;
    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataNascimento;
    @Column(nullable = true)
    private String tipoSanguineo = "";
    @Column(nullable = true)
    private Double peso;
    @Column(nullable = true)
    private Double altura;
    @Column(nullable = true)
    private boolean contaAtiva = true;
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Vacina> vacinas;
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<DoencaCronica> doencaCronicas;
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)    
    private List<Alergia> alergias;
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<MedicamentoControlado> medicamentoControlados;
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<ContatoEmergencia> contatoEmergencias;
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Cirurgia> cirurgias;
    
    @Column(nullable = true)
    private String nomeCompleto = "";
    @Column(nullable = true)
    private String telefoneCasa = "";
    @Column(nullable = true)
    private String informacoesImportantes = "";
    @Column(nullable = true)
    private String nomeMae = "";

    // -------- GETTERS
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public char getSexo() {
        return sexo;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public Double getPeso() {
        return peso;
    }

    public Double getAltura() {
        return altura;
    }
    
    public String getTipoSanguineo() {
        return tipoSanguineo;
    }

    public List<Vacina> getVacinas() {
        return vacinas;
    }

    public boolean isContaAtiva() {
        return contaAtiva;
    }

    public String getCodEmergencia() {
        return codEmergencia;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public List<ContatoEmergencia> getContatoEmergencias() {
        return contatoEmergencias;
    }

    public List<Cirurgia> getCirurgias() {
        return cirurgias;
    }
    
    
        
    /**
     * Metodo criado para calcular a idade em anos.
     * @return int, que representa a idade em Anos.
     */
    public int getIdade() {
        GregorianCalendar hj = new GregorianCalendar();

        GregorianCalendar nascimento = new GregorianCalendar();
        nascimento.setTime(dataNascimento);

        // Verifica se a pessoa nasceu no ano corrente
        if (nascimento.get(GregorianCalendar.YEAR) == hj.get(GregorianCalendar.YEAR)) {
            return 0;
        } else {
            int idade = -1;
            
            //Calcula a idade ate chegar o ano corrente       
            while (nascimento.get(GregorianCalendar.YEAR) < hj.get(GregorianCalendar.YEAR)) {
                idade++;
                nascimento.add(GregorianCalendar.YEAR, 1);
            }

            //Testa se ja ouve aniversario no ano corrente
            if ((idade > 0)
                    && (nascimento.get(GregorianCalendar.MONTH) <= hj.get(GregorianCalendar.DAY_OF_MONTH))
                    && (nascimento.get(GregorianCalendar.DAY_OF_MONTH) <= hj.get(GregorianCalendar.DAY_OF_MONTH))) {

                idade++;
            }

            return idade;
        }
    }

    public List<DoencaCronica> getDoencaCronicas() {
        return doencaCronicas;
    }

    public List<Alergia> getAlergias() {
        return alergias;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getTelefoneCasa() {
        return telefoneCasa;
    }

    public String getInformacoesImportantes() {
        return informacoesImportantes;
    }

    public List<MedicamentoControlado> getMedicamentoControlados() {
        return medicamentoControlados;
    }
    
    
    //--------- SETTER
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }
    
    public void setVacinas(List<Vacina> vacinas) {
        this.vacinas = vacinas;
    }

    public void setContaAtiva(boolean contaAtiva) {
        this.contaAtiva = contaAtiva;
    }

    public void setDoencaCronicas(List<DoencaCronica> doencaCronicas) {
        this.doencaCronicas = doencaCronicas;
    }

    public void setAlergias(List<Alergia> alergias) {
        this.alergias = alergias;
    }

    public void setCodEmergencia(String codEmergencia) {
        this.codEmergencia = codEmergencia;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public void setTelefoneCasa(String telefoneCasa) {
        this.telefoneCasa = telefoneCasa;
    }

    public void setInformacoesImportantes(String informacoesImportantes) {
        this.informacoesImportantes = informacoesImportantes;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public void setContatoEmergencias(List<ContatoEmergencia> contatoEmergencias) {
        this.contatoEmergencias = contatoEmergencias;
    }

    public void setCirurgias(List<Cirurgia> cirurgias) {
        this.cirurgias = cirurgias;
    }
    
    
    //--------- GERADOS PELO NETBEANS
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "promepe.entidade.Usuario[ id=" + id + " ]";
    }

    public void setMedicamentoControlados(List<MedicamentoControlado> medicamentoControlados) {
        this.medicamentoControlados = medicamentoControlados;
    }
    
    
}
