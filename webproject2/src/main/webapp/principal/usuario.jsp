<%@page import="model.ModelLogin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>

<body>

    <jsp:include page="theme-loader.jsp"></jsp:include>

    <div id="pcoded" class="pcoded">
        <div class="pcoded-overlay-box"></div>
        <div class="pcoded-container navbar-wrapper">

            <jsp:include page="navebar.jsp"></jsp:include>

            <div class="pcoded-main-container">
                <div class="pcoded-wrapper">

                    <jsp:include page="mainMenu.jsp"></jsp:include>

                    <div class="pcoded-content">

                        <jsp:include page="page-header.jsp"></jsp:include>

                        <div class="pcoded-inner-content">
                            <div class="main-body">
                                <div class="page-wrapper">
                                    <div class="page-body">
                                    
                                        <div class="alert alert-info">
                                          <span>${msg}</span>
                                        </div>
                                        
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <!-- Basic Form Inputs card start -->
                                                <div class="card">
                                                    <div class="card-block">
                                                        <h3 class="sub-title">Cad.Usuário</h3>

                                                        <form Class="form-material" enctype="multipart/form-data" action="<%= request.getContextPath() %>/ServletUsuarioController" method="post" id="formUser">
                                                        
                                                        <input type="hidden" name="acao" id="acao" value="">
                                                            <div class="form-group row">
                                                                <label class="col-sm-2 col-form-label">ID</label>
                                                                <div class="col-sm-1">
                                                                    <input type="text" name="id" id="id" class="form-control" readonly="readonly" value="${modelLogin.id}">
                                                                    </div>
                                                                </div>
                                                             <div class="input-group mb-4">
                                                               <div class="input-group-prepend">
                                                               <img alt="Imagem User" id="fotoembase64" src="" width="70px">
                                                               </div>
                                                               <input type='file' id="fileFoto" name="fileFoto" accept="image/*" onchange="visualizarImg('fotoembase64', 'fileFoto')" class="form-control-life">
                                                            </div>   


                                                            <div class="form-group row">
                                                                <label class="col-sm-2 col-form-label">Nome</label>
                                                                <div class="col-sm-4">
                                                                    <input type="text" name="nome" id="nome" class="form-control" required="required" autocomplete="off" value="${modelLogin.nome}">
                                                                </div>
                                                            </div>
                                                            
                                                            <div class="form-group row">
                                                                <label class="col-sm-2 col-form-label">Email</label>
                                                                <div class="col-sm-4">
                                                                    <input type="email" name="email" id="email" class="form-control" required="required" autocomplete="off" value="${modelLogin.email}">
                                                                </div>
                                                            </div>
                                                            
                                                            <div class="form-group row">
                                                                <label class="col-sm-2 col-form-label">Login</label>
                                                                <div class="col-sm-4">
                                                                    <input type="text" name="login" id="login" class="form-control" required="required" autocomplete="off" value="${modelLogin.login}">
                                                                </div>
                                                            </div>                                                            
                                                            
                                                            <div class="form-group row">
                                                                <label class="col-sm-2 col-form-label">Senha</label>
                                                                <div class="col-sm-4">
                                                                    <input type="password" name="senha" id="senha" class="form-control" required="required" autocomplete="off" value="${modelLogin.senha}">
                                                                </div>
                                                             </div>

															<div class="form-group row">
																<div class="col-sm-4">
															<input type="radio" name="sexo" value="MASCULINO" <%
																	    
																	ModelLogin modelLoginsexo = (ModelLogin) request.getAttribute("modelLogin");
																	    
																	    if (modelLoginsexo != null && modelLoginsexo.getSexo().equals("MASCULINO")){
																			
																			out.println("");
																			out.println("checked=\"checked\"");
																			out.println("");
																		} %>
															/> Masculino
															
															<input type="radio" name="sexo" value="FEMININO" 
															
															<% modelLoginsexo = (ModelLogin) request.getAttribute("modelLogin"); 
																    if (modelLoginsexo != null && modelLoginsexo.getSexo().equals("FEMININO")){
																		
																		out.println("");
																		out.println("checked=\"checked\"");
																		out.println("");
																	}															
															
															%>
															
															/> Feminino
																</div>
															</div>

															<div class="form-group row">
																<label class="col-sm-2 col-form-label" for="perfil">Perfil</label>
																<div class="col-sm-10">
																	<select class="form-control" name="perfil" id="perfil"required>
																		<option selected="selected">[Seleciona o Perfil]</option>
																		
																		<option value="ADMIN" <%
																		
																		ModelLogin modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																		
																		if (modelLogin != null && modelLogin.getPerfil().equals("ADMIN")){
																			
																			out.println("");
																			out.println("selected=\"selected\"");
																			out.println("");
																		} %>>ADMIN</option>
																		
																		
																	    <option value="SECRETARIA" <%
																	    
																	    modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																	    
																	    if (modelLogin != null && modelLogin.getPerfil().equals("SECRETARIA")){
																			
																			out.println("");
																			out.println("selected=\"selected\"");
																			out.println("");
																		} %>>SECRETARIA</option>
																		<option value="AUXILIAR" <%
																		
																		modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																		
																		if (modelLogin != null && modelLogin.getPerfil().equals("AUXILIAR")){
																			
																			out.println("");
																			out.println("selected=\"selected\"");
																			out.println("");
																		} %>>AUXILIAR</option>
																		
																		
																	</select>
																</div>
															</div>
															<button class="btn btn-primary waves-effect waves-light"> Novo</button>
                                                            <button type="submit" onclick="document.getElementById('acao').value='salvar'" class="btn btn-primary">Salvar</button>
                                                            <button type="button" onclick="criarDelete()" class="btn btn-primary waves-effect waves-light">Deletar</button>
                                                            <button type="button" class="btn btn-primary" data-toggle="modal"  data-target="#exampleModal">Buscar Usuário</button>
                                                        </form>

                                                    </div>
                                                </div>
                                            </div>
										</div>

										<div style="height: 300px; overflow: scroll;">
											<table class="table table-dark" id="view">
												<thead>
													<tr>
														<th scope="col">ID</th>
														<th scope="col">Nome Completo</th>
														<th scope="col">Detalhes do Usuario</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${modelLogins}" var="ml">
														<tr>
															<td><c:out value="${ml.id}"></c:out></td>
															<td><c:out value="${ml.nome}"></c:out></td>
															<td><a class="btn btn-primary" href=<%=request.getContextPath() %>/ServletUsuarioController?acao=buscarEditar&id=${ml.id}>ver</a></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>

									</div>
                                </div>
                                <div id="styleSelector"> </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
    

    <jsp:include page="fileJS.jsp"></jsp:include>
    
    
<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Verificar Dados</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      
        <div class="form-row">
         <div class="col">
         <input type="text" class="form-control" placeholder="Digite o nome do usuário" id="nomeBusca">
         <button type="submit" class="btn btn-primary" onClick="buscarUsuario();"> Localizar</button>
         </div>
      </div>
      <div style="height: 300px;overflow: scroll;">
      <table class="table table-dark" id="tabelaresultados">
  <thead>
    <tr>
      <th scope="col">ID</th>
      <th scope="col">Name Completo</th>
      <th scope="col">Detalhes do Usuario</th>
    </tr>
  </thead>
  <tbody>

  </tbody>
</table>
</div>
 <span id="totalResultados" class="badge badge-info"></span>
  </div>
    
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
    
    <script type="text/javascript">
    
    function Detalhes(id){
    
    	var urlAction = document.getElementById('formUser').action;
    	
    	window.location.href = urlAction + '?acao=buscarEditar&id='+id;
    }
    
    function buscarUsuario(){
    	var nomeBusca = document.getElementById('nomeBusca').value;
    	
    	if(nomeBusca != null && nomeBusca != '' && nomeBusca.trim() != ''){
    		
    		var urlAction = document.getElementById('formUser').action;
    		
    		$.ajax({
    			method: "get",
    			url : urlAction,
    			data : "nomeBusca=" + nomeBusca + '&acao=buscarUserAjax',
    			success: function (response){
    				
    			var json = JSON.parse(response);
    			
    			$('#tabelaresultados > tbody > tr').remove();
    			
    			for(var p = 0; p < json.length; p++){
    				$('#tabelaresultados > tbody').append('<tr> <td>'+ json[p].id +'</td> <td>'+ json[p].nome +'</td><td><button onclick="Detalhes('+json[p].id+')" type="button" class="btn btn-primary">Detalhes</button></td></tr>')
    			}
    			
    			document.getElementById('totalResultados').textContent = 'Resultados:' + json.length;
    			
    			}
    		}).fail(function(xhr, status, errorThrown){
    			alert('Erro ao buscar usuario por nome: ' + xhr.responseText)
    		});
    	}
    }
    
    function criarDeleteComAjax(){
    	
    	if (confirm('Deseja realmente excluir os dados?')){
    		
    		var urlAction = document.getElementById('formUser').action;
    		var idUser = document.getElementById('id').value;
    		
    		$.ajax({
    			method: "get",
    			url : urlAction,
    			data : "id=" + idUser + '&acao=deletarajax',
    			success: function (response){
    				alert(response);
    			}
    		}).fail(function(xhr, status, errorThrown){
    			alert('Erro ao deletar usuario por id: ' + xhr.responseText)
    		});
    		
    	}
    }
    
 
    
    function criarDelete() {
    	
    	if(confirm('Deseja Realmente Excluir Os Dados?')){
    		document.getElementById("formUser").method = 'get';
        	document.getElementById("acao").value = 'deletar';
        	document.getElementById("formUser").submit();
    	}

    }
    
    function limparForm() {
        
        var elementos = document.getElementById("formUser").elements; /*Retorna os elementos html dentro do form*/
        
        for (p = 0; p < elementos.length; p ++){
    	    elementos[p].value = '';
        }
    }

    function visualizarImg(fotoembase64, filefoto) {
        var preview = document.getElementById(fotoembase64);
        var fileUser = document.getElementById(filefoto).files[0];
        var reader = new FileReader();

        reader.onloadend = function () {
            preview.src = reader.result;
        };

        if (fileUser) {
            reader.readAsDataURL(fileUser);
        } else {
            preview.src = '';
        }
    }
    
    </script>

</body>

</html>