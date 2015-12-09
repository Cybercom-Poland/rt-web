var socket;
//TODO add rt-web wrapper
var modelObject = {
     method: null,
     address: "/second",
     methodToInvoke: "/methodToInvoke",
     parameters: null,
     body: {
        someText: "string",
            someNumber: 43,
            someBollean: true,
            embededObject: {
                name: "mic"
            }
     }
};

var sock = new SockJS('localhost:8081/ws');

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
    sock.send(JSON.stringify(modelObject));
}