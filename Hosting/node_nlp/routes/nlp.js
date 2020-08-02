const express = require('express');
const aposToLexForm = require('apos-to-lex-form');
const natural = require('natural');

const router = express.Router();

router.post('/', function(req, res, next) {
  const { review } = req.body;
  const lexedReview = aposToLexForm(review);
  const casedReview = lexedReview.toLowerCase();
  const alphaOnlyReview = casedReview.replace(/[^a-zA-Z\s]+/g, '');
  const { WordTokenizer } = natural;
  const tokenizer = new WordTokenizer();
  const tokenizedReview = tokenizer.tokenize(alphaOnlyReview);
});