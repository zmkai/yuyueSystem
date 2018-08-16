var login = $("button:first");
login.click(function() {
	$.post("http://localhost:8080/yuyueSystem/login", {
			account: $("input[name='account']").val()
		},
		function(data, status) {
			alert("成功");
		})
})