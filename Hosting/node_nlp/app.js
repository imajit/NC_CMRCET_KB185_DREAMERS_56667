var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var PORT = process.env.PORT || 3023;

var indexRouter = require('./routes/index');
//var nlpRouter = require('./routes/nlp');

var app = express();

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
const nlpRouter = require('./routes/nlp')
app.use('/nlp',nlpRouter)
//app.use('/api/nlp', nlpRouter);

app.listen(PORT,() => console.log('Server Started'))
module.exports = app;