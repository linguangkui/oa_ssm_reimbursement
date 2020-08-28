<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>

<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>OA办公自动化系统-登录页</title>
<link rel="stylesheet" href="static/css/bootstrap.min.css">
<link rel="stylesheet" href="static/css/style1.css">
<link rel="stylesheet" href="static/css/bootstrapValidator.min.css">
<script src="static/js/jquery-1.11.0.min.js"></script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/bootstrapValidator.min.js"></script>

<style type="text/css">
body{
 		background-image:url(static/images/img2.jpg);
 		background-repeat: no-repeat;
 		background-size:100%;
 }
.container{
 	 margin-left: 20px;
 } 
 #loginForm{
 	opacity: 0.7;
 	background-color: darkgray;
 }
.row {
	margin-left: 250px;
	margin-top: 60px;
}

#sub{
	margin-top:28px;
	margin-right: 88px;
}
#img{
	margin-top: 18px;
}
#remember{
	padding-left: 52px;
}
#f{
	padding-left: 68px;
}
</style>
		<script>
            window.onload = function () {
                document.getElementById("img").onclick = function () {
                    this.src = "${pageContext.request.contextPath}/check?time="+Math.random();
                }
            }
        </script>
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-md-offset-3 col-md-6">

				<form class="form-horizontal" action="login" method="post"
					id="loginForm">
					<span class="heading">OA办公自动化系统</span>
					<%-- <div class="error">${error}</div> --%>
					<div class="form-group">
						<div class="col-sm-10">
							<input type="text" name="username" class="form-control"
								id="username" placeholder="请输入用户名"> <i
								class="fa fa-user"></i>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-10">
							<input type="password" name="password" class="form-control"
								id="password" placeholder="请输入密码"> <i
								class="fa fa-lock"></i> <a href="#"
								class="fa fa-question-circle"></a>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-10">
							<input type="text" name="mycode" class="form-control"
								id="mycode" placeholder="请输入验证码">
								<img src="${pageContext.request.contextPath}/check" id="img" name="randomCode" alt="验证码图片加载失败"><br>
        					<span style="color:red;">${codeErrorMsg}</span> <i
								class="fa fa-lock"></i> <a href="#"
								class="fa fa-question-circle"></a>
						</div>
					</div>
					<div class="form-group" id="remember">
						<div class="main-checkbox">
							<input type="checkbox" name="rememberMe" value="1"
								id="checkbox1" /> <label for="checkbox1"></label>
						</div>
						<span class="text">Remember me</span>
						<div class="col-sm-10">
						  <span style="color:red;font-size: 14px;font-weight: bold;">${errorMsg}</span>
							<button type="submit" id="sub" name="sub" class="btn btn-primary">登录</button>
						</div>
					</div>
				</form>
			  <div id="f">Copyright © 2020  Made by JAVA课程-孙飞</div>
			</div>
		</div>

	</div>


</body>
</html>