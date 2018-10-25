var usercontroller = require('../Controller/usercontroller');
var express = require('express');
var app = express();

module.exports = function () {
    app.post('/success', usercontroller.insertMember);
    app.get('/web', usercontroller.findMemberForWeb);
    app.post('/loginMobile', usercontroller.findMember);
    app.get('/view/:id',usercontroller.findOneMember);
    app.post('/view/delete/:id',usercontroller.deleteMemberWeb);
    app.delete('/',usercontroller.deleteMember);
    app.post('/view/update/:id',usercontroller.updateMemberWeb);
    app.put('/',usercontroller.updateMember);
    app.post('/auth/:id',usercontroller.findOneMemberForAuth);
    app.post('/addcart',usercontroller.addShoppingCart);
    app.get('/shoppingcart/:id',usercontroller.findShoppingCart);
    app.post('/deletecart',usercontroller.deleteShoppingCart);
    app.post('/numcart',usercontroller.updateShoppingCart);
    app.get('/create',function(req,res,next){
      res.render('insertuser');
    });
    return app;
};
