var exec = require('cordova/exec');

/*exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'EsptouchForCordova', 'smartConfig', [arg0]);
};*/

module.exports = {
    start: function (wifiName, wifiPassword, wifiMac, successCallback, failCallback) {
        exec(successCallback, failCallback, "EsptouchForCordova", "start", [wifiName, wifiPassword, wifiMac]);
    },
    stop: function (successCallback, failCallback) {
        exec(successCallback, failCallback, "esptouch", "stop", []);
    }
};