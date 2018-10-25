var farmcontroller = require('../Controller/farmcontroller');
var express = require('express');
var multer = require("multer");
var path = require("path");
var app = express();
var multer = require("multer");


module.exports = function () {
  var storage = multer.diskStorage({
    destination: function(req, file ,callback){
      callback(null, "./FarmImage/");
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
    app.post('/success',upload.any(),farmcontroller.insertFarm);
    app.post('/',farmcontroller.findFarm);
    app.post('/view/update/:id',upload.any(),farmcontroller.updateFarm);
    app.post('/view/delete/:id',farmcontroller.deleteFarm);
    app.get('/view/:id',farmcontroller.findOneFarm);
    app.get('/', farmcontroller.findFarm);
    app.get('/web', farmcontroller.findFarmForWeb);
    app.get('/detailFarm/:id',farmcontroller.findDetailFarm);
    app.get('/mainpage',farmcontroller.findMainPageFarm);
    app.get('/main/:id',farmcontroller.findOneFarmMainPage);
    app.post('/:name',farmcontroller.findFarmForAuth);
    app.get('/main',farmcontroller.findMainFarm);
    app.get('/insertmainfarm',function(req,res){
      res.render('insertmainpagefarm');
    });
    app.get('/createfarm',function(req,res){
      res.render('insertFarm');
    });
    return app;
};
