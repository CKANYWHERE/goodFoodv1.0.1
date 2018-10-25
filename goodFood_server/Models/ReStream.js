var mongoose = require('mongoose');
var Schema = mongoose.Schema;


var ReStreamSchema = new Schema({
  coverimg:{type:String},
  stream_name: {type:String},
  farm_name:{type:String},
  product_name : {type:String},
  cost:{type:Number},
  productimg:{type:String},
  stream_url : {type:String},
  date : {type:Date,default:Date.now},
});

var ReStream = mongoose.model('ReStream',ReStreamSchema);
module.exports = ReStream;
