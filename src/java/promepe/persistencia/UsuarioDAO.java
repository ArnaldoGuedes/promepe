package promepe.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import promepe.entidade.Usuario;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
public class UsuarioDAO {
    EntityManager ge = GerenciadorEntidade.getGerenciadorEntidade();
    
    /**
     * Metodo com a finalidade apenas de salvar um usuario.
     * @param usuario Objeto do tipo usuario.
     */
    public void cadastrarUsuario(Usuario usuario){
        ge.getTransaction().begin();
        ge.persist(usuario);
        ge.getTransaction().commit();
    }
    
    /**
     * Metodo que busca um usuario no banco de dados, pelo E-Mail.
     * @param email tipo String
     * @return Objeto do tipo Usuario.
     */
    public Usuario obterUsuario(String email){
        Query query = ge.createQuery("SELECT u FROM Usuario u WHERE u.email =:email");
        query.setParameter("email", email);      
        Usuario usuario = (Usuario) query.getSingleResult();
        return usuario;
    }
    
    /**
     * Obtem o usuario atravez do codigo de emergencia.
     * @param CodEmergencia tipo String.
     * @return 
     */
    public Usuario obterUsuarioCodEmergencia(String CodEmergencia){
        Query query = ge.createQuery("SELECT u FROM Usuario u WHERE u.codEmergencia =:codEmergencia");
        query.setParameter("codEmergencia", CodEmergencia);
        Usuario usuario = (Usuario) query.getSingleResult();
        return usuario;
    }
            
    public void alterarUsuario(Usuario usuario){
        this.cadastrarUsuario(usuario);
    }
}
