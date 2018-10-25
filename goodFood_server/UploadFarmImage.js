var express = require('express');
var mongoose = require('mongoose');
var router = express.Router();
var bodyParser = require('body-parser');
var multer = require("multer");
var path = require("path");
var Farm = require("./Models/Farm");
var url = require('./config/url');

router.use(bodyParser.urlencoded({ extended: false }));//body parser를 사용함

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

// 뷰 페이지 경로
router.get('/createfarmimage', function(req, res) {
  res.render("createfarmimage");
});

// 파일 업로드 처리
router.post('/createfarmimage/success', upload.single("imgFile"), function(req, res, next) {
  var file = req.file;

  var result = {
    originalName : file.originalname,
    size : file.size,
  };

  Farm.findOne({farmname:req.body.farmorigin},function(error,Farm){
    console.log(url.farmimgurl + "noimage.jpg");
    if(Farm.imgpath[0] == url.farmimgurl + "noimage.jpg"){
      Farm.update({imgpath:url.farmimgurl + file.originalname},function(data){
        console.log("imgpath saved");
      });
    }
    else{
      Farm.update({$push:{imgpath:url.farmimgurl + file.originalname}},function(data){
        console.log("imgpath saved");
      });
    }
  });
  res.json(result);
});

module.exports = router;
