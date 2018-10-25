var mongoose = require('mongoose');
var Schema = mongoose.Schema;


var FarmSchema = new Schema({
  farmname: {type: String},
  address: {type: String},
  coverimg:{type:String},
  farmimg:{type:String},
  formainpage:String,
  product:[{type: Schema.Types.ObjectId,ref:"Product"}]
});

var Farm = mongoose.model('Farm',FarmSchema);
module.exports = Farm;
