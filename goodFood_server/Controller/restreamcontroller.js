var mongoose = require('mongoose');
var ReStream = require('../Models/ReStream');
var url = require('../config/url');

module.exports = {

insertReStream : function(req, res,next){
  var newReStream = new ReStream({
  coverimg:url.liveimgurl+req.files[0].originalname,
  stream_name:req.body.stream_name,
  farm_name:req.body.farm_name,
  product_name:req.body.product_name,
  cost:req.body.cost,
  productimg:url.liveimgurl+req.files[1].originalname,
  stream_url:req.body.stream_url,
});
  newReStream.save(function(error, data){
    if (error) {
      console.log(error);
    }
    else{
      console.log('saved');
    }
    res.render('index');
  });
},

deleteReStream :function(req,res,next){
  var contentId = req.params.id;
  ReStream.remove({_id:contentId},function(error){
    if(error){
      console.log(error);
    }else {
    res.render('index');
    }
  });
},

findReStreamForWeb : function(req,res,next){
  console.log("a");
  ReStream.find({}).sort({}).exec( function(error,ReStream){
    if (error) {
      console.log(error);
    }
    else{
      console.log(ReStream);
    }
  res.render('ReStream',{title:"ReStream",restream:ReStream});
});
},

findReStream : function(req,res,next){
  console.log("a");
  ReStream.find({}).sort({}).exec( function(error,ReStream){
    if (error) {
      console.log(error);
    }
    else{
      res.send(ReStream);
    }
});
},

findOneReStream : function(req,res,next){
  var contentId = req.params.id;
  ReStream.findOne({_id:contentId},function(error,ReStream){
    console.log("a");
    if (error) {
      console.log(error);
    }
    else{
      console.log(ReStream);
    }
    res.render('detailfindrestream',{title:"ReStream",restream:ReStream});
  });
},
findRecentReStream : function(req,res){
  ReStream.find().sort({_id:1},function(ReStream){
    res.send(ReStream);
  });
}
};
