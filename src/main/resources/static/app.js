var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
    $("#taskExecutor").html("");
}

function connect() {
    var socket = new SockJS('/stomp-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body));
        });
        stompClient.subscribe('/topic/taskexecutor', function (taskexecutor) {
            showTaskExecutor(JSON.parse(taskexecutor.body));
        });
        sendUpdateRequest();
    }, function (message) {
        disconnect();
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({ 'name': $("#name").val() }));
}

function sendUpdateRequest() {
    stompClient.send("/app/update", {}, JSON.stringify({}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message.message + "</td></tr>");
}

function showTaskExecutor(taskexecutor) {
    $("#taskExecutor").html(JSON.stringify(taskexecutor, undefined, 2));
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () { connect(); });
    $("#disconnect").click(function () { disconnect(); });
    $("#send").click(function () { sendName(); });
});

