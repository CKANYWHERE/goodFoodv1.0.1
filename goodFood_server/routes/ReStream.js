var restreamcontroller = require('../Controller/restreamcontroller');
var express = require('express');
var multer = require("multer");
var path = require("path");
var app = express();

module.exports = function () {
  var storage = multer.diskStorage({
    destination: function(req, file ,callback){
      callback(null, "./ReStreamImage/");
    },

    filename: function(req, file, callback){
      var extension = path.extname(file.originalname);
      var basename = path.basename(file.originalname, extension);
      callback(null, file.originalname);
    }
  });

  var upload = multer({
    storage: storage
  });

    app.post('/success', upload.any(),restreamcontroller.insertReStream);
    app.get('/web',  restreamcontroller.findReStreamForWeb);
    app.get('/',  restreamcontroller.findReStream);
    app.post('/delete/:id', restreamcontroller.deleteReStream);
    app.get('/view/:id',restreamcontroller.findOneReStream);
    app.get('/main',restreamcontroller.findRecentReStream);
    app.get('/start',function(req,res){
      res.render('insertrestream');
    });
    return app;
};
