package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import beandto.BeanDtoGraficoSalarioUser;
import connection.SingleConnectionBanco;
import model.ModelLogin;
import model.ModelTelefone;

public class DAOUsuarioRepository {
	
	private Connection connection;
	
	
	
	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	//não esqueça de lancar o throwns 
	public ModelLogin gravarUsuario(ModelLogin objeto, Long userLogado) throws Exception{
	
		
	try {
		
		if (objeto.isNovo()) {
			
			String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, rua, bairro, cidade, uf, numero, datanascimento, rendamensal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
			preparasql.setString(8, objeto.getCep());
			preparasql.setString(9, objeto.getRua());
			preparasql.setString(10, objeto.getBairro());
			preparasql.setString(11, objeto.getCidade());
			preparasql.setString(12, objeto.getUf());
			preparasql.setString(13, objeto.getNumero());
			preparasql.setDate(14, objeto.getDataNascimento());
			preparasql.setDouble(15, objeto.getRendaMensal());
			
			preparasql.execute();
			connection.commit();
			
			if (objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
			sql= "update model_login set fotouser =?, extensaofotouser=? where login =?";
			preparasql = connection.prepareStatement(sql);
			preparasql.setString(1, objeto.getFotouser());
			preparasql.setString(2, objeto.getExtensaofotouser());
			preparasql.setString(3, objeto.getLogin());
			preparasql.execute();
			connection.commit();
			}
		}else {
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, rua=?, bairro=?, cidade=?, uf=?, numero=?, datanascimento=?, rendamensal=?  WHERE id = "+objeto.getId()+";";
			
			PreparedStatement preparasql = connection.prepareStatement(sql);
			preparasql.setString(1, objeto.getLogin());
			preparasql.setString(2, objeto.getSenha());
			preparasql.setString(3, objeto.getNome());
			preparasql.setString(4, objeto.getEmail());
			preparasql.setString(5, objeto.getPerfil());
			preparasql.setString(6, objeto.getSexo());
			preparasql.setString(7, objeto.getCep());
			preparasql.setString(8, objeto.getRua());
			preparasql.setString(9, objeto.getBairro());
			preparasql.setString(10, objeto.getCidade());
			preparasql.setString(11, objeto.getUf());
			preparasql.setString(12, objeto.getNumero());
			preparasql.setDate(13, objeto.getDataNascimento());
			preparasql.setDouble(14, objeto.getRendaMensal());
			
			
			preparasql.executeUpdate();
			connection.commit();
			
			if (objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
				sql= "update model_login set fotouser = ?, extensaofotouser=? where login = ?";
				preparasql = connection.prepareStatement(sql);
				preparasql.setString(1, objeto.getFotouser());
				preparasql.setString(2, objeto.getExtensaofotouser());
				preparasql.setLong(3, objeto.getId());
				preparasql.execute();
				connection.commit();
				
			}
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
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setRua(resultado.getString("rua"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setCidade(resultado.getString("cidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendamensal"));
			
			
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
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setRua(resultado.getString("rua"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setCidade(resultado.getString("cidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendamensal"));
			
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
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setRua(resultado.getString("rua"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setCidade(resultado.getString("cidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendamensal"));
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
	
	public ModelLogin consultarUsuarioID(Long id ) throws Exception {
		ModelLogin modelLogin = new ModelLogin();
		String sql = "select * from model_login where id = ? and useradmin is false";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, id);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setRua(resultado.getString("rua"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setCidade(resultado.getString("cidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendamensal"));
		}
		return modelLogin;
	
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
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setRua(resultado.getString("rua"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setCidade(resultado.getString("cidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendamensal"));
			
		}
		return modelLogin;
	
	}
	
	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws Exception {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		String sql = "SELECT * FROM model_login WHERE useradmin IS FALSE and usuario_id =" + userLogado;
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
	
	
	public List<ModelLogin> consultaUsuarioListRel(Long userLogado) throws Exception {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		String sql = "SELECT * FROM model_login WHERE useradmin IS FALSE and usuario_id =" + userLogado;
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
		modelLogin.setTelefones(this.listFone(modelLogin.getId()));
		modelLogin.setDataNascimento(resultado.getDate("datanascimento"));	
		
		retorno.add(modelLogin);
		
	}
		
		return retorno;
	}
	
	
	public List<ModelLogin> consultaUsuarioListRel(Long userLogado, String dataInicial, String dataFinal) throws Exception {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		String sql = "SELECT * FROM model_login WHERE useradmin IS FALSE and usuario_id =" + userLogado + " and datanascimento >= ? and datanascimento <= ?";
		PreparedStatement statement = connection.prepareStatement(sql); 
		statement.setDate(1, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
		statement.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));
	    ResultSet resultado = statement.executeQuery();
	   
	   while (resultado.next()) {
		ModelLogin modelLogin = new ModelLogin();
		modelLogin.setEmail(resultado.getString("email"));
		modelLogin.setId(resultado.getLong("id"));
		modelLogin.setNome(resultado.getString("nome"));
		modelLogin.setLogin(resultado.getString("login"));
		//modelLogin.setSenha(resultado.getString("senha"));
		modelLogin.setPerfil(resultado.getString("perfil"));
		modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
	    //modelLogin.setSexo(resultado.getString("sexo"));
		modelLogin.setTelefones(this.listFone(modelLogin.getId()));
		
		
		retorno.add(modelLogin);
		
	}
		
		return retorno;
	}
	
	public List<ModelLogin> consultaUsuarioListPaginada(Long userLogado, Integer offset ) throws Exception {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		String sql = "SELECT * FROM model_login WHERE useradmin IS FALSE and usuario_id =" + userLogado + " order by nome offset " + offset + " limit 5";
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
	
public List<ModelLogin> consultaUsuarioListOffSet(String nome, Long userLogado, int offset) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login  where upper(nome) like upper(?) and useradmin is false and usuario_id = ? offset "+offset+" limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { /*percorrer as linhas de resultado do SQL*/
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			//modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			retorno.add(modelLogin);
		}
		
		
		return retorno;
	}

		
		

	
	public int totalPagina(Long userLogado) throws Exception {
		String sql = "select count(1) as total from model_login where usuario_id =" + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		resultado.next();
		Double cadastros = resultado.getDouble("total");
		Double porpagina = 5.0;
		Double pagina = cadastros / porpagina;
		Double resto = pagina % 2;
		
		if (resto > 0) {
			pagina ++;
		}
		return pagina.intValue();
	}

public int consultaUsuarioListTotalPaginaPaginacao(String nome, Long userLogado) throws Exception {
		
		
		String sql = "select count(1) as total from model_login  where upper(nome) like upper(?) and useradmin is false and usuario_id = ? ";
	
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		resultado.next();
		
		Double cadastros = resultado.getDouble("total");
		
		Double porpagina = 5.0;
		
		Double pagina = cadastros / porpagina;
		
		Double resto = pagina % 2;
		
		if (resto > 0) {
			pagina ++;
		}
		
		return pagina.intValue();
		
	}

	public List<ModelTelefone> listFone(Long idUserPai) throws Exception {
		List<ModelTelefone> retorno = new ArrayList<ModelTelefone>();
		String sql = "select * from telefone where usuario_pai_id =?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setLong(1, idUserPai);
		ResultSet rs = preparedStatement.executeQuery();

		while (rs.next()) {

			ModelTelefone modelTelefone = new ModelTelefone();
			modelTelefone.setId(rs.getLong("id"));
			modelTelefone.setNumero(rs.getString("numero"));
			modelTelefone.setUsuario_cad_id(this.consultarUsuarioID(rs.getLong("usuario_cad_id")));
			modelTelefone.setUsuario_pai_id(this.consultarUsuarioID(rs.getLong("usuario_pai_id")));

			retorno.add(modelTelefone);
		}

		return retorno;
	}
	
	public BeanDtoGraficoSalarioUser montarGraficoMediaSalarial(Long userLogado ) throws Exception {
		String sql = "select avg (rendamensal) AS media_salarial, perfil FROM model_login where usuario_id =? GROUP BY perfil";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, userLogado);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		List<String> perfils = new ArrayList<String>();
		List<Double> salarios = new ArrayList<Double>();
		
		BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = new BeanDtoGraficoSalarioUser();
		
		while (resultSet.next()) {
			Double media_salario = resultSet.getDouble("media_salarial");
			String perfil = resultSet.getString("perfil");
			
			perfils.add(perfil);
			salarios.add(media_salario);
			
		}
		
		beanDtoGraficoSalarioUser.setPerfils(perfils);
		beanDtoGraficoSalarioUser.setSalarios(salarios);
		
		return beanDtoGraficoSalarioUser;
	}

	public BeanDtoGraficoSalarioUser montarGraficoMediaSalarial(Long userLogado, String dataInicial, String dataFinal)throws Exception {
		String sql = "select avg (rendamensal) AS media_salarial, perfil FROM model_login where usuario_id =? and datanascimento >= ? and datanascimento <= ? GROUP BY perfil";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, userLogado);
		preparedStatement.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
		preparedStatement.setDate(3, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));
		ResultSet resultSet = preparedStatement.executeQuery();
		
		List<String> perfils = new ArrayList<String>();
		List<Double> salarios = new ArrayList<Double>();
		
		BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = new BeanDtoGraficoSalarioUser();
		
		while (resultSet.next()) {
			Double media_salario = resultSet.getDouble("media_salarial");
			String perfil = resultSet.getString("perfil");
			
			perfils.add(perfil);
			salarios.add(media_salario);
			
		}
		
		beanDtoGraficoSalarioUser.setPerfils(perfils);
		beanDtoGraficoSalarioUser.setSalarios(salarios);
		
		return beanDtoGraficoSalarioUser;
	}

}
