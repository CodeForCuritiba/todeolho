var express = require('express');
var router = express.Router();

var mysql      = require('mysql');
var pool  = mysql.createPool({
//	host     : 'localhost',
//	user     : 'corrupcao',
//	password : 'pa$$Word',
//	database : 'corrupcao'
	host     : 'us-cdbr-iron-east-03.cleardb.net',
	user     : 'bab3fd69323e0a',
	password : '708bd041',
	database : 'heroku_700bc4349db223c'
});

/* GET users listing. */
router.get('/', function(req, res, next) {
	
	console.log(req.query);
	NELat = req.query.NELatLon.split(",")[0];
	NELon = req.query.NELatLon.split(",")[1];
	SWLat = req.query.SWLatLon.split(",")[0];
	SWLon = req.query.SWLatLon.split(",")[1];

	pool.getConnection(function(err, connection) {
		connection.query('SELECT id, nome_da_obra, inicio, data_estimada_termino, situacao, lat, lon from obras where lat <= ? and lat >= ? and lon <= ? and lon >= ?',  [NELat, SWLat, NELon, SWLon], function(err, rows, fields) {
			if (err) throw err;
			console.log('Got from DB', rows[0]);
			res.send(rows);
			connection.release();
		});
	});
});

module.exports = router;
