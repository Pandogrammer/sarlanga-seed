var stompClient = null;
var combate = "/app/combate";

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
        var sessionId = /\/([^\/]+)\/websocket/.exec(socket._transport.url)[1];
        console.log("connected, session id: " + sessionId);
		setConnected(true);
		console.log('Connected: ' + frame);

		stompClient.subscribe('/combate/mensajes-'+sessionId, 
			function(turnoAccionOut) {
				showMensaje(JSON.parse(turnoAccionOut.body).mensaje);
			});
		
	});
}

function disconnect() {
	if (stompClient !== null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
	$("#mensajes").remove();
}

function accion() {
	stompClient.send(combate+"/accion", {}, JSON.stringify({
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
	$("#accion").click(function() {
		accion();
	});
	$("#crear").click(function() {
		crear();
	});
});