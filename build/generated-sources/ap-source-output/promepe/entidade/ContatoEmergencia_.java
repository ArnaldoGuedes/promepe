package promepe.entidade;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import promepe.entidade.Usuario;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-06-12T17:20:43")
@StaticMetamodel(ContatoEmergencia.class)
public class ContatoEmergencia_ { 

    public static volatile SingularAttribute<ContatoEmergencia, Long> id;
    public static volatile SingularAttribute<ContatoEmergencia, String> parentesco;
    public static volatile SingularAttribute<ContatoEmergencia, String> telefone2;
    public static volatile SingularAttribute<ContatoEmergencia, Usuario> usuario;
    public static volatile SingularAttribute<ContatoEmergencia, String> email;
    public static volatile SingularAttribute<ContatoEmergencia, String> nome;
    public static volatile SingularAttribute<ContatoEmergencia, String> telefone1;
    public static volatile SingularAttribute<ContatoEmergencia, String> descricao;

}