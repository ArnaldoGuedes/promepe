package promepe.entidade;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import promepe.entidade.Alergia;
import promepe.entidade.ContatoEmergencia;
import promepe.entidade.DoencaCronica;
import promepe.entidade.MedicamentoControlado;
import promepe.entidade.Vacina;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-06-12T17:20:43")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> informacoesImportantes;
    public static volatile ListAttribute<Usuario, Vacina> vacinas;
    public static volatile SingularAttribute<Usuario, Boolean> contaAtiva;
    public static volatile SingularAttribute<Usuario, Character> sexo;
    public static volatile SingularAttribute<Usuario, String> codEmergencia;
    public static volatile SingularAttribute<Usuario, String> senha;
    public static volatile SingularAttribute<Usuario, Double> altura;
    public static volatile SingularAttribute<Usuario, String> telefoneCasa;
    public static volatile ListAttribute<Usuario, ContatoEmergencia> contatoEmergencias;
    public static volatile SingularAttribute<Usuario, Long> id;
    public static volatile SingularAttribute<Usuario, Double> peso;
    public static volatile SingularAttribute<Usuario, String> nomeMae;
    public static volatile ListAttribute<Usuario, MedicamentoControlado> medicamentoControlados;
    public static volatile SingularAttribute<Usuario, String> email;
    public static volatile SingularAttribute<Usuario, String> nomeCompleto;
    public static volatile ListAttribute<Usuario, DoencaCronica> doencaCronicas;
    public static volatile SingularAttribute<Usuario, Date> dataNascimento;
    public static volatile SingularAttribute<Usuario, String> nome;
    public static volatile ListAttribute<Usuario, Alergia> alergias;
    public static volatile SingularAttribute<Usuario, String> tipoSanguineo;

}