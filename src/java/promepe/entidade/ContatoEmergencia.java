package promepe.entidade;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
@Entity
public class ContatoEmergencia implements Serializable {
    @ManyToOne
    private Usuario usuario;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 75)
    private String nome = "";
    @Column(nullable = false, length = 20)
    private String parentesco = "";
    @Column(nullable = false)
    private String telefone1 = "";
    @Column(nullable = true)
    private String telefone2 = "";
    @Column(nullable = true)
    private String email = "";
    @Column(nullable = true)
    private String descricao = "";
    //GETTERS
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public String getEmail() {
        return email;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getParentesco() {
        return parentesco;
    }

    public String getDescricao() {
        return descricao;
    }
    
    
    //SETTERS
    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
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
        if (!(object instanceof ContatoEmergencia)) {
            return false;
        }
        ContatoEmergencia other = (ContatoEmergencia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "promepe.entidade.ContatoEmergencia[ id=" + id + " ]";
    }
    
}
