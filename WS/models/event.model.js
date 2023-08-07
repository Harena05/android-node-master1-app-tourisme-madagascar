const mongoose = require('mongoose');

const eventShema = new mongoose.Schema({
  title: { type: String, required: true},
  description: { type: String, required: true },
  date : { type: String},
  place:[{
    type: mongoose.Schema.Types.ObjectId,
    ref:"PlaceTouristic"
  }]
});

module.exports = mongoose.model('Event', eventShema);