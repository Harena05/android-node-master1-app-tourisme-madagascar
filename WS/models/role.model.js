const mongoose = require('mongoose');

const roleSchema = new mongoose.Schema({
  rolename: { type: String, required: true, unique: true },
});

module.exports = mongoose.model('Role', roleSchema);