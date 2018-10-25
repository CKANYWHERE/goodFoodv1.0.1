var mongoose = require('mongoose');
var Product = require('../Models/Product');
var Farm = require('../Models/Farm');
var fs = require('fs');
var url = require('../config/url');

module.exports = {
  insertProduct : function(req, res,next){
    console.log(req.body);
    var newProduct = new Product({
    _id: new mongoose.Types.ObjectId(),
    productname:req.body.productname,
    productorgin:req.body.productorgin,
    productcost:req.body.productcost,
    formainpage:req.body.formainpage,
    coverimg:url.productimgurl+req.files[0].originalname,
    productimg:url.productimgurl+req.files[1].originalname
  });
  newProduct.save(function(error, data){
      if (error) {
        console.log(error);
      }
      else{
        console.log("Product Save Success");
      }
    });
  Farm.findOne({farmname:req.body.productorgin},function(error,Farm){
          if(error){
            console.log(error);
          }
          if(Farm == null){
            res.send("Product has saved but no Farm for Product");
          }
          else{
            Farm.update({$push:{product:newProduct._id}},function(data){
              res.send("success");
            });
          }
      });
  },

deleteProduct :function(req,res,next){
  Product.findOne({_id:req.params.id},function(error,Product){
    fs.unlink('./' + Product.imgpath,function(err){
      console.log(Product.imgpath);
      if(error){
        console.log(err);
      }else{
        console.log('deleted image');
      }
    });
});

Product.findOne({_id:req.params.id},function(error,Product){
  Farm.findOne({farmname:Product.productorgin},function(error, Farm){
    if(error){
      console.log(error);
    }
      if(Farm == null){
        console.log("This product don't have farm ref");
      }
      else{
        Farm.update({$pull:{product:req.params.id}},function(data){
          console.log("deleted from Farm");
        });
      }
  });
});

Product.remove({_id:req.params.id},function(error){
  if (error) {
    console.log(error);
  }else{
    res.render('index');
  }
});

},

updateProduct : function(req,res,next){
  Product.findById({_id:req.params.id},function(error,Product){
    if(error){
      console.log(error);
    }
    else{
      Product.productname = req.body.productname;
      Product.description = req.body.description;
      Product.productorder = req.body.productorder;
      Product.productorgin = req.body.productorgin;
      Product.productcost = req.body.productcost;
      Product.coverimg = url.productimgurl+req.files[0].originalname;
      Product.productimg = url.productimgurl+req.files[1].originalname;
      Product.save(function(error,ModifiedProduct){
        if(error){
          console.log(error);
        }else {
          res.render('index');
        }

      });
    }
  });
},


findProduct : function(req,res,next){
  Product.find({}).sort({}).exec( function(error,Product){
    if (error) {
      console.log(error);
    }
    else{
      res.send(Product);
    }
  });
},

findMainProduct : function(req,res,next){
  Product.find({"formainpage":"true"}).sort({}).exec( function(error,Product){
    if (error) {
      console.log(error);
    }
    else{
      res.send(Product);
    }
  });
},


findProductForWeb : function(req,res,next){
  Product.find({}).sort({}).exec( function(error,Product){
    if (error) {
      console.log(error);
    }
    else{
      console.log(Product);
    }
    res.render('findproduct',{title:"Product",product:Product});
  });
},

findProductForAuth : function(req,res,next){
  console.log(req.params.name);
    Product.findOne({productname:req.params.name}, function(error,Product){
    if (error) {
      console.log(error);
    }
    console.log(Product);
    if(Product == null) {
      console.log('false');
      res.json({result:"false"});
    }
    else{
      console.log('sucess');
      res.json({result:"true"});
    }
  });
},

findOneProduct : function(req,res,next){
  var contentId = req.params.id;
  Product.findOne({_id:contentId},function(error,Product){
    if (error) {
      console.log(error);
    }
    else{
      console.log(Product);
    }
    res.render('detailfindproduct',{title:"Product",product:Product});
  });
},

findOneProductForMobile : function(req,res,next){
  var contentId = req.params.id;
  Product.findOne({_id:contentId},function(error,Product){
    if (error) {
      console.log(error);
    }
    else{
      console.log(Product);
    }
    res.send(Product);
  });
},
findMainPageProduct:function(req,res){
  console.log("findMainPageProduct");
  Product.find({"formainpage":"true"}).sort({}).exec(function(error,Product){
    if(error){
      console.log(error);
    }else{
      res.render('findproductformain',{title:"Product",product:Product});
    }
  });
},

findOneProductMainPage:function(req,res){
  var contentId = req.params.id;
  Product.findOne({_id:contentId},function(error,Product){
    if (error) {
      console.log(error);
    }
    else{
      res.render('detailfindproductmain',{title:"Product",product:Product});
    }
  });
},



};
