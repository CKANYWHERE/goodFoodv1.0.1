var mongoose = require('mongoose');
var User = require('../Models/User');
var Product = require('../Models/Product');
var async = require('async');

module.exports = {

insertMember : function(req, res, next){
  var newStudent = new User({
  name:req.body.name,
  age:req.body.age,
  id:req.body.id,
  password:req.body.password,
  });

  var kakaoUser = new User({
    name:req.body.name,
    id:req.body.number
  });

  if(req.body.number == null){
    newStudent.save(function(error, data){
      if (error) {
        console.log(error);
      }
      else{
      res.send({message:"sucess",success:true});
      }
    });
  }else{
    User.findOne({id:req.body.number},function(error,User){
      if(error){
        console.log(error);
      }
      console.log(User);
        if(User == null){
          console.log(kakaoUser);
          kakaoUser.save(function(error, data){
            if (error) {
              console.log(error);
            }
            else{
            res.json({message:"sucess",success:true});
            }
          });
        }
        else{
          res.json({message:"LogedOn",success:true});
        }
    });
  }

},

deleteMemberWeb :function(req,res,next){
  var contentId = req.params.id;
  User.remove({_id:contentId},function(error){
    if(error){
      console.log(error);
    }else {
      res.render('index');
    }
  });
},

deleteMember :function(req,res,next){
  User.remove({id:req.body.id},function(error){
    if(error){
      console.log(error);
    }else {
      res.send('sucess');
    }
  });
},

updateMemberWeb : function(req,res,next){
  User.findById({_id:req.params.id},function(error,User){
    if(error){
      console.log(error);
    }
    else{
      User.name = req.body.name;
      User.age = req.body.age;
      User.id = req.body.id;
      User.password = req.body.password;
      User.save(function(error,ModifiedUser){
        if(error){
          console.log(error);
        }else {
        res.send('sucess');
        }
      });
    }
  });
},

updateMember : function(req,res,next){
  User.findById({id:req.body.id},function(error,User){
    if(error){
      console.log(error);
    }
    else{
      User.name = req.body.name;
      User.age = req.body.age;
      User.id = req.body.id;
      User.password = req.body.password;
      User.save(function(error,ModifiedUser){
        if(error){
          console.log(error);
        }else {
        res.send('sucess');
        }
      });
    }
  });
},

findMemberForWeb : function(req,res,next){
  User.find({}).sort({}).exec(function(error,User){
    if (error) {
      console.log(error);
    }
    else{
      console.log(User);
    }
  res.render('findnormaluser',{title:"User",user:User});
  });
},

findMember : function(req,res,next){
  User.findOne({id:req.body.id,password:req.body.password},function(error,User){
    console.log(User);
    if (User == null) {
      res.send({message:"아이디와 비밀번호를 확인해주세요",success:false});
    }
    else{
      res.send({message:"Authorized",success:true,username:User.name,oid:User._id});
    }
  });
},

findOneMemberForAuth : function(req,res,next){
  var contentId = req.params.id;
  User.findOne({id:contentId},function(error,User){
    if (error) {
      console.log(error);
    }
    console.log(User);
    if(User == null) {
      console.log('false');
      res.json({result:"false"});
    }
    else{
      console.log('sucess');
      res.json({result:"true"});
    }
  });
},

findOneMember : function(req,res,next){
  var contentId = req.params.id;
  User.findOne({_id:contentId},function(error,User){
    if (error) {
      console.log(error);
    }
    else{
      console.log(User);
    }
    res.render('detailfindnormaluser',{title:"User",user:User});
  });
},

addShoppingCart : function(req,res){
  Product.findOne({_id:req.body.product_id},function(error,Product){
    if(Product == null){
      res.send({success:false});
    }else{
      User.findOne({'id':req.body.user_id,shoppingcart:{$elemMatch:{product:Product._id}}},function(error,shoppingcart){
        console.log(shoppingcart);
        if(shoppingcart == null){
          User.findOne({id:req.body.user_id},function(error,User){
            User.update({$addToSet:{shoppingcart:{product:Product._id}}},function(data){
              res.send({success:true});
          });
        });
        }else{
          res.send({success:false});
        }
      });
    }
  });
},

findShoppingCart : function(req,res,next){
      var id = req.params.id;

      if(id == 'null'){
        res.send({success:false});
      }

      else{
        User.findOne({id:id},function(error,User){
          if(error){
            console.log(error);
          }
          else{
            if(User.shoppingcart == null){
                res.send({"Products":[]});
            }

          var product = [];
          var shoppingcart = [];
          var product_id = [];
          var num = [];
          var i = 0;

           async.each(User.shoppingcart,function(data,callback){
              product_id.push({
                _id:data.product
              });
              num.push({
                num:data.num
              });
              shoppingcart.push({
                _id:data._id
              });
              callback(null);
            });

            var j = 0;
            async.each(product_id,function(data,callback){
                Product.findById({_id:product_id[j]._id},function(error,Product){
                  product.push(Product);
                  callback(null);
                });
                j++;
              },function(error){
                if(error){
                  console.log(error);
                }else{
                  console.log({product:product,num:num,shoppingcart:shoppingcart});
                  res.json({product:product,num:num,shoppingcart:shoppingcart});
                }
              });
          }
        });
      }

},

deleteShoppingCart:function(req,res){
  User.findOne({id:req.body.user_id},function(error,User2) {
    User2.update({$pull:{shoppingcart:{product:req.body.product_id,num:req.body.num}}},function(data){
      console.log(req.body.product_id);
      res.send({success:true});
    });
  });
},

updateShoppingCart:function(req,res){
   User.update({id:req.body.user_id,"shoppingcart._id":req.body.shoppingcart_id},
        {$set:{"shoppingcart.$.num":req.body.num}},function(error,data){
          if(error){
            console.log(error);
            res.send({success:false});
          }else{
            console.log(data);
            res.send({success:true});
          }
    });

},
};
