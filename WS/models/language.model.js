const mongoose = require('mongoose');

const LanguageSchema = new mongoose.Schema({
  languageName: { type: String, required: true, unique: true },
  symbol: { type: String, unique: true}
});

module.exports = mongoose.model('Language', LanguageSchema);