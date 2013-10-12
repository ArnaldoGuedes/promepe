package promepe.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
public class GerenciadorEntidade {
    private static EntityManager gerenciadorEntidade = null;
    
    /**
     * Metodo criado com a finalidade de retornar o gerenciador de entidade.
     * @return um objeto do tipo EntityManager, Reponsal por gerenciar o Banco de dados.
     */
    public static EntityManager getGerenciadorEntidade(){
        if (gerenciadorEntidade == null || !gerenciadorEntidade.isOpen()){
            gerenciadorEntidade = Persistence.createEntityManagerFactory("PromepePU").createEntityManager();
        }
        return gerenciadorEntidade;
    }
}
