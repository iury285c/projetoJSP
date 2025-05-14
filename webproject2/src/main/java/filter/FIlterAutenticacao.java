package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.security.PrivateKey;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.ConnectionPoolDataSource;

import org.apache.catalina.valves.rewrite.Substitution.StaticElement;

import connection.SingleConnectionBanco;

// Classe de filtro que será chamada antes de Servlets ou páginas JSP
// OBS: o nome da classe está com "FIlterAutenticacao" 
//(com "I" maiúsculo), o ideal seria "FilterAutenticacao"
@WebFilter(urlPatterns = {"/principal/ServletLogin", "/servletLogin"})
public class FIlterAutenticacao implements Filter {
       
	
	private static Connection connection;
	 // Construtor padrão - chamado quando o filtro é instanciado
    public FIlterAutenticacao() {
    	// Você pode fazer inicializações básicas aqui, se quiser
    	
    	
     
    }

    // Método chamado **uma única vez** quando o filtro está sendo destruído 
    //(por exemplo, quando o servidor é desligado)
    // Aqui você pode liberar recursos, fechar conexões, etc.
	public void destroy() {
	// Limpeza de recursos, se necessário
		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	// Método chamado toda vez que uma requisição passar por este filtro
	// Esse é o método principal onde você coloca a lógica para interceptar,
	//validar, redirecionar, etc.
	// request: representa a requisição que está chegando (ex: do navegador para um servlet ou JSP)
	// response: representa a resposta que será enviada de volta ao cliente (navegador)
	// chain: representa a cadeia de filtros. Quando você chama chain.doFilter(), está dizendo "pode continuar para o próximo"
	// Aqui é onde sua lógica de autenticação ou bloqueio entraria.
    // Atualmente, o filtro apenas permite que a requisição continue normalmente.
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	
	try {	
		
	HttpServletRequest req = (HttpServletRequest)request;
	HttpSession session = req.getSession();
	
	String usuarioLogado = (String) session.getAttribute("usuario");
	
	String urlParaAutenticar = req.getServletPath();
	
	if (usuarioLogado == null || (usuarioLogado != null && 
			!urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin"))) {
		
		RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
		request.setAttribute("msg", "Por favor realize o login!");
		redireciona.forward(request, response);
		return;
	}else {
		chain.doFilter(request, response);
	}
	
	connection.commit();
	
	}catch (Exception e) {
		e.printStackTrace();
		
		RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
		request.setAttribute("msg", e.getMessage());
		redirecionar.forward(request, response);
		
		try {
			connection.rollback();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
		
	}

	 // Método chamado **uma única vez** quando o filtro é criado pelo servidor (por exemplo, na inicialização do Tomcat)
    // Pode ser usado para carregar configurações iniciais
    // fConfig: contém dados de configuração do filtro (definidos em web.xml, se aplicável)
	public void init(FilterConfig fConfig) throws ServletException {
        // Exemplo: pegar um parâmetro de inicialização
        // String parametro = fConfig.getInitParameter("nomeParametro");
		
		connection = SingleConnectionBanco.getConnection();
	}

}
