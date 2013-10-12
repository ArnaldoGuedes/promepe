package promepe.utilitarios;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
public class Validacoes {
    /**
     * Verifica se o e-mail é valido.
     * 
     * @param email do tipo String
     * @return FALSE se o email for invalido ou TRUE se o email for valido.
     */
    public boolean validarEmail(String email){
        int qtdArroba = 0; int indiceArroba = 0; int ponto = 0; //Variaveis auxiliares para a validação do e-mail.

        //Verifica se o e-mail possui arroba, caso contrario e considerado e-mail invalido.
        for (int i = 1 ; i < email.length() ; i++){
            if(email.charAt(i) == '@'){
                qtdArroba++;
                //adiciona o indice do arroba valido, para comparar com o ponto futuramente.
                if(qtdArroba == 1){
                    indiceArroba = i;
                }                
            }
        }
        if (qtdArroba != 1){
            return false;
        }
        
        //Verifica se o e-mail possui "ponto" apos o arroba.
        for (int i = indiceArroba ; i < email.length() ; i++){
            if (email.charAt(i) == '.'){
                ponto++;
            }
        }
        if (ponto < 1 || ponto > 3){
            return false;
        }
        //se tudo estiver correto retorna True;
        return true;
    }
    
    /**
     * Verifica se de a data atual para a data "anoTeste" se passaram "maximoAno".
     * @param dataTeste data qual sera testado.
     * @param maximoAnos quantos anos se passaram
     * @return se da data atual até a "dataTeste" estiver se passado "maximoAnos" retorna TRUE
     *  caso contrario retorna FALSE.
     */
    public boolean anosPassados(Date dataTeste, int maximoAnos){
        GregorianCalendar diaXanos = new GregorianCalendar();
        
        GregorianCalendar nascimento = new GregorianCalendar();
        nascimento.setTime(dataTeste);
        
        diaXanos.add(GregorianCalendar.YEAR, -maximoAnos);
        
        if (nascimento.get(GregorianCalendar.YEAR) <= diaXanos.get(GregorianCalendar.YEAR)){
            return true;
        }
        
    return false;
    }
    
    
}
