package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.internal.compiler.ast.ReturnStatement;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {
	
	private Connection connection;
	
	
	
	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	//não esqueça de lancar o throwns 
	public ModelLogin gravarUsuario(ModelLogin objeto, Long userLogado) throws Exception{
	
		
	try {
		
		if (objeto.isNovo()) {
			
			String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id, perfil, sexo) VALUES (?, ?,?, ?, ?, ?, ?)";
			PreparedStatement preparasql = connection.prepareStatement(sql);
			
			// A ordem dos parâmetros no PreparedStatement deve corresponder exatamente à ordem das colunas esperadas na instrução SQL,
			// garantindo que os valores sejam inseridos corretamente no banco de dados.
			preparasql.setString(1, objeto.getLogin());
			preparasql.setString(2, objeto.getSenha());
			preparasql.setString(3, objeto.getNome());
			preparasql.setString(4, objeto.getEmail());
			preparasql.setLong(5, userLogado);
			preparasql.setString(6, objeto.getPerfil());
			preparasql.setString(7, objeto.getSexo());
			preparasql.execute();
			connection.commit();
		}else {
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=? WHERE id = "+objeto.getId()+";";
			PreparedStatement preparasql = connection.prepareStatement(sql);
			preparasql.setString(1, objeto.getLogin());
			preparasql.setString(2, objeto.getSenha());
			preparasql.setString(3, objeto.getNome());
			preparasql.setString(4, objeto.getEmail());
			preparasql.setString(5, objeto.getPerfil());
			preparasql.setString(6, objeto.getSexo());
			preparasql.executeUpdate();
			connection.commit();
		}	

	} catch (Exception e) {
		connection.rollback();
		e.printStackTrace();
	}	
	
	return this.consultarUsuario(objeto.getLogin());
	}
	
	
	public List<ModelLogin> consultaUsuarioList(String nome, Long userlogado) throws Exception {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		String sql = "select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
	    statement.setString(1, "%" + nome + "%");
	    statement.setLong(2, userlogado);
	    
	   ResultSet resultado = statement.executeQuery();
	   
	   while (resultado.next()) {
		ModelLogin modelLogin = new ModelLogin();
		modelLogin.setEmail(resultado.getString("email"));
		modelLogin.setId(resultado.getLong("id"));
		modelLogin.setNome(resultado.getString("nome"));
		modelLogin.setLogin(resultado.getString("login"));
		//modelLogin.setSenha(resultado.getString("senha"));
		modelLogin.setPerfil(resultado.getString("perfil"));
		modelLogin.setSexo(resultado.getString("sexo"));
		
		retorno.add(modelLogin);
		
	}
		
		return retorno;
	}
	
	public ModelLogin consultarUsuarioLogado(String login) throws Exception {
		ModelLogin modelLogin = new ModelLogin();
		String sql = "select * from model_login where upper(login) = upper('"+login+"')";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
		}
		return modelLogin;
	}
	
	public ModelLogin consultarUsuario(String login) throws Exception {
		ModelLogin modelLogin = new ModelLogin();
		String sql = "select * from model_login where upper(login) = upper('"+login+"') and useradmin is false";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
		}
		return modelLogin;
	}
	
	public ModelLogin consultarUsuario(String login, Long userLogado) throws Exception {
		ModelLogin modelLogin = new ModelLogin();
		String sql = "select * from model_login where upper(login) = upper('"+login+"') and useradmin is false and usuario_id =" + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
		}
		return modelLogin;
	}
	
	public boolean validarLogin(String login) throws Exception{
		String sql = "SELECT COUNT(*) as existe from model_login where upper(login) = upper(?) ";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, login);
		ResultSet resultado = statement.executeQuery();
		resultado.next();
		return resultado.getBoolean("existe");
		

	}
	
	public void deletarUSer(String idUser) throws Exception {
		String sql = "DELETE FROM model_login WHERE id = ? and useradmin is false;";
		PreparedStatement prepareSQl = connection.prepareStatement(sql);
		prepareSQl.setLong(1, Long.parseLong(idUser));
		prepareSQl.executeUpdate();
		connection.commit();
	}
	
	public ModelLogin consultarUsuarioID(String id, Long userLogado) throws Exception {
		ModelLogin modelLogin = new ModelLogin();
		String sql = "select * from model_login where id = ? and useradmin is false and usuario_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		statement.setLong(2, userLogado);
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
		}
		return modelLogin;
	
	}
	
	public List<ModelLogin> consultaUsuarioList(Long userLogado ) throws Exception {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		String sql = "SELECT * FROM model_login WHERE useradmin IS FALSE and usuario_id =" +userLogado;
		PreparedStatement statement = connection.prepareStatement(sql); 
	    ResultSet resultado = statement.executeQuery();
	   
	   while (resultado.next()) {
		ModelLogin modelLogin = new ModelLogin();
		modelLogin.setEmail(resultado.getString("email"));
		modelLogin.setId(resultado.getLong("id"));
		modelLogin.setNome(resultado.getString("nome"));
		modelLogin.setLogin(resultado.getString("login"));
		//modelLogin.setSenha(resultado.getString("senha"));
		modelLogin.setPerfil(resultado.getString("perfil"));
		
		retorno.add(modelLogin);
		
	}
		
		return retorno;
	}
}
