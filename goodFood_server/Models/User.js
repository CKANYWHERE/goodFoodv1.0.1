var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var UserSchema = new Schema({
  name: {type: String},
  age: {type:String, 'default': 0},
  id: {type: String},
  password: {type: String, index: 'hashed'},
  usernumber: String,
  shoppingcart:[{product:{type:Schema.Types.ObjectId,ref:"Product"},
                num:{type:Number,default:1}}]
});

var User = mongoose.model('User',UserSchema);
module.exports = User;
