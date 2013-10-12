package promepe.negocio;

import java.util.List;
import promepe.entidade.ContatoEmergencia;
import promepe.persistencia.ContatoEmergenciaDAO;
import promepe.utilitarios.Validacoes;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
public class ContatoEmergenciaBO {
    private ContatoEmergenciaDAO contatoEmergenciaDAO = new ContatoEmergenciaDAO();
    private Validacoes validacoes = new Validacoes();
            
    public void adicionarContatoEmergencia(ContatoEmergencia contatoEmergencia){
        //Verfica se os contattos de emergencia não ultrapassam o máximo permitido que e de 3.
        List<ContatoEmergencia> contatos;
        int qtd;
        try{
            contatos = contatoEmergenciaDAO.obterTodasContatoEmergenciasUsuarioId(contatoEmergencia.getUsuario().getId());
            qtd = contatos.size();
        }catch(Exception e){
            qtd = 0;
        }
        
        if(qtd >= 5){
            throw new RuntimeException("São permitidos apenas 5 contatos de emergência!");
        }
        
        //Verifica se e-mail e valido.
        if(!contatoEmergencia.getEmail().equals("")){
            if(!validacoes.validarEmail(contatoEmergencia.getEmail())){
                throw new RuntimeException("E-mail invalido!");
            }
        }
        
        //Verifica se telefone1 e igual a telefone2
        if(contatoEmergencia.getTelefone1().equals(contatoEmergencia.getTelefone2())){
            throw new RuntimeException("Não é permitido dois numeros de telefone iguais para o mesmo contato.");
        }
        
        //Se estiver tudo correto passa o objeto para a camada de persistencia.
        contatoEmergenciaDAO.adicionarContatoEmergencia(contatoEmergencia);
    }
    
    public void alterarContatoEmergencia(ContatoEmergencia contatoEmergencia){        
        contatoEmergenciaDAO.alterarContatoEmergencia(contatoEmergencia);
    } 
    
    public void excluirContatoEmergencia(ContatoEmergencia contatoEmergencia){
        contatoEmergenciaDAO.excluirContatoEmergencia(contatoEmergencia);
    }
    
    public List<ContatoEmergencia> obterTodasContatoEmergenciasUsuarioId(long id){
        return contatoEmergenciaDAO.obterTodasContatoEmergenciasUsuarioId(id);
    }    
   
}
