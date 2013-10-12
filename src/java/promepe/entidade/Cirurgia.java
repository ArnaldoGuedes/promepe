package promepe.entidade;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Cirurgia implements Serializable {
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private String localOperado = "";
    @Column(nullable=true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataOperacao;
    @Column(nullable=true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataAdd;
    @Column(nullable=true)
    private String descricao = "";

    //GETTERS
    public Long getId() {
        return id;
    }

    public String getLocalOperado() {
        return localOperado;
    }

    public Date getDataOperacao() {
        return dataOperacao;
    }

    public Date getDataAdd() {
        return dataAdd;
    }

    public String getDescricao() {
        return descricao;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    
    
    //SETTERS
    public void setId(Long id) {
        this.id = id;
    }

    public void setDataOperacao(Date dataOperacao) {
        this.dataOperacao = dataOperacao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setLocalOperado(String localOperado) {
        this.localOperado = localOperado;
    }

    public void setDataAdd(Date dataAdd) {
        this.dataAdd = dataAdd;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        if (!(object instanceof Cirurgia)) {
            return false;
        }
        Cirurgia other = (Cirurgia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "promepe.entidade.Cirurgia[ id=" + id + " ]";
    }
    
}
