package promepe.negocio;

import java.util.List;
import promepe.entidade.DoencaCronica;
import promepe.persistencia.DoencaCronicaDAO;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
public class DoencaCronicaBO {
    private DoencaCronicaDAO doencaCronicaDAO = new DoencaCronicaDAO();
    
    public void adicionarDoencaCronica(DoencaCronica dC){
        DoencaCronica obterDoencaCronica;
        try{
            obterDoencaCronica = doencaCronicaDAO.obterDoencaNomeUsuarioId(dC.getUsuario().getId(), dC.getNome().toLowerCase());
        }catch(Exception e){
            obterDoencaCronica = new DoencaCronica();
        }
        
        if (!obterDoencaCronica.getNome().equals("")) {
            throw new RuntimeException("A doença '"+ dC.getNome() +"' já existe.");
        }
        
        doencaCronicaDAO.adicionarDoencaCronica(dC);
    }
    
    public void alterarDoencaCronica(DoencaCronica dC){
        DoencaCronica obterDoencaCronica;
        try{
            obterDoencaCronica = doencaCronicaDAO.obterDoencaNomeUsuarioId(dC.getUsuario().getId(), dC.getNome().toLowerCase());
        }catch(Exception e){
            obterDoencaCronica = new DoencaCronica();
        }
        
        if ((!obterDoencaCronica.getNome().equals("")) && (obterDoencaCronica.getId() != dC.getId())) {
            throw new RuntimeException("A doença '"+ dC.getNome() +"' já existe.");
        }               
        
        doencaCronicaDAO.alterarDoencaCronica(dC);
    } 
    
    public void excluirDoencaCronica(DoencaCronica dC){
        doencaCronicaDAO.excluirDoencaCronica(dC);
    }
    
    public List<DoencaCronica> obterTodasDoencasCronicasUsuarioId(long id){
        return doencaCronicaDAO.obterTodasDoencasCronicas(id);
    }
    
}
