package promepe.negocio;

import java.io.Serializable;
import java.util.Date;
import promepe.entidade.Usuario;
import promepe.persistencia.UsuarioDAO;
import promepe.utilitarios.GeradorCodigoAlfaNum;
import promepe.utilitarios.Validacoes;

/**
 *
 * @author Arnaldo F Guedes Reis
 */
public class UsuarioBO implements Serializable {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Validacoes validacao = new Validacoes();

    /**
     * Aplica as Regras de negocio, se estiver tudo correto, passa para a camada
     * de persistencia.
     *
     * @param usuario
     */
    public void cadastrarUsuario(Usuario usuario) {
        // -------- VALIDAÇÃO E-MAIL ------------       
        if (!validacao.validarEmail(usuario.getEmail())) {
            throw new RuntimeException("E-mail invalido!");
        }

        //---- VERIFICA A EXISTENCIA DE OUTRO USUARIO COM O MESMO E-MAIL ----
        Usuario obterUsuario;
        try {
            obterUsuario = usuarioDAO.obterUsuario(usuario.getEmail());
        } catch (Exception e) {
            obterUsuario = new Usuario();
        }

        if (!obterUsuario.getEmail().isEmpty()) {
            throw new RuntimeException("E-mail ja cadastrado, por favor utilize outro e-mail.");
        }

        //---- VERIFICA SE A DATA DE NASCIMENTO NÂO ESTA NO FUTURO -----
        Date hj = new Date();
        if (hj.before(usuario.getDataNascimento())) {
            throw new RuntimeException("A data de nascimento não pode ser posterior a data atual.");
        }

        //---- VERIFICA SE A PESSOA ESTA INFORMANDO UMA DATA COM MAIS DE 150 ANOS -----
        if (validacao.anosPassados(usuario.getDataNascimento(), 150)) {
            throw new RuntimeException("A data de nascimento informada excede a idade maxima permitida.");
        }

        //----- GERA CODIGO DE EMERGENCIA ------
        usuario.setCodEmergencia(gerarCodigoEmergencia());

        //----- SE ESTIVER TUDO CORRETO PASSA PARA A CAMADA DE PERSISTENCIA ---
        usuarioDAO.alterarUsuario(usuario);
    }

    /*------------------------------------------------------------------------------------*/
    /**
     * Metodo reponsavel por alterar as informações do cliente, muito semelhante
     * ao metodo 'cadastrarUsuario' porém com algumas validações a mais.
     *
     * @param usuario
     */
    public void alterarInformacoesUsuario(Usuario usuario) {
        // -------- VALIDAÇÃO E-MAIL ------------       
        if (!validacao.validarEmail(usuario.getEmail())) {
            throw new RuntimeException("E-mail invalido!");
        }

        //---- VERIFICA SE A DATA DE NASCIMENTO NÂO ESTA NO FUTURO -----
        Date hj = new Date();
        if (hj.before(usuario.getDataNascimento())) {
            throw new RuntimeException("A data de nascimento não pode ser após a data atual.");
        }

        //---- VERIFICA SE A PESSOA ESTA INFORMANDO UMA DATA COM MAIS DE 150 ANOS -----
        if (validacao.anosPassados(usuario.getDataNascimento(), 150)) {
            throw new RuntimeException("A data de nascimento informada excede a idade maxima permitida.");
        }

        //---- VERIFICA SE O PESO E MAIOR QUE 0.5 E MENOR OU IGUAL A 200.0
        if(usuario.getPeso() != null)
        if (usuario.getPeso() != 0.0) {
            if ((usuario.getPeso() < 0.5) || (usuario.getPeso() > 200.0)) {
                throw new RuntimeException("O peso informado é invalido.");
            }
        }

        //---- VERIFICA SE A ALTURA E MAIOR QUE 0.1 E MENOR QUE 2.5 -----
        if(usuario.getPeso() != null)
        if (usuario.getAltura() != 0.0) {
            if ((usuario.getAltura() < 0.1) || (usuario.getAltura() > 2.7)) {
                throw new RuntimeException("A altura informada é invalida.");
            }
        }

        //----- SE ESTIVER TUDO CORRETO MANDA PARA A CAMADA DE PERSISTENCIA
        usuarioDAO.alterarUsuario(usuario);
    }

    /*------------------------------------------------------------------------------------*/
    /**
     * Obtem Usuario da camada de persistencia.
     *
     * @param email
     * @return objeto do tipo usuario.
     */
    public Usuario obterUsuario(String email) {
        Usuario usuario;
        try {
            usuario = usuarioDAO.obterUsuario(email);
            return usuario;

        } catch (Exception e) {
            return new Usuario();
        }
    }

    /*-----------------------------------------------------------------------------------*/
    /**
     * Obtem um usuario, pelo seu código de emergencia.
     *
     * @param codEmergencia
     * @return Usuario;
     */
    public Usuario obterUsuarioCodEmergencia(String codEmergencia) {
        Usuario usuario;

        try {
            usuario = usuarioDAO.obterUsuarioCodEmergencia(codEmergencia);
        } catch (Exception e) {
            usuario = new Usuario();
        }

        if (usuario.getCodEmergencia().equals("")) {
            throw new RuntimeException("Código de emergência não cadastrado.");
        }

        if (!usuario.isContaAtiva()) {
            throw new RuntimeException("Usuario desativado.");
        }
        //Se estiver tudo correto, retorna o usuario.
        return usuario;
    }

    public void alterarSenha(String senhaAntiga, String novaSenha, Usuario usuario) {
        //Verifica se a senha antiga, realmente e verdadeira.
        if (!usuario.getSenha().equals(senhaAntiga)) {
            throw new RuntimeException("A senha antiga não confere.");
        }

        if (usuario.getSenha().equals(novaSenha)) {
            throw new RuntimeException("A nova senha não deve ser igual a antiga.");
        }

        //Caso esteja certo
        usuario.setSenha(novaSenha);
        usuarioDAO.alterarUsuario(usuario);
    }

    public void alterarEmail(String email, Usuario usuario) {
        // -------- VALIDAÇÃO E-MAIL ------------       
        if (!validacao.validarEmail(email)) {
            throw new RuntimeException("E-mail invalido!");
        }

        //---- VERIFICA A EXISTENCIA DE OUTRO USUARIO COM O MESMO E-MAIL ----
        Usuario obterUsuario;
        try {
            obterUsuario = usuarioDAO.obterUsuario(email);
        } catch (Exception e) {
            obterUsuario = new Usuario();
        }

        if (!obterUsuario.getEmail().isEmpty()) {
            throw new RuntimeException("E-mail ja cadastrado, por favor utilize outro e-mail.");
        }

        //Caso esteja tudo correto
        usuario.setEmail(email);
        usuarioDAO.alterarUsuario(usuario);
    }

    public String gerarCodigoEmergencia() {
        boolean repeticao = true;
        String codTemp = "";
        GeradorCodigoAlfaNum gerador = new GeradorCodigoAlfaNum();
        while (repeticao) {
            codTemp = gerador.gerarCodigoMaiusculas(12);

            try {
                usuarioDAO.obterUsuarioCodEmergencia(codTemp);
            } catch (Exception e) {
                repeticao = false;
            }
        }
        return codTemp;
    }
}
