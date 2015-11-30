var socket;
var modelObject = {
    someText: "string",
    someNumber: 43,
    someBollean: true,
    embededObject: {
        name: "mic"
    }    
};

if (window.WebSocket) {
    socket = new WebSocket("ws://localhost:8080/api");
    socket.onmessage = function(event) {
        console.log("Received data: " +event.data);
    }
    socket.onopen = function(event) {        
        console.log("Web Socket opened");    
    };
    socket.onclose = function(event) {
        console.log("Web Socket closed");    
    };
} else {
    alert("Your browser does not support Websockets. Use something modern!");
}

function send(message) {
    if (!window.WebSocket) {
        return;
    }
    if (socket.readyState == WebSocket.OPEN) {
        socket.send(modelObject);
        console.log("Data send to web socket");    
    } else {
        console.log("Web socked is not opened");    
    }
}