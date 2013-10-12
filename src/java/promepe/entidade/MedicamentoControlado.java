package promepe.entidade;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
@Entity
public class MedicamentoControlado implements Serializable {
    @ManyToOne
    private Usuario usuario;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 25)
    private String nome = "";
    @Column(nullable = true, length = 25)
    private String laboratorio = "";
    @Column(nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataInicio;
    @Column(nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataAdd;
    @Column(nullable = true)
    private String descricao = "";
    
    //GETTERS
    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getNome() {
        return nome;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataAdd() {
        return dataAdd;
    }

    public String getDescricao() {
        return descricao;
    }
    
    
    //SETTERS
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataAdd(Date dataAdd) {
        this.dataAdd = dataAdd;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicamentoControlado)) {
            return false;
        }
        MedicamentoControlado other = (MedicamentoControlado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "promepe.entidade.MedicamentoControlado[ id=" + id + " ]";
    }
    
}
