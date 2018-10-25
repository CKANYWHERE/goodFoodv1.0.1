var express = require('express');
var createError = require('http-errors');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var mongoose = require('mongoose');
var app = express();

mongoose.Promise = global.Promise;
mongoose.connect('mongodb://localhost:27017/mongodb');
var db = mongoose.connection;
db.on('error', function(){
    console.log('Connection Failed!');
});
db.on('open', function() {
    console.log('Connected!');
});
app.listen(3000,function(){
  console.log("Server started at 3000");
});
require('./routes');

module.exports = app;
