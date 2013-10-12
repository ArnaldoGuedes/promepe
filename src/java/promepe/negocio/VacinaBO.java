package promepe.negocio;

import java.util.Date;
import java.util.List;
import promepe.entidade.Vacina;
import promepe.persistencia.VacinaDAO;
import promepe.utilitarios.Validacoes;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
public class VacinaBO {

    private Validacoes validacoes = new Validacoes();
    private VacinaDAO vacinaDAO = new VacinaDAO();

    /**
     * Retorna todas as vacinas de usuario identificado pelo ID.
     *
     * @param id do usuario que se pretende obter as vacinas.
     * @return Todas as vacinas de um determinado usuario
     */
    public List<Vacina> obterTodasVacinasUsuarioId(long id) {
        List<Vacina> vacinas = vacinaDAO.obterTodasVacinasUsuarioId(id);
        return vacinas;
    }

    /**
     * Persiste a vacina ao banco de dados.
     *
     * @param vacina
     */
    public void adicionarVacina(Vacina vacina) {
        //Testa se ja existe Vacina com o mesmo nome.
        Vacina vacinaObtida;
        try{
            vacinaObtida = vacinaDAO.obterVacinaNomeUsuarioId(vacina.getUsuario().getId(), vacina.getNome().toLowerCase());
        }catch(Exception e){
            vacinaObtida = new Vacina();
        }
        
        if (!vacinaObtida.getNome().equals("")) {
            throw new RuntimeException("A vacina '"+ vacina.getNome() +"' já existe.");
        }
        
        //Testar se a data esta vazia.
        boolean dataVazia = true;
        try {
            vacina.getDataVacina().compareTo(new Date()); //Gambiarra! =D
        }catch(Exception e){
            dataVazia = false;
        } 
        
        if(dataVazia){
            if (vacina.getDataVacina().after(new Date())) {
                throw new RuntimeException("A data da vacina não pode ser posterior a data atual.");
            }

            if (!(vacina.getUsuario().getDataNascimento().before(vacina.getDataVacina()))) {
                throw new RuntimeException("A data da vacina não pode ser antes do nascimento do usuário.");
            }

            if (validacoes.anosPassados(vacina.getDataVacina(), 150)) {
                throw new RuntimeException("A data da vacina informada possui 150 anos ou mais.");
            }
        }   
        //Caso esteja tudo correto
        vacinaDAO.adicionarVacina(vacina);
    }
    
    public void alterarVacina(Vacina vacina){
        //Testa se ja existe Vacina com o mesmo nome.
        Vacina vacinaObtida;
        try{
            vacinaObtida = vacinaDAO.obterVacinaNomeUsuarioId(vacina.getUsuario().getId(), vacina.getNome().toLowerCase());
        }catch(Exception e){
            vacinaObtida = new Vacina();
        }
        
        if (!vacinaObtida.getNome().equals("") && (vacinaObtida.getId() != vacinaObtida.getId()) ) {
            throw new RuntimeException("A vacina '"+ vacina.getNome() +"' já existe.");
        }       
        
        //Testar se a data esta vazia.
        boolean dataVazia = true;
        try {
            vacina.getDataVacina().compareTo(new Date()); //Gambiarra! =D
        }catch(Exception e){
            dataVazia = false;
        } 
        
        if(dataVazia){
            if (vacina.getDataVacina().after(new Date())) {
                throw new RuntimeException("A data da vacina, não pode ser apos a data atual.");
            }

            if (!(vacina.getUsuario().getDataNascimento().before(vacina.getDataVacina()))) {
                throw new RuntimeException("A data da vacina, não pode ser antes do nascimento do usuario.");
            }

            if (validacoes.anosPassados(vacina.getDataVacina(), 150)) {
                throw new RuntimeException("A data da vacina informada, possui 150 anos ou mais.");
            }
        }   
        //Caso esteja tudo correto
        vacinaDAO.alterarVacina(vacina);
    }
    
    public void excluirVacina(Vacina vacina){
        vacinaDAO.excluirVacina(vacina);
    }
}
