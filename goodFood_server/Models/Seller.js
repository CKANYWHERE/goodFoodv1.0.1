var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var SellerSchema = new Schema({
  name: {type: String,},
  age: {type:Number, 'default': 0},
  id: {type: String,},
  password: {type: String, index: 'hashed'},
  usernumber: String
});

var Seller = mongoose.model('Seller',SellerSchema);
module.exports = Seller;
