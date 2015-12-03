var socket;
var modelObject = {
    someText: "string",
    someNumber: 43,
    someBollean: true,
    embededObject: {
        name: "mic"
    }    
};

var sock = new SockJS('localhost:8080/ws');

sock.onopen = function() {
  console.log('open');
};

sock.onmessage = function(e) {
  console.log('message', e.data);
};

sock.onclose = function() {
  console.log('close');
};

var send = function() {
    sock.send(modelObject);
}