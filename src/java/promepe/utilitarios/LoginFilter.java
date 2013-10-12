
package promepe.utilitarios;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import promepe.entidade.Usuario;


/**
 *
 * @author Arnaldo F Guedes Reis
 */
public class LoginFilter implements Serializable, Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Usuario usuarioLogado = (Usuario) req.getSession().getAttribute("usuarioLogado");
        //Trata possivel usuario Null;
        try{
           if (usuarioLogado.getEmail().isEmpty());
        }catch(Exception e){
            usuarioLogado = new Usuario();
        }
        
        if(usuarioLogado.getEmail().isEmpty()){
            resp.sendRedirect("../inicio.jsf");
        }else{
            chain.doFilter(request, response);
        }    
    }

    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    @Override
    public void destroy() {
    }
    
}
