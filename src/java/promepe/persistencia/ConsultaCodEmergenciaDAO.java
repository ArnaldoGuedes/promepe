package promepe.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import promepe.entidade.ConsultaCodEmergencia;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
public class ConsultaCodEmergenciaDAO {
    EntityManager ge = GerenciadorEntidade.getGerenciadorEntidade();

    /**
         * Persiste um objeto do tipo alergia no Banco de Dados.
         * @param ConsultaCodEmergencia
         */
        public void adicionarConsultaCodEmergencia(ConsultaCodEmergencia consultaCodEmergencia){
            ge.getTransaction().begin();
            ge.persist(consultaCodEmergencia);
            ge.getTransaction().commit();
        }

        public void atualizaConsultaCodEmergencia(ConsultaCodEmergencia consultaCodEmergencia){
            ge.getTransaction().begin();
            ge.merge(consultaCodEmergencia);
            ge.flush();
            ge.getTransaction().commit();
        }
        
        public ConsultaCodEmergencia obterConsultaCodEmergencia(long idUsuario, String codEmergencia){
            Query query = ge.createQuery("SELECT c FROM ConsultaCodEmergencia c WHERE  (c.codUsuario =:id) AND (c.codEmergencia =:codigo)");
            query.setParameter("id", idUsuario);
            query.setParameter("codigo", codEmergencia);
            return (ConsultaCodEmergencia) query.getSingleResult();
        }

    
}
