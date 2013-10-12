package promepe.persistencia;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import promepe.entidade.DoencaCronica;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
public class DoencaCronicaDAO {
         EntityManager ge = GerenciadorEntidade.getGerenciadorEntidade();
        
        /**
         * Persiste um objeto do tipo DoencaCronica no Banco de Dados.
         * @param DoencaCronica
         */
        public void adicionarDoencaCronica(DoencaCronica doencaCronica){
            ge.getTransaction().begin();
            ge.persist(doencaCronica);
            ge.getTransaction().commit();
        }
        
        public void alterarDoencaCronica(DoencaCronica doencaCronica){
            ge.getTransaction().begin();
            ge.merge(doencaCronica);
            ge.getTransaction().commit();
        }
        
        public void excluirDoencaCronica(DoencaCronica doencaCronica){
            ge.getTransaction().begin();
            ge.remove(doencaCronica);
            ge.getTransaction().commit();
        }
        /**
         * Retorna todas as Doencas Cronicas de usuario identificado pelo ID.
         * @param id do usuario que se pretende obter as Doencas Cronicas.
         * @return Todas as Doencas Cronicas de um determinado usuario
         */
        public List<DoencaCronica> obterTodasDoencasCronicas(long id){
            Query query = ge.createQuery("SELECT c FROM DoencaCronica c WHERE c.usuario.id =:id");
            query.setParameter("id", id);
            return query.getResultList();
        }
        
        public DoencaCronica obterDoencaNomeUsuarioId(long id, String doencaNome){
            Query query = ge.createQuery("SELECT c FROM DoencaCronica c WHERE  (c.usuario.id =:id) AND (LOWER(c.nome) =:doencaNome)");
            query.setParameter("id", id);
            query.setParameter("doencaNome", doencaNome);
            return (DoencaCronica) query.getSingleResult();
        }

}
