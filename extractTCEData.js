#!/usr/bin/env node
var http = require('http');
var fs = require('fs');
var xpath = require('xpath');
var parse5 = require('parse5');
var xmlser = require('xmlserializer');
var dom = require('xmldom').DOMParser;
//use sync request to avoid killing the remote service
var request = require('sync-request');
var async = require("async");

var FROM_ID = 10000;
var TO_ID = 15000;
var FIELD_DELIM = "#;#";
var FILE_NAME = "./data/" + "FROM-"+ FROM_ID + "-TO-" + TO_ID + ".csv";
var FAILED_FILE_NAME = "./data/" + "FAILS-FROM-"+ FROM_ID + "-TO-" + TO_ID + ".csv";
var MISSING_FILE_NAME = "./data/" + "MISSING-FROM-"+ FROM_ID + "-TO-" + TO_ID + ".csv";

//create a queue object with concurrency 2
var queue = async.queue(async.ensureAsync(function (ID, callback) {
	try {
		response = request('GET', 'http://servicos.tce.pr.gov.br/TCEPR/Municipal/Obras/Detalhes/'+ID);
		if (response.statusCode == 200) {
			line = "";
			document = parse5.parse(response.getBody().toString());
			xhtml = xmlser.serializeToString(document);
			doc = new dom().parseFromString(xhtml);
			select = xpath.useNamespaces({"x": "http://www.w3.org/1999/xhtml"});
			nodes = select("//x:div", doc);
			for (i=0; i<nodes.length; i++) {
				for (j=0; j<nodes[i].attributes.length; j++)
					if (nodes[i].attributes[j].nodeName = "class" && nodes[i].attributes[j].nodeValue.startsWith("form-group")) {
						for (k=0; k<nodes[i].childNodes.length; k++) {
							if (nodes[i].childNodes[k].tagName == "input") {
								for (l=0;l<nodes[i].childNodes[k].attributes.length; l++) {
									if (nodes[i].childNodes[k].attributes[l].nodeName == "value") {
										console.log("##############");
										console.log(nodes[i].childNodes[k].attributes[l].nodeValue);
										line += nodes[i].childNodes[k].attributes[l].nodeValue.trim() + FIELD_DELIM;
										console.log("##############");
									}
								}
							}
							else if (nodes[i].childNodes[k].tagName == "textarea") {
//								for (l=0;l<nodes[i].childNodes[k].attributes.length; l++) {
//								if (nodes[i].childNodes[k].attributes[l].nodeName == "value") {
								console.log("##############");
								console.log(nodes[i].childNodes[k].firstChild.nodeValue);
								line += nodes[i].childNodes[k].firstChild.nodeValue.trim() + FIELD_DELIM;
								console.log("##############");
//								}
//								}
							}
						}
					}
			}

			if (line.length > 0) {

				//Request aditional data to get position;
				response = request('GET', 'http://servicos.tce.pr.gov.br/TCEPR/Municipal/Obras/ObraDetalhes/CarregarBem?idIntervencao='+ID);
				if (response.statusCode == 200) {
					document = parse5.parse(response.getBody().toString());
					xhtml = xmlser.serializeToString(document);
					doc = new dom().parseFromString(xhtml);
					select = xpath.useNamespaces({"x": "http://www.w3.org/1999/xhtml"});
					lines = select("//x:tbody/x:tr", doc);
					for (i=0;i<lines.length;i++) {
						(function (ID, line, cells) {
							for (k=0; k<cells.length; k++) {
								if (k != 7 && (!cells[k].nodeName || cells[k].nodeName != "td" || !cells[k].firstChild || !cells[k].firstChild.data)) {
									continue;
								}
								console.log("###");
								if (k == 7) { // this is the geo position we need to transform do decimal degrees
									try {
										console.log(cells[k].firstChild.data);
										latlon = cells[k].firstChild.data.split("/");
										lat = latlon[0];
										lon = latlon[1];
										if (lat && lon) {
											lat = lat.split("\u00B0");
											lon = lon.split("\u00B0");
											lat = -1*(parseFloat(lat[0])+parseFloat(lat[1].split("'")[0])/60+parseFloat(lat[1].split("'")[1])/3600);
											lon = -1*(parseFloat(lon[0])+parseFloat(lon[1].split("'")[0])/60+parseFloat(lon[1].split("'")[1])/3600);
											line += lat + FIELD_DELIM + lon;
										}
										else
											throw new Error("Missing data");
									}
									catch (err) {
										line += "0" + FIELD_DELIM + "0";
										console.log("Failed to parse position" + cells[k].firstChild.data + " For ID:" + ID);
									}
									break;
								}
								else {
									console.log(cells[k].firstChild.data);
									line += cells[k].firstChild.data.trim() + FIELD_DELIM;
								}
								console.log("###");
							}
							fs.appendFileSync(FILE_NAME, ID+line.trim().split("\n").join(" ") +"\n");
						}(ID + "-" + i + FIELD_DELIM, line, lines[i].childNodes));
					}
				}
			}
			else {
				console.log("Missing ID:" + ID);
				fs.appendFileSync(MISSING_FILE_NAME, ID +"\n");
			}
		}
		else {
			console.log("Failed ID:" + ID);
			fs.appendFileSync(FAILED_FILE_NAME, ID +"\n");
		}
	}
	catch (err){
		console.log("Failed ID:" + ID + "\n" + err);
		fs.appendFileSync(FAILED_FILE_NAME, ID +"\n");
	}
	callback();
}), 2);

//assign a callback
queue.drain = function() {
	console.log('all items have been processed');
}

//var queue = async.queue(workerDownload, 2);
//queue.drain = function() {
//console.log("All Data Donwloaded");
//};

for (ID = FROM_ID; ID < TO_ID; ID++) {
	queue.push(ID, function (err) {
		console.log('finished processing');
	});
}

function downloadData (ID) {
}