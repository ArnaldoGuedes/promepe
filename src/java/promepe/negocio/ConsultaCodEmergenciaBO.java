package promepe.negocio;

import java.util.Date;
import promepe.entidade.ConsultaCodEmergencia;
import promepe.persistencia.ConsultaCodEmergenciaDAO;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
public class ConsultaCodEmergenciaBO {
    private ConsultaCodEmergenciaDAO codEmergenciaDAO = new ConsultaCodEmergenciaDAO();
    
    public void adicionarConsultaCodEmergencia(ConsultaCodEmergencia consultaCodEmergencia){
        ConsultaCodEmergencia consultaObtida;
        
        //Verifica se ja houve consulta ao mesmo codigo.
        try{
            consultaObtida = codEmergenciaDAO.obterConsultaCodEmergencia(consultaCodEmergencia.getCodUsuario(), consultaCodEmergencia.getCodEmergencia());
        }catch(Exception e){
            consultaObtida = new ConsultaCodEmergencia();
        }
        
        //Se ja existir consulta
        if (!consultaObtida.getCodEmergencia().equals("")){
            consultaObtida.setDataConsulta(new Date()); //Somente atualiza a data da consulta
            codEmergenciaDAO.atualizaConsultaCodEmergencia(consultaObtida);
        }else{//Se não existir
            codEmergenciaDAO.adicionarConsultaCodEmergencia(consultaCodEmergencia);
        }
    }
    
    public ConsultaCodEmergencia buscarConCodEme(long idUsuario, String codEmergencia){
        return codEmergenciaDAO.obterConsultaCodEmergencia(idUsuario, codEmergencia);
    }
}
