var mongoose = require('mongoose');
var Seller = require('../Models/Seller');

module.exports = {

insertSeller : function(req, res,next){
  var newSeller = new Seller({
  name:req.body.name,
  age:req.body.age,
  id:req.body.id,
  password:req.body.password,
  usernumber:req.body.number});
  newSeller.save(function(error, data){
    if (error) {
      console.log(error);
    }
    else{
      res.send(data);
    }
  });
},

deleteSellerWeb :function(req,res,next){
  var contentId = req.params.id;
  Seller.remove({_id:contentId},function(error){
  if (error) {
    console.log(error);
  }else{
  res.render('index');
  }
});
},

deleteSeller :function(req,res,next){
  Seller.remove({id:req.body.id},function(error){
  if (error) {
    console.log(error);
  }else{
    res.send('sucess');
  }
});
},

updateSellerWeb : function(req,res,next){
  Seller.findById({_id:req.params.id},function(error,Seller){
    if(error){
      console.log(error);
    }
    else{
      Seller.name = req.body.name;
      Seller.age = req.body.age;
      Seller.id = req.body.id;
      Seller.password = req.body.password;
      Seller.save(function(error,ModifiedSeller){
        if(error){
          console.log(error);
        }else {
        res.send(ModifiedSeller);
        }

      });
    }
  });
},

updateSeller : function(req,res,next){
  Seller.findById({id:req.body.id},function(error,Seller){
    if(error){
      console.log(error);
    }
    else{
      Seller.name = req.body.name;
      Seller.age = req.body.age;
      Seller.id = req.body.id;
      Seller.password = req.body.password;
      Seller.save(function(error,ModifiedSeller){
        if(error){
          console.log(error);
        }else {
          res.send(ModifiedSeller);
        }

      });
    }
  });
},

findSellerForAuth : function(req,res,next){
  console.log('test1');
  Seller.findOne({id:req.body.name}, function(error,Seller){
    if (error) {
      console.log(error);
    }
    console.log(Seller);
    if(Seller == null) {
      console.log('false');
      res.json({result:"false"});
    }
    else{
      console.log('sucess');
      res.json({result:"true"});
    }
  });
},

findSellerForWeb : function(req,res,next){
  Seller.find({}).sort({}).exec(function(error,Seller){
    if (error) {
      console.log(error);
    }
    else{
      console.log(Seller);
    }
  res.render('findnormalseller',{title:"Seller",seller:Seller});
  });
},

findSeller : function(req,res,next){
  Seller.find({}).sort({}).exec(function(error,Seller){
    if (error) {
      console.log(error);
    }
    else{
      res.send(Seller);
    }
  });
},

findOneSeller : function(req,res,next){
  var contentId = req.params.id;
  Seller.findOne({_id:contentId},function(error,Seller){
    if (error) {
      console.log(error);
    }
    else{
      console.log(Seller);
    }
  res.render('detailfindseller',{title:"Seller",seller:Seller});
  });
}

};
