var productcontroller = require('../Controller/productcontroller');
var express = require('express');
var multer = require("multer");
var path = require("path");
var app = express();


module.exports = function () {
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
    app.post('/api/Product/success',upload.any(), productcontroller.insertProduct);
    app.get('/api/Product/web',  productcontroller.findProductForWeb);
    app.post('/api/Product/',  productcontroller.findProduct);
    app.post('/api/Product/view/update/:id', upload.any(),productcontroller.updateProduct);
    app.post('/api/Product/view/delete/:id',productcontroller.deleteProduct);
    app.get('/api/Product/view/:id',productcontroller.findOneProduct);
    app.get('/api/Product',productcontroller.findProduct);
    app.post('/api/Product/:name',productcontroller.findProductForAuth);
    app.get('/api/Product/mainpage',productcontroller.findMainPageProduct);
    app.get('/api/Product/main/:id',productcontroller.findOneProductMainPage);
    app.get('/api/Product/insertmainproduct',function(req,res){
      res.render('insertmainpageproduct');
    });
    app.get('/api/Product/main',productcontroller.findMainProduct);
    app.get('/api/Product/detail/:id',productcontroller.findOneProductForMobile);
    app.get('/createproduct',function(req,res){
      res.render('insertproduct');
    });
    return app;
};
