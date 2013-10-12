package promepe.entidade;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import promepe.entidade.Usuario;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-06-12T17:20:43")
@StaticMetamodel(Vacina.class)
public class Vacina_ { 

    public static volatile SingularAttribute<Vacina, Long> id;
    public static volatile SingularAttribute<Vacina, Date> dataVacina;
    public static volatile SingularAttribute<Vacina, Usuario> usuario;
    public static volatile SingularAttribute<Vacina, Date> dataAdd;
    public static volatile SingularAttribute<Vacina, String> nome;
    public static volatile SingularAttribute<Vacina, String> descricao;

}