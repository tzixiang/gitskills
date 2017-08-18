<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

<h2>你好WebSocket</h2>

<button onclick="sayHello()">发送消息</button>

</body>
<script type="text/javascript">

if ('WebSocket' in window){
	var url = "ws://" + window.location.host + "/" + "<%=request.getContextPath()%>/websocket/demo?userId=1001";
	var socket = new WebSocket(url);
	socket.onopen = function(){
		console.info("已连接!");
	}
	socket.onmessage = function(e){
		console.info("接收到消息："+e.data);
		sayHello();
	}
	socket.onclose = function(){
		console.info("已关闭!");
		socket.close();
	}
	
	function sayHello(){
		socket.send("你好吗?");
	}
	window.onbeforeunload = function(){
		socket.close();
	}
}

</script>
</html>