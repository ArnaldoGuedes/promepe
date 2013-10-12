package promepe.negocio;

import java.util.Date;
import java.util.List;
import promepe.entidade.MedicamentoControlado;
import promepe.persistencia.MedicamentoControladoDAO;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
public class MedicamentoControladoBO {
    private MedicamentoControladoDAO medicamentoControladoDAO = new MedicamentoControladoDAO();
    
    public void adicionarMedicamentoControlado(MedicamentoControlado medicamentoControlado){
        //Verifica se ja existe Medicamento Controlado com o mesmo nome
        MedicamentoControlado obterMedicamentoControlado;
        try{
            obterMedicamentoControlado = medicamentoControladoDAO.obterMedicamentoControladoNomeUsuarioId(medicamentoControlado.getUsuario().getId(), medicamentoControlado.getNome().toLowerCase());
        }catch(Exception e){
            obterMedicamentoControlado = new MedicamentoControlado();
        }
        
        if (!obterMedicamentoControlado.getNome().equals("")) {
            throw new RuntimeException("O medicamento controlado '"+ medicamentoControlado.getNome() +"' já existe.");
        }
        
        //Verifica se a data da ultima do inicio é valida.
                //Testar se a data esta vazia.
        boolean dataVazia = true;
        try {
            medicamentoControlado.getDataInicio().compareTo(new Date()); //Gambiarra! =D
        }catch(Exception e){
            dataVazia = false;
        } 
        
        if(dataVazia){
            if (medicamentoControlado.getDataInicio().after(new Date())) {
                throw new RuntimeException("A data de inicio da medicação não pode ser posterior a data atual.");
            }

            if (!(medicamentoControlado.getUsuario().getDataNascimento().before(medicamentoControlado.getDataInicio()))) {
                throw new RuntimeException("A data de inicio da medicação não pode ser anterior ao nascimento do usuário.");
            }

        }
        //Se as validaçõe estiverem corretas, passa o medicamento controlado para a camada de persistencia.
        medicamentoControladoDAO.adicionarMedicamentoControlado(medicamentoControlado);
    }
    
    public void alterarMedicamentoControlado(MedicamentoControlado medicamentoControlado){
        MedicamentoControlado obterMedicamentoControlado;
        try{
            obterMedicamentoControlado = medicamentoControladoDAO.obterMedicamentoControladoNomeUsuarioId(medicamentoControlado.getUsuario().getId(), medicamentoControlado.getNome().toLowerCase());
        }catch(Exception e){
            obterMedicamentoControlado = new MedicamentoControlado();
        }
        
        if ((!obterMedicamentoControlado.getNome().equals("")) && (obterMedicamentoControlado.getId() != medicamentoControlado.getId())) {
            throw new RuntimeException("O medicamento controlado '"+ medicamentoControlado.getNome() +"' já existe.");
        }
        
                //Verifica se a data do inicio da medicação é valida.
                //Testar se a data esta vazia.
        boolean dataVazia = true;
        try {
            medicamentoControlado.getDataInicio().compareTo(new Date()); //Gambiarra! =D
        }catch(Exception e){
            dataVazia = false;
        } 
        
        if(dataVazia){
            if (medicamentoControlado.getDataInicio().after(new Date())) {
                throw new RuntimeException("A data do inicio da medicação não pode ser posterior a data atual.");
            }

            if (!(medicamentoControlado.getUsuario().getDataNascimento().before(medicamentoControlado.getDataInicio()))) {
                throw new RuntimeException("A data do inicio da medicação não pode ser posterior do nascimento do usuário.");
            }

        }
        
        medicamentoControladoDAO.alterarMedicamentoControlado(medicamentoControlado);
    } 
    
    public void excluirMedicamentoControlado(MedicamentoControlado medicamentoControlado){
        medicamentoControladoDAO.excluirMedicamentoControlado(medicamentoControlado);
    }
    
    public List<MedicamentoControlado> obterTodosMedicamentoControladosUsuarioId(long id){
        return medicamentoControladoDAO.obterTodosMedicamentoControladosUsuarioId(id);
    }
    
}
