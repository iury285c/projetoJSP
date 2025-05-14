<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet">
  <title>Login</title>
  
  <style type="text/css">
  
  form{
  
  position: absolute;
  top: 40%;
  left: 33%;
  }
  
  h1{
  position: absolute;
  top:30%;
  left: 33%;
  }
  
  .msg{
  position: absolute;
  top:30%;
  left:33%;
  font-size: 22px;
  color:#842029;;
  background-color: ##f8d7da;
  border-color: #f5c2c7;
  }
  
  </style>
</head>
<body class="bg-light">

<div class="container mt-5">
  <div class="row justify-content-center">
    <div class="col-md-6">
    
      <form class="row g-3 needs-validation" action="<%=request.getContextPath() %>/ServletLogin" method="post" novalidate>

        <input type="hidden" value="<%=request.getParameter("url") %>" name="url">

        <div class="col-md-6">
          <label for="staticlogin" class="form-label">Login</label>
          <input name="login" id="staticlogin" type="text"  class="form-control" required="required">
      <div class="invalid-feedback">
       Please enter your Login
      </div>
        </div>

        <div class="col-md-6">
          <label for="inputPassword2" class="form-label">Senha</label>
          <input type="password" class="form-control" id="inputPassword2" name="senha" required="required">
      <div class="invalid-feedback">
        Please enter your password
      </div>
        </div>

        <div class="col-12">
          <button type="submit" class="btn btn-primary">Confirm</button>
        </div>

      </form>

<h5 class="msg">${msg}</h5>
    </div>
  </div>
</div>




<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>


<script type="text/javascript">

(function () {
	  'use strict'

	  // Fetch all the forms we want to apply custom Bootstrap validation styles to
	  var forms = document.querySelectorAll('.needs-validation')

	  // Loop over them and prevent submission
	  Array.prototype.slice.call(forms)
	    .forEach(function (form) {
	      form.addEventListener('submit', function (event) {
	        if (!form.checkValidity()) {
	          event.preventDefault()
	          event.stopPropagation()
	        }

	        form.classList.add('was-validated')
	      }, false)
	    })
	})()

</script>

</body>
</html>