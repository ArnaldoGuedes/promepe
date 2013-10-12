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
public class Vacina implements Serializable {
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 25)
    private String nome = "";
    @Column(nullable = true)
    private String descricao = "";
    @Column(nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataVacina;
    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataAdd;
            
    //GETTER
    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Date getDataVacina() {
        return dataVacina;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getNome() {
        return nome;
    }

    public Date getDataAdd() {
        return dataAdd;
    }
    
    

    //SETTER
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDataVacina(Date dataVacina) {
        this.dataVacina = dataVacina;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataAdd(Date dataAdd) {
        this.dataAdd = dataAdd;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

    
    
    //GERADOS PELO NETBEANS
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vacina)) {
            return false;
        }
        Vacina other = (Vacina) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "promepe.entidade.Vacina[ id=" + id + " ]";
    }
    
}
