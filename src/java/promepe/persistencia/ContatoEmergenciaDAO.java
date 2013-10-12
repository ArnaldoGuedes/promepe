package promepe.persistencia;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import promepe.entidade.ContatoEmergencia;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
public class ContatoEmergenciaDAO {
    EntityManager ge = GerenciadorEntidade.getGerenciadorEntidade();
        
        /**
         * Persiste um objeto do tipo Contato Emergencia no Banco de Dados.
         * @param contato
         */
        public void adicionarContatoEmergencia(ContatoEmergencia contatoEmergencia){
            ge.getTransaction().begin();
            ge.persist(contatoEmergencia);
            ge.getTransaction().commit();
        }
        
        public void alterarContatoEmergencia(ContatoEmergencia contatoEmergencia){
            ge.getTransaction().begin();
            ge.merge(contatoEmergencia);
            ge.flush();
            ge.getTransaction().commit();
        }
        
        public void excluirContatoEmergencia(ContatoEmergencia contatoEmergencia){
            ge.getTransaction().begin();
            ge.remove(contatoEmergencia);
            ge.getTransaction().commit();
        }
        /**
         * Retorna todas as contatoEmergencias de usuario identificado pelo ID.
         * @param id do usuario que se pretende obter as contatoEmergencias.
         * @return Todas as contatoEmergencias de um determinado usuario
         */
        public List<ContatoEmergencia> obterTodasContatoEmergenciasUsuarioId(long id){
            Query query = ge.createQuery("SELECT c FROM ContatoEmergencia c WHERE c.usuario.id =:id");
            query.setParameter("id", id);
            return query.getResultList();
        }
    
}
