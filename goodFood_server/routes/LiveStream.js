var livestreamcontroller = require('../Controller/livestreamcontroller');
var express = require('express');
var multer = require("multer");
var path = require("path");
var app = express();

module.exports = function () {
  var storage = multer.diskStorage({
    destination: function(req, file ,callback){
      callback(null, "./LiveStreamImage/");
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

    app.post('/success',upload.any(), livestreamcontroller.insertLiveStream);
    app.get('/web',  livestreamcontroller.findLiveStreamForWeb);
    app.get('/',  livestreamcontroller.findLiveStream);
    app.post('/delete/:id', livestreamcontroller.deleteLiveStream);
    app.get('/view/:id',livestreamcontroller.findOneLiveStream);
    app.get('/show/:id',livestreamcontroller.findDetailLiveStream);
    app.get('/start',function(req,res){
      res.render('insertlivestream');
    });
    return app;
};
