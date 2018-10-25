var express = require('express');
var mongoose = require('mongoose');
var router = express.Router();
var bodyParser = require('body-parser');
var multer = require("multer");
var path = require("path");
var Product = require("./Models/Product");
var url = require('./config/url');
var fs = require('fs');
var path = require('path');
var async = require('async');

router.use(bodyParser.urlencoded({ extended: false }));//body parser를 사용함

var storage = multer.diskStorage({
  destination: function(req, file ,callback){
    callback(null, "./ProductImage/");
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

// 뷰 페이지 경로
router.get('/createproductimage', function(req, res) {
  res.render("createproductimage");
});

router.post('/checkproductimg/:id',function(req,res){
  var filename = req.params.id;
  fs.exists(__dirname+'/ProductImage/'+filename, function (exists) {
      if (exists) {
          res.json({result:true});
      } else {
          res.json({result:false});
      }
  });
});

// 파일 업로드 처리
router.post('/createproductimage/success', upload.any(), function(req, res, next) {
  var file = req.files;
  var size = req.files.length;
  var i = 0;

  async.each(file,function(data,callback){
    Product.findOne({productname:req.body.productname},function(error,Product){
      console.log(url.productimgurl + "noimage.jpg");
      console.log(data);
      if(Product.imgpath[0] == url.productimgurl + "noimage.jpg"){
        Product.update({imgpath:url.productimgurl + data.originalname},function(data){
          console.log("imgpath saved");
        });
      }
      else{
        Product.update({$push:{imgpath:url.productimgurl + data.originalname}},function(data){
          console.log("imgpath saved");
        });
      }
    });
    i++;
    callback(null);
  });

  res.json(req.files);
});

module.exports = router;
