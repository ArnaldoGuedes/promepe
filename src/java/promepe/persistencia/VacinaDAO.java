package promepe.persistencia;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import promepe.entidade.Vacina;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
public class VacinaDAO {
        EntityManager ge = GerenciadorEntidade.getGerenciadorEntidade();
        
        /**
         * Persiste um objeto do tipo vacina no Banco de Dados.
         * @param vacina
         */
        public void adicionarVacina(Vacina vacina){
            ge.getTransaction().begin();
            ge.persist(vacina);
            ge.getTransaction().commit();
        }
        
        public void alterarVacina(Vacina vacina){
            ge.getTransaction().begin();
            ge.merge(vacina);
            ge.getTransaction().commit();
        }
        
        public void excluirVacina(Vacina vacina){
            ge.getTransaction().begin();
            ge.remove(vacina);
            ge.getTransaction().commit();
        }
        /**
         * Retorna todas as vacinas de usuario identificado pelo ID.
         * @param id do usuario que se pretende obter as vacinas.
         * @return Todas as vacinas de um determinado usuario
         */
        public List<Vacina> obterTodasVacinasUsuarioId(long id){
            Query query = ge.createQuery("SELECT c FROM Vacina c WHERE c.usuario.id =:id");
            query.setParameter("id", id);
            List<Vacina> vacinas = query.getResultList();
            return vacinas;
        }
        
        public Vacina obterVacinaNomeUsuarioId(long id, String vacinaNome){
            Query query = ge.createQuery("SELECT c FROM Vacina c WHERE  (c.usuario.id =:id) AND (LOWER(c.nome) =:vacinaNome)");
            query.setParameter("id", id);
            query.setParameter("vacinaNome", vacinaNome);
            return (Vacina) query.getSingleResult();
        }

}
