var stompClient = null;

function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	if (connected) {
		$("#conversation").show();
	} else {
		$("#conversation").hide();
	}
	$("#greetings").html("");
}

function connect() {
	var socket = new SockJS('/mensajes');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		setConnected(true);
		console.log('Connected: ' + frame);

		stompClient.subscribe('/user/combate/mensajes', 
			function(turnoAccionOut) {
				showMensaje(JSON.parse(turnoAccionOut.body).mensaje);
			});
/*
		stompClient.subscribe('/combate/mensajes', 
			function(turnoAccionOut) {
				showMensaje(JSON.parse(turnoAccionOut.body).mensaje);
			});
*/		
	});
}

function disconnect() {
	if (stompClient !== null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

function sendAccion() {
	stompClient.send("/app/accion", {}, JSON.stringify({
		'accionId' : $("#accionId").val(),
		'objetivoId' : $("#objetivoId").val()
	}));
}

function showMensaje(message) {
	$("#mensajes").append("<tr><td>" + message + "</td></tr>");
}

$(function() {
	$("form").on('submit', function(e) {
		e.preventDefault();
	});
	$("#connect").click(function() {
		connect();
	});
	$("#disconnect").click(function() {
		disconnect();
	});
	$("#send").click(function() {
		sendAccion();
	});
});