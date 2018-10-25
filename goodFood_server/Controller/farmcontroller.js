var mongoose = require('mongoose');
var Farm = require('../Models/Farm');
var Product = require('../Models/Product');
var fs = require('fs');
var async = require('async');
var url = require('../config/url');

module.exports = {

  insertFarm : function(req, res, next){
    console.log(req.body);
    var newFarm = new Farm({
    farmname:req.body.farmname,
    address:req.body.address,
    formainpage:req.body.formainpage,
    coverimg:url.farmimgurl+req.files[0].originalname,
    farmimg:url.farmimgurl+req.files[1].originalname,
  });
  newFarm.save(function(error, data){
      if (error) {
        console.log(error);
      }
      else{
        console.log('saved');
      }
    }); // DB 정보 저장
    res.render("index");
  },

deleteFarm :function(req,res,next){

Farm.findOne({_id:req.params.id},function(error,Farm){
  fs.unlink('./' + Farm.imgpath,function(err){
    console.log(Farm.imgpath);
    if(error){
      console.log(err);
    }else{
      console.log('deleted');
    }
  });
});

  Farm.remove({_id:req.params.id},function(error){
  if (error) {
    console.log(error);
  }else{
    res.render('index');
  }
});
},

updateFarm : function(req,res,next){
  Farm.findById({_id:req.params.id},function(error,Farm){
    if(error){
      console.log(error);
    }
    else{
      Farm.farmname=req.body.farmname;
      Farm.address=req.body.address;
      Farm.farmorder=req.body.farmorder;
      Farm.description=req.body.description;
      Farm.coverimg=url.farmimgurl+req.files[0].originalname;
      Farm.farmimg=url.farmimgurl+req.files[1].originalname;
      Farm.save(function(error,ModifiedFarm){
        if(error){
          console.log(error);
        }else {
          res.render('index');
        }

      });
    }
  });
},



findFarm : function(req,res,next){
  Farm.find({}).sort({}).exec( function(error,Farm){
    if (error) {
      console.log(error);
    }
    else{
      res.send(Farm);
    }
  });
},

findMainFarm : function(req,res,next){
  Farm.find({"formainpage":"true"}).sort({}).exec( function(error,Farm){
    if (error) {
      console.log(error);
    }
    else{
      res.send(Farm);
    }
  });
},

findDetailFarm : function(req,res,next){
  Farm.findOne({_id:req.params.id},function(error,Farm){
    if(error){
      console.log(error);
    }
    else{
      if(Farm.product == null){
          res.send({"Products":[]});
      }

    var products = [];
    var products_id = [];

    var i = 0;
     async.each(Farm.product,function(data,callback){
        products_id.push({
          _id:Farm.product[i]
        });
        console.log(Farm.product);
        i++;
        callback(null);
      });

      var j = 0;
      async.each(products_id,function(data,callback){
          Product.findById({_id:products_id[j]._id},function(error,Product){
            products.push(Product);
            console.log(Product);
            callback(null);
          });
          j++;
        },function(error){
          res.send(products);
        });
    }
  });
},

findFarmForAuth : function(req,res,next){
  console.log('test1');
    Farm.findOne({farmname:req.body.name}, function(error,Farm){
    if (error) {
      console.log(error);
    }
    console.log(Farm);
    if(Farm == null) {
      console.log('false');
      res.json({result:"false"});
    }
    else{
      console.log('sucess');
      res.json({result:"true"});
    }
  });
},

findFarmForWeb : function(req,res,next){
  Farm.find({}).sort({}).exec( function(error,Farm){
    if (error) {
      console.log(error);
    }
    else{
      console.log(Farm);
    }
  res.render('findfarm',{title:"Farm",farm:Farm});
  });
},

findOneFarm : function(req,res,next){
  var id = req.params.id;
  Farm.findOne({_id:id} ,function(error,Farm){
    if (error) {
      console.log(error);
    }
    else{
      console.log(Farm);
    }
  res.render('detailfindfarm',{title:"Farm",farm:Farm});
  });
},
findOneFarmMainPage : function(req,res,next){
  var id = req.params.id;
  Farm.findOne({_id:id} ,function(error,Farm){
    if (error) {
      console.log(error);
    }
    else{
      console.log(Farm);
    }
  res.render('detailfindfarmmain',{title:"Farm",farm:Farm});
  });
},
findMainPageFarm : function(req,res,next){
  Farm.find({"formainpage":"true"}).sort({}).exec( function(error,Farm){
    if (error) {
      console.log(error);
    }
    else{
      console.log(Farm);
    }
  res.render('findfarmformain',{title:"Farm",farm:Farm});
  });
},

};
