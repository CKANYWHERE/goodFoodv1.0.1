var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var ProductSchema = new Schema({
  productname:{type: String},
  productorgin: String,
  productcost: Number,
  coverimg: String,
  productimg:String,
  formainpage:String
});

var Product = mongoose.model('Product',ProductSchema);
module.exports = Product;
