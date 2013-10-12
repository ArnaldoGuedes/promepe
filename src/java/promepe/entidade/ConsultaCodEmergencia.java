package promepe.entidade;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
@Entity
public class ConsultaCodEmergencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String codEmergencia = "";
    @Column
    private Long codUsuario;
    @Column
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataConsulta;
    
    //GETTERS
    public Long getId() {
        return id;
    }

    public String getCodEmergencia() {
        return codEmergencia;
    }

    public Long getCodUsuario() {
        return codUsuario;
    }

    public Date getDataConsulta() {
        return dataConsulta;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    public void setCodEmergencia(String codEmergencia) {
        this.codEmergencia = codEmergencia;
    }

    public void setCodUsuario(Long codUsuario) {
        this.codUsuario = codUsuario;
    }

    public void setDataConsulta(Date dataConsulta) {
        this.dataConsulta = dataConsulta;
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
        if (!(object instanceof ConsultaCodEmergencia)) {
            return false;
        }
        ConsultaCodEmergencia other = (ConsultaCodEmergencia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "promepe.entidade.ConsultaCodEmergencia[ id=" + id + " ]";
    }
    
}
