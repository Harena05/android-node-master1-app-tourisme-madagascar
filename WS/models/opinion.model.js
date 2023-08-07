const mongoose = require('mongoose');

const opinionSchema = new mongoose.Schema({
    place:{
        type: mongoose.Schema.Types.ObjectId,
        ref: "placeTourictic",
        required: true
    },
    user:{
        type: mongoose.Schema.Types.ObjectId,
        ref: "user",
        required: true
    },
    mark: { type: Number, required: true },
    comment: { type: String, required: true }
});

module.exports = mongoose.model('Opinion', opinionSchema);