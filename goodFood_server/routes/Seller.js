var sellercontroller = require('../Controller/sellercontroller');
var express = require('express');
var app = express();

module.exports = function () {
    app.post('/success', sellercontroller.insertSeller);
    app.get('/',  sellercontroller.findSeller);
    app.get('/web',  sellercontroller.findSellerForWeb);
    app.get('/view/:id',sellercontroller.findOneSeller);
    app.post('/view/delete/:id',sellercontroller.deleteSellerWeb);
    app.post('/view/update/:id',sellercontroller.updateSellerWeb);
    app.delete('/', sellercontroller.deleteSeller);
    app.put('/', sellercontroller.updateSeller);
    app.post('/:name',sellercontroller.findSellerForAuth);
    app.get('/create',function(req,res,next){
      res.render('insertseller');
    });
    return app;
};
