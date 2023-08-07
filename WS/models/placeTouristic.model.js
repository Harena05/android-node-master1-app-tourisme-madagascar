const mongoose = require('mongoose');

const placeTouristicSchema = new mongoose.Schema({
  placeName: { type: String, required: true, unique: true },
  description: { type: String, required: true },
  types : [{ type: String}],
  images : [{ type: String}],
  latitude : { type: Number},
  longitude : { type: Number}
});

module.exports = mongoose.model('PlaceTouristic', placeTouristicSchema);