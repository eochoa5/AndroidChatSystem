var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);

app.get('/', function(req, res){
	res.sendFile(__dirname+'/index.html');

});

io.on('connection', function(socket){
	console.log('one android user connected: ' + socket.id);

	socket.on('message', function(data){
		var sockets = io.sockets.sockets;

		/*
		sockets.forEach(function(sock){
			if(sock.id != socket.id){
				sock.emit('message', {message:data});

			}
		});
		*/
		socket.broadcast.emit('message', data);


	});

	socket.on('disconnect', function(){
		console.log('one android user disconnected: ' + socket.id);
	});

});

http.listen(process.env.PORT || 3000, function(){
	console.log('server running');
});


