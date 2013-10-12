package promepe.negocio;

import java.util.Date;
import java.util.List;
import promepe.entidade.Cirurgia;
import promepe.persistencia.CirurgiaDAO;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
public class CirurgiaBO {
    private CirurgiaDAO cirurgiaDAO = new CirurgiaDAO();
    
    public void adicionarCirurgia(Cirurgia cirurgia){        
        //Testar se a data esta vazia.
        boolean dataVazia = true;
        try {
            cirurgia.getDataOperacao().compareTo(new Date()); //Gambiarra! =D
        }catch(Exception e){
            dataVazia = false;
        } 
        
        if(dataVazia){
            if (cirurgia.getDataOperacao().after(new Date())) {
                throw new RuntimeException("A data da cirurgia não pode ser posterior a data atual.");
            }

            if (!(cirurgia.getUsuario().getDataNascimento().before(cirurgia.getDataOperacao()))) {
                throw new RuntimeException("A data da cirurgia não pode ser antes do nascimento do usuário.");
            }

        }
        //Se as validaçõe estiverem corretas, passa a cirurgia para a camada de persistencia.
        cirurgiaDAO.adicionarCirurgia(cirurgia);
    }
    
    public void alterarCirurgia(Cirurgia cirurgia){        
        //Testar se a data esta vazia.
        boolean dataVazia = true;
        try {
            cirurgia.getDataOperacao().compareTo(new Date()); //Gambiarra! =D
        }catch(Exception e){
            dataVazia = false;
        } 
        
        if(dataVazia){
            if (cirurgia.getDataOperacao().after(new Date())) {
                throw new RuntimeException("A data da cirurgia não pode ser posterior a data atual.");
            }

            if (!(cirurgia.getUsuario().getDataNascimento().before(cirurgia.getDataOperacao()))) {
                throw new RuntimeException("A data da cirurgia não pode ser antes do nascimento do usuário.");
            }

        }
        
        cirurgiaDAO.alterarCirurgia(cirurgia);
    } 
    
    public void excluirCirurgia(Cirurgia cirurgia){
        cirurgiaDAO.excluirCirurgia(cirurgia);
    }
    
    public List<Cirurgia> obterTodasCirurgiasUsuarioId(long id){
        return cirurgiaDAO.obterTodasCirurgiasUsuarioId(id);
    }
    
}
