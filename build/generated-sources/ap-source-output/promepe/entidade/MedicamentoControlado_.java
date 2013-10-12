package promepe.entidade;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import promepe.entidade.Usuario;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-06-12T17:20:43")
@StaticMetamodel(MedicamentoControlado.class)
public class MedicamentoControlado_ { 

    public static volatile SingularAttribute<MedicamentoControlado, Long> id;
    public static volatile SingularAttribute<MedicamentoControlado, String> laboratorio;
    public static volatile SingularAttribute<MedicamentoControlado, Usuario> usuario;
    public static volatile SingularAttribute<MedicamentoControlado, Date> dataInicio;
    public static volatile SingularAttribute<MedicamentoControlado, Date> dataAdd;
    public static volatile SingularAttribute<MedicamentoControlado, String> nome;
    public static volatile SingularAttribute<MedicamentoControlado, String> descricao;

}