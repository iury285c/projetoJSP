package servlets;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Formatter;
import java.util.List;


import org.apache.tomcat.jakartaee.commons.compress.utils.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelLogin;


@MultipartConfig
@WebServlet(urlPatterns = {"/ServletUsuarioController"})
public class ServletUsuarioController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
     
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

    public ServletUsuarioController() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String acao = request.getParameter("acao");
			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
				String iduser = request.getParameter("id");
				daoUsuarioRepository.deletarUSer(iduser);
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("msg", "Excluido com sucesso!" );
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
				String iduser = request.getParameter("id");
				daoUsuarioRepository.deletarUSer(iduser);
				response.getWriter().write("Excluido com sucesso!");
				
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
				
				String nomeBusca = request.getParameter("nomeBusca");
				List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca, super.getUserLogado(request));
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(dadosJsonUser);
				response.getWriter().write(json);
				
			}
			
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {
				 
				 String nomeBusca = request.getParameter("nomeBusca");
				 String pagina = request.getParameter("pagina");
				 
				 List<ModelLogin> dadosJsonUser =  daoUsuarioRepository.consultaUsuarioListOffSet(nomeBusca, super.getUserLogado(request), Integer.parseInt(pagina));
				 
				 ObjectMapper mapper = new ObjectMapper();
				 
				 String json = mapper.writeValueAsString(dadosJsonUser);
				 
				 response.addHeader("totalPagina", ""+ daoUsuarioRepository.consultaUsuarioListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
				 response.getWriter().write(json);
				 
			 	}


			
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
				
				String id = request.getParameter("id");
			    String nomeBusca = request.getParameter("nomeBusca");
			    String pagina = request.getParameter("pagina");
			    
			    ModelLogin loginEdicao = daoUsuarioRepository.consultarUsuarioID(id, super.getUserLogado(request));
			    
			    List<ModelLogin> modelLogins;
			    int totalPaginas = 0;
			    
			    if (nomeBusca != null && !nomeBusca.isEmpty() && pagina != null && !pagina.isEmpty()) {
			        modelLogins = daoUsuarioRepository.consultaUsuarioListOffSet(nomeBusca, super.getUserLogado(request), Integer.parseInt(pagina));
			        totalPaginas = daoUsuarioRepository.consultaUsuarioListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request));
			    } else {
			        modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
			        totalPaginas = daoUsuarioRepository.totalPagina(this.getUserLogado(request));
			    }
			    
			    request.setAttribute("modelLogin", loginEdicao);
			    request.setAttribute("modelLogins", modelLogins);
			    request.setAttribute("totalPagina", totalPaginas);
			    request.setAttribute("paginaAtual", pagina);
			    request.setAttribute("nomeBusca", nomeBusca);
			    request.setAttribute("msg", "Usuario em edição");
			    
			    request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listaUser")) {
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				
				request.setAttribute("msg", "Usuarios Carregados");
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
			    int offset = Integer.parseInt(request.getParameter("pagina"));
			    List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioListPaginada(this.getUserLogado(request), offset);
			    
			    request.setAttribute("modelLogins", modelLogins);
			    request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
			    request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioUser")) {
				String dataInicial =request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");
				
				if (dataInicial ==  null || dataInicial.isEmpty() 
						&& dataFinal == null || dataFinal.isEmpty()) {
					
					request.setAttribute("listaUser", daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request)));
				}else {
					request.setAttribute("listaUser", daoUsuarioRepository
							.consultaUsuarioListRel(super.getUserLogado(request), dataInicial, dataFinal));
				}
				
				request.setAttribute("dataInicial", dataInicial);
				request.setAttribute("datafinal", dataFinal);
				request.getRequestDispatcher("principal/reluser.jsp").forward(request, response);
			}
			
			
			else {
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		try {
			
		String msg = "Operação Realizada com sucesso!";	
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String perfil = request.getParameter("perfil");
		String sexo = request.getParameter("sexo");
		String cep = request.getParameter("cep");
		String rua = request.getParameter("rua");
		String bairro = request.getParameter("bairro");
		String cidade = request.getParameter("cidade");
		String uf = request.getParameter("uf");
		String numero = request.getParameter("numero");
		String dataNascimento = request.getParameter("dataNascimento");
        String rendaMensal = request.getParameter("rendamensal");
		
       

		
		ModelLogin modelLogin = new ModelLogin();
		modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
		modelLogin.setNome(nome);
		modelLogin.setEmail(email);
		modelLogin.setLogin(login);
		modelLogin.setSenha(senha);
		modelLogin.setPerfil(perfil);
		modelLogin.setSexo(sexo);
		modelLogin.setCep(cep);
		modelLogin.setRua(rua);
		modelLogin.setBairro(bairro);
		modelLogin.setCidade(cidade);
		modelLogin.setUf(uf);
		modelLogin.setNumero(numero);
		modelLogin.setDataNascimento(Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento))));
		
		
		if (rendaMensal != null && !rendaMensal.isBlank()) {
		    rendaMensal = rendaMensal.replace("R$ ", "").replace(".", "").replace(",", ".");
		} else {
		    rendaMensal = "0.0";
		}
		
		modelLogin.setRendaMensal(Double.valueOf(rendaMensal));
		
		 Part part = request.getPart("fileFoto");

	        if (part != null && part.getSize() > 0) {
	            byte[] foto = IOUtils.toByteArray(part.getInputStream());
	            String imagemBase64 = Base64.getEncoder().encodeToString(foto);

	            response.setContentType("text/html");
	            response.getWriter().write("<img src='data:image/jpeg;base64," + imagemBase64 + "' />");
	           modelLogin.setFotouser(imagemBase64);
	           modelLogin.setExtensaofotouser(part.getContentType().split("\\/")[1]);
	        } else {
	            response.getWriter().write("Nenhum arquivo enviado.");
	        }
	        
		
		if (daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
			msg = "Este login já está sendo usado por outro usuário.";
		}else {
			if (modelLogin.isNovo()) {
				msg = "Usuario salvo com sucesso!";
			}else {
				msg = "Atualizado com sucesso!";
			}
			modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin, super.getUserLogado(request));		
		}
		
		List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
		request.setAttribute("modelLogins", modelLogins);
		//redirecionar a pagina ("caso de esquecimento, Lembre se)//
		request.setAttribute("msg", msg);
		request.setAttribute("modelLogin", modelLogin);
		request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
		request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

}
