const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
  username: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  email : { type: String},
  firstName : { type: String},
  lastName : { type: String},
  roles:[{
    type: mongoose.Schema.Types.ObjectId,
    ref: "Role"
  }],
  fcmToken: { type: String}

});

module.exports = mongoose.model('User', userSchema);
