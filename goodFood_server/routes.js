var userRoute = require('./routes/User');
var sellerRoute = require('./routes/Seller');
var productRoute = require('./routes/Product');
var farmRoute = require('./routes/Farm');
var FarmImageRoute = require('./UploadFarmImage.js');
var ProductImageRoute = require('./UploadProductImage.js');
var LiveStreamRoute = require('./routes/LiveStream');
var ReStreamRoute =  require('./routes/ReStream');
var fs = require('fs');

module.exports = function(app) {
  app.use('/api/User', userRoute());
  app.use('/api/Seller', sellerRoute());
  app.use('/', productRoute());
  app.use('/api/Farm', farmRoute());
  app.use('/',FarmImageRoute);
  app.use('/',ProductImageRoute);
  app.use('/api/LiveStream',LiveStreamRoute());
  app.use('/api/ReStream',ReStreamRoute());

  app.get('/ProductImage/:id',function (req,res){
      var filename = req.params.id;
      console.log(__dirname+'/ProductImage/'+filename);
      fs.exists(__dirname+'/ProductImage/'+filename, function (exists) {
          if (exists) {
              fs.readFile(__dirname+'/ProductImage/'+filename, function (err,data){
                  res.end(data);
              });
          } else {
            fs.readFile(__dirname+'/ProductImage/noimage.jpg', function (err,data){
                res.end(data);
            });
          }
      });
  });

  app.get('/FarmImage/:id',function (req,res){
      var filename = req.params.id;
      console.log(__dirname+'/FarmtImage/'+filename);
      fs.exists(__dirname+'/FarmImage/'+filename, function (exists) {
          if (exists) {
              fs.readFile(__dirname+'/FarmImage/'+filename, function (err,data){
                  res.end(data);
              });
          } else {
            fs.readFile(__dirname+'/FarmImage/noimage.jpg', function (err,data){
                res.end(data);
            });
          }
      });
  });

  app.get('/LiveStreamImage/:id',function (req,res){
      var filename = req.params.id;
      console.log(__dirname+'/LiveStreamImage/'+filename);
      fs.exists(__dirname+'/LiveStreamImage/'+filename, function (exists) {
          if (exists) {
              fs.readFile(__dirname+'/LiveStreamImage/'+filename, function (err,data){
                  res.end(data);
              });
          } else {
            fs.readFile(__dirname+'/LiveStreamImage/noimage.jpg', function (err,data){
                res.end(data);
            });
          }
      });
  });


  app.get('/ReStreamImage/:id',function (req,res){
      var filename = req.params.id;
      console.log(__dirname+'/ReStreamImage/'+filename);
      fs.exists(__dirname+'/ReStreamImage/'+filename, function (exists) {
          if (exists) {
              fs.readFile(__dirname+'/ReStreamImage/'+filename, function (err,data){
                  res.end(data);
              });
          } else {
            fs.readFile(__dirname+'/ReStreamImage/noimage.jpg', function (err,data){
                res.end(data);
            });
          }
      });
  });
  app.get('*', function(req, res) {
  		res.render('./public/index.html');
  	});
};
