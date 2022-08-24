'use strict';

var os = require('os');
var nodeStatic = require('node-static');
var http = require('http');
var accesslog = require('access-log');

var fileServer = new (nodeStatic.Server)();
var app = http.createServer(function (req, res) {
  accesslog(req, res);
  fileServer.serve(req, res);
}).listen(8282);

