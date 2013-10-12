package promepe.negocio;

import java.util.Date;
import java.util.List;
import promepe.entidade.Alergia;
import promepe.persistencia.AlergiaDAO;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
public class AlergiaBO {
    private AlergiaDAO alergiaDAO = new AlergiaDAO();
    
    public void adicionarAlergia(Alergia alergia){
        //Verifica se ja existe Alergia com o mesmo nome
        Alergia obterAlergia;
        try{
            obterAlergia = alergiaDAO.obterAlergiaNomeUsuarioId(alergia.getUsuario().getId(), alergia.getNome().toLowerCase());
        }catch(Exception e){
            obterAlergia = new Alergia();
        }
        
        if (!obterAlergia.getNome().equals("")) {
            throw new RuntimeException("A alergia '"+ alergia.getNome() +"' já existe.");
        }
        
        //Verifica se a data da ultima reação alergica é valida.
                //Testar se a data esta vazia.
        boolean dataVazia = true;
        try {
            alergia.getUltimaReacao().compareTo(new Date()); //Gambiarra! =D
        }catch(Exception e){
            dataVazia = false;
        } 
        
        if(dataVazia){
            if (alergia.getUltimaReacao().after(new Date())) {
                throw new RuntimeException("A data da ultima reação alérgica não pode ser posterior a data atual.");
            }

            if (!(alergia.getUsuario().getDataNascimento().before(alergia.getUltimaReacao()))) {
                throw new RuntimeException("A data da ultima reação alérgica não pode ser antes do nascimento do usuário.");
            }

        }
        //Se as validaçõe estiverem corretas, passa a alergia para a camada de persistencia.
        alergiaDAO.adicionarAlergia(alergia);
    }
    
    public void alterarAlergia(Alergia alergia){
        Alergia obterAlergia;
        try{
            obterAlergia = alergiaDAO.obterAlergiaNomeUsuarioId(alergia.getUsuario().getId(), alergia.getNome().toLowerCase());
        }catch(Exception e){
            obterAlergia = new Alergia();
        }
        
        if ((!obterAlergia.getNome().equals("")) && (obterAlergia.getId() != alergia.getId())) {
            throw new RuntimeException("A alergia '"+ alergia.getNome() +"' já existe.");
        }
        
                //Verifica se a data da ultima reação alergica é valida.
                //Testar se a data esta vazia.
        boolean dataVazia = true;
        try {
            alergia.getUltimaReacao().compareTo(new Date()); //Gambiarra! =D
        }catch(Exception e){
            dataVazia = false;
        } 
        
        if(dataVazia){
            if (alergia.getUltimaReacao().after(new Date())) {
                throw new RuntimeException("A data da ultima reação alérgica não pode ser posterior a data atual.");
            }

            if (!(alergia.getUsuario().getDataNascimento().before(alergia.getUltimaReacao()))) {
                throw new RuntimeException("A data da ultima reação alérgica não pode ser antes do nascimento do usuário.");
            }

        }
        
        alergiaDAO.alterarAlergia(alergia);
    } 
    
    public void excluirAlergia(Alergia alergia){
        alergiaDAO.excluirAlergia(alergia);
    }
    
    public List<Alergia> obterTodasAlergiasUsuarioId(long id){
        return alergiaDAO.obterTodasAlergiasUsuarioId(id);
    }
    
}
