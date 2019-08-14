# Esptouch For Cordova 

An Esptouch library for Cordova.

**Now only support Android platform**.

## Installation

```
cordova plugin add cordova-plugin-smartesptouch --save
```

## Usage

Fiist, Get Esptouch plugin from global variable:

```javascript
const esptouch =  cordova.plugins.EsptouchForCordova;
```

> This must be effective in a real machine environment.

### start config wifi

```javascript
/**
* @param wifiName       WiFi name
* @param wifiPassword   WiFi passwod
* @param wifiMac        WiFi bssid
*/
function start(wifiName, wifiPassword, wifiMac) {
  const successCallback = function (resp) {
	console.log('wifi config success:', JSON.stringify(resp))
  };
  const failCallback = function (resp) {
    console.error('wifi config fail:', JSON.stringify(resp))
  };
  esptouch.start(wifiName, wifiPassword, wifiMac, successCallback, failCallback);
}

```

### stop cofig wifi


```javascript
function stop() {
  const successCallback = function (resp) {
    console.log('wifi config stop success:', JSON.stringify(resp))
  };
  const failCallback = function (resp) {
    console.error('wifi config stop fail:', JSON.stringify(resp))
  };
  esptouch.stop(successCallback, failCallback);
}

```

## License

Copyright 2019 Chiwei Hu

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
