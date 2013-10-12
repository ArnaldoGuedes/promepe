package promepe.persistencia;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import promepe.entidade.MedicamentoControlado;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
public class MedicamentoControladoDAO {
    EntityManager ge = GerenciadorEntidade.getGerenciadorEntidade();
        
        /**
         * Persiste um objeto do tipo MedicamentoControlado no Banco de Dados.
         * @param medicamentoControlado
         */
        public void adicionarMedicamentoControlado(MedicamentoControlado medicamentoControlado){
            ge.getTransaction().begin();
            ge.persist(medicamentoControlado);
            ge.getTransaction().commit();
        }
        
        public void alterarMedicamentoControlado(MedicamentoControlado medicamentoControlado){
            ge.getTransaction().begin();
            ge.merge(medicamentoControlado);
            ge.flush();
            ge.getTransaction().commit();
        }
        
        public void excluirMedicamentoControlado(MedicamentoControlado medicamentoControlado){
            ge.getTransaction().begin();
            ge.remove(medicamentoControlado);
            ge.getTransaction().commit();
        }
        /**
         * Retorna todas os medicamento controlados de usuario identificado pelo ID.
         * @param id do usuario que se pretende obter as medicamentoControlados.
         * @return Todas as medicamentoControlados de um determinado usuario
         */
        public List<MedicamentoControlado> obterTodosMedicamentoControladosUsuarioId(long id){
            Query query = ge.createQuery("SELECT c FROM MedicamentoControlado c WHERE c.usuario.id =:id");
            query.setParameter("id", id);
            return query.getResultList();
        }

        public MedicamentoControlado obterMedicamentoControladoNomeUsuarioId(long id, String medicamentoControladoNome){
            Query query = ge.createQuery("SELECT c FROM MedicamentoControlado c WHERE  (c.usuario.id =:id) AND (LOWER(c.nome) =:medicamentoControladoNome)");
            query.setParameter("id", id);
            query.setParameter("medicamentoControladoNome", medicamentoControladoNome);
            return (MedicamentoControlado) query.getSingleResult();
        }

}
