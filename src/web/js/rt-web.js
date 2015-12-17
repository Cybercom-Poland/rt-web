let SockJS = require('sockjs-client');


class RtWeb {
  constructor(webSocketURL) {
    this.sockJS = new SockJS(webSocketURL);
    return this;
  }

  onopen(fn) {
    this.sockJS.onopen = fn;
  }

  onmessage(fn) {
    this.sockJS.onmessage = fn;
  }

  onclose(fn) {
    this.sockJS.onclose = fn;
  }

  send(object) {
    this.sockJS.send(JSON.stringify(object));
  }
}

window.RtWeb = RtWeb;
