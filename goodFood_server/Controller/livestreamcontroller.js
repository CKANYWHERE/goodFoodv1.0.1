var mongoose = require('mongoose');
var LiveStream = require('../Models/LiveStream');
var Product = require('../Models/Product');
var url = require('../config/url');


module.exports = {


insertLiveStream : function(req, res,next){
    var file = req.files;
    console.log(file);
    var newLiveStream = new LiveStream({
    coverimg:url.liveimgurl+req.files[0].originalname,
    stream_name:req.body.stream_name,
    farm_name:req.body.farm_name,
    product_name:req.body.product_name,
    cost:req.body.cost,
    productimg:url.liveimgurl+req.files[1].originalname,
    stream_url:req.body.stream_url,
    chat_url:req.body.chat_url,
  });
  newLiveStream.save(function(error, data){
    if (error) {
      console.log(error);
    }
    else{
      console.log('saved');
    }
    res.render('index');
  });
},

deleteLiveStream :function(req,res,next){
  var contentId = req.params.id;
  LiveStream.remove({_id:contentId},function(error){
    if(error){
      console.log(error);
    }else {
      res.render('index');
    }
  });
},

findLiveStreamForWeb : function(req,res,next){
  LiveStream.find({}).sort({}).exec( function(error,LiveStream){
    if (error) {
      console.log(error);
    }
    else{
      console.log(LiveStream);
    }
  res.render('LiveStream',{title:"LiveStream",livestream:LiveStream});
});
},

findDetailLiveStream : function(req,res,next){
  LiveStream.findOne({_id:req.params.id},function(error,LiveStream){
      if(error){
        console.log(error);
      }
      else{
        Product.findOne({_id:LiveStream.product},function(error,Product){
          if(error){
            console.log(error);
          }else{
            res.json({imgpath:Product.imgpath[0]});
          }
        });
      }
  });
},

findLiveStream : function(req,res,next){
  LiveStream.find({}).sort({}).exec( function(error,LiveStream){
    if (error) {
      console.log(error);
    }
    else{
      res.send(LiveStream);
    }
});
},

findOneLiveStream : function(req,res,next){
  var contentId = req.params.id;
  LiveStream.findOne({_id:contentId},function(error,LiveStream){
    if (error) {
      console.log(error);
    }
    else{
      console.log(LiveStream);
    }
    res.render('detailfindlivestream',{title:"LiveStream",livestream:LiveStream});
  });
}

};
