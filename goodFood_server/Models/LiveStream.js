var mongoose = require('mongoose');
var Schema = mongoose.Schema;


var LiveStreamSchema = new Schema({
  coverimg:{type:String},
  stream_name:{type:String},
  farm_name:{type:String},
  product_name:{type:String},
  cost:{type:Number},
  productimg:{type:String},
  stream_url:{type:String},
  chat_url:{type:String},
});

var LiveStream = mongoose.model('LiveStream',LiveStreamSchema);
module.exports = LiveStream;
