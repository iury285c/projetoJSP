<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   
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
										<div class="row">
											<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
												<div class="card">
													<div class="card-block">
														<h4 class="sub-title">Relatorio Salarial</h4>

														<form class="form-material" 
															action="<%=request.getContextPath()%>/ServletUsuarioController"
															method="get" id="formUser">
															
															<input type ="hidden" id="acaoRelatorioImprimirTipo" name="acao" value="imprimirRelatorioUser">
															
															<div class="form-row align-items-center">
																<div class="col-sm-3 my-1">
																	<label class="sr-only" for="dataInicial">Data Inicial</label>
																	<input type="text" class="form-control"
																		id="dataInicial" name="dataInicial" value="${dataInicial}" >
																</div>
																<div class="col-sm-3 my-1">
																	<label class="sr-only"
																		for="dataFinal">Data Final</label>
																		<input type="text" class="form-control"
																			id="dataFinal" name="dataFinal" value="${datafinal}" >
																	
																</div>
																<div class="col-auto my-1">
																	<button type="button" onclick="gerarGrafico();" class="btn btn-primary">Gerar Grafico</button>
																</div>
															</div>
																
																</form>

														<div style="height: 300px; overflow: scroll;">
															<div>
																	<canvas id="myChart"></canvas>
																</div>
														</div>


													</div>


												</div>
											</div>
											<!--  project and team member end -->
										</div>
									</div>
									<!-- Page-body end -->
								</div>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<jsp:include page="fileJS.jsp"></jsp:include>
	
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	
	<script type="text/javascript">
	function gerarGrafico(){
		var myChart = new Chart(
				document.getElementById('myChart'),
				{
					type: 'line',
					data: {
						labels: [
							'January',
							'February',
							'March',
							'April',
							'May',
							'June',
						],
						datasets: [{
							label: 'Grafico de media salarial',
						backgroundColor: 'rgb(255, 99, 132)',
						borderColor: 'rgb(255, 99, 132)',
						data: [0, 10, 5, 2, 20, 30, 45],
						}]
					},
					options: {}
				}
			);
	}
	
	
	
	$( function() {
  	  
  	  $("#dataInicial").datepicker({
  		    dateFormat: 'dd/mm/yy',
  		    dayNames: ['Domingo','Segunda','Ter�a','Quarta','Quinta','Sexta','S�bado'],
  		    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
  		    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','S�b','Dom'],
  		    monthNames: ['Janeiro','Fevereiro','Mar�o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
  		    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
  		    nextText: 'Pr�ximo',
  		    prevText: 'Anterior'
  		});
  } );
	
	
	$( function() {
  	  
  	  $("#dataFinal").datepicker({
  		    dateFormat: 'dd/mm/yy',
  		    dayNames: ['Domingo','Segunda','Ter�a','Quarta','Quinta','Sexta','S�bado'],
  		    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
  		    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','S�b','Dom'],
  		    monthNames: ['Janeiro','Fevereiro','Mar�o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
  		    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
  		    nextText: 'Pr�ximo',
  		    prevText: 'Anterior'
  		});
  } );
	
	</script>

</body>

</html>
