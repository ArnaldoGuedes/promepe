package promepe.persistencia;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import promepe.entidade.Alergia;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
public class AlergiaDAO {
    EntityManager ge = GerenciadorEntidade.getGerenciadorEntidade();
        
        /**
         * Persiste um objeto do tipo alergia no Banco de Dados.
         * @param alergia
         */
        public void adicionarAlergia(Alergia alergia){
            ge.getTransaction().begin();
            ge.persist(alergia);
            ge.getTransaction().commit();
        }
        
        public void alterarAlergia(Alergia alergia){
            ge.getTransaction().begin();
            ge.merge(alergia);
            ge.flush();
            ge.getTransaction().commit();
        }
        
        public void excluirAlergia(Alergia alergia){
            ge.getTransaction().begin();
            ge.remove(alergia);
            ge.getTransaction().commit();
        }
        /**
         * Retorna todas as alergias de usuario identificado pelo ID.
         * @param id do usuario que se pretende obter as alergias.
         * @return Todas as alergias de um determinado usuario
         */
        public List<Alergia> obterTodasAlergiasUsuarioId(long id){
            Query query = ge.createQuery("SELECT c FROM Alergia c WHERE c.usuario.id =:id");
            query.setParameter("id", id);
            return query.getResultList();
        }

        public Alergia obterAlergiaNomeUsuarioId(long id, String alergiaNome){
            Query query = ge.createQuery("SELECT c FROM Alergia c WHERE  (c.usuario.id =:id) AND (LOWER(c.nome) =:alergiaNome)");
            query.setParameter("id", id);
            query.setParameter("alergiaNome", alergiaNome);
            return (Alergia) query.getSingleResult();
        }

}
