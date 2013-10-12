package promepe.persistencia;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import promepe.entidade.Cirurgia;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
public class CirurgiaDAO {
    EntityManager ge = GerenciadorEntidade.getGerenciadorEntidade();
        
        /**
         * Persiste um objeto do tipo cirurgia no Banco de Dados.
         * @param cirurgia
         */
        public void adicionarCirurgia(Cirurgia cirurgia){
            ge.getTransaction().begin();
            ge.persist(cirurgia);
            ge.getTransaction().commit();
        }
        
        public void alterarCirurgia(Cirurgia cirurgia){
            ge.getTransaction().begin();
            ge.merge(cirurgia);
            ge.flush();
            ge.getTransaction().commit();
        }
        
        public void excluirCirurgia(Cirurgia cirurgia){
            ge.getTransaction().begin();
            ge.remove(cirurgia);
            ge.getTransaction().commit();
        }
        /**
         * Retorna todas as cirurgias de usuario identificado pelo ID.
         * @param id do usuario que se pretende obter as cirurgias.
         * @return Todas as cirurgias de um determinado usuario
         */
        public List<Cirurgia> obterTodasCirurgiasUsuarioId(long id){
            Query query = ge.createQuery("SELECT c FROM Cirurgia c WHERE c.usuario.id =:id");
            query.setParameter("id", id);
            return query.getResultList();
        }
    
}
