<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>

<body>

	<jsp:include page="theme-loader.jsp"></jsp:include>

	<!-- Pre-loader end -->
	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">

			<jsp:include page="navebar.jsp"></jsp:include>

			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">
					<jsp:include page="mainMenu.jsp"></jsp:include>
					<div class="pcoded-content">
						<!-- Page-header start -->
						<jsp:include page="page-header.jsp"></jsp:include>

						<!-- Page-header end -->
						<div class="pcoded-inner-content">
							<!-- Main-body start -->
							<div class="main-body">
								<div class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body">
										<div class="alert alert-info">
											<span>${msg}</span>
										</div>
										<div class="row">
											<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
												<div class="card">
													<div class="card-block">
														<h3 class="sub-title">Cad.Telefone</h3>
														<form Class="form-material"
															action="<%=request.getContextPath()%>/ServletTelefone"
															method="post" id="formfone">
															<input type="hidden" name="acao" id="acao" value="">
															<div class="form-group row">
																<label class="col-sm-2 col-form-label">ID User</label>
																<div class="col-sm-1">
																	<input type="text" name="id" id="id"
																		class="form-control" readonly="readonly"
																		value="${modelLogin.id}">
																</div>
															</div>

															<div class="form-group row">
																<label class="col-sm-2 col-form-label">Nome</label>
																<div class="col-sm-4">
																	<input type="text" readonly="readonly" name="nome"
																		id="nome" class="form-control" required="required"
																		autocomplete="off" value="${modelLogin.nome}">
																</div>
															</div>

															<div class="form-group row">
																<label class="col-sm-2 col-form-label">Numero
																	Telefone</label>
																<div class="col-sm-4">
																	<input type="text" name="numero" id="numero"
																		class="form-control" required="required"
																		autocomplete="off"">
																</div>
															</div>

															<button class="btn btn-primary">Salvar</button>
														</form>
													</div>
												</div>
												<!--  project and team member end -->
											</div>
										</div>


										<div style="height: 300px; overflow: scroll;">
											<table class="table table-dark" id="view">
												<thead>
													<tr>
														<th scope="col">ID</th>
														<th scope="col">Numero</th>
														<th scope="col">Excluir</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${modelTelefones}" var="f">
														<tr>
															<td><c:out value="${f.id}"></c:out></td>
															<td><c:out value="${f.numero}"></c:out></td>
															<td><a class="btn btn-primary"
																href="<%=request.getContextPath()%>
																/ServletTelefone?acao=excluir&id=${f.id}&userpai=${modelLogin.id}" >Excluir</a></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
									<div id="styleSelector"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="fileJS.jsp"></jsp:include>
	<script type="text/javascript"> 
	
	$("#numero").keypress(function (event){
    	return /\d/.test(String.fromCharCode(event.keyCode));
    });
	
	</script>

</body>

</html>
