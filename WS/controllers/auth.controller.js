const config = require("../config/auth.config");
const db = require("../models");
const User = db.user;
const Role = db.role;

var jwt = require("jsonwebtoken");
var bcrypt = require("bcryptjs");

exports.signup = (req, res) => {
  const user = new User({
    username: req.body.username,
    email: req.body.email,
    firstName:req.body.firstName,
    lastName:req.body.lastName,
    fcmToken:req.body.fcmToken,
    password: bcrypt.hashSync(req.body.password, 8),
  });

  user.save()
    .then((savedUser) => {
      if (req.body.roles) {
        return Role.find({ rolename: { $in: req.body.roles } })
          .then((roles) => {
            savedUser.roles = roles.map((role) => role._id);
            return savedUser.save();
          });
      } else {
        return Role.findOne({ rolename: 'user' })
          .then((role) => {
            savedUser.roles = [role._id];
            return savedUser.save();
          });
      }
    })
    .then(() => {
      res.send({ message: 'User was registered successfully!' });
    })
    .catch((err) => {
      res.status(500).send({ message: err.message });
    });
};


exports.signin = (req, res) => {
  User.findOne({ username: req.body.username })
    .populate('roles', '-__v')
    .exec()
    .then((user) => {
      if (!user) {
        return res.status(404).send({ message: 'User Not found.' });
      }

      const passwordIsValid = bcrypt.compareSync(req.body.password, user.password);

      if (!passwordIsValid) {
        return res.status(401).send({ message: 'Invalid Password!' });
      }

      const token = jwt.sign({ id: user.id }, config.secret, {
        algorithm: 'HS256',
        allowInsecureKeySizes: true,
        expiresIn: 86400, // 24 hours
      });

      const authorities = user.roles.map((role) => 'ROLE_' + String(role.rolename).toUpperCase());

      req.session.token = token;

      res.status(200).send({
        id: user._id,
        username: user.username,
        email: user.email,
        roles: authorities,
      });
    })
    .catch((err) => {
      res.status(500).send({ message: err.message });
    });
};


exports.signout = async (req, res) => {
  try {
    req.session = null;
    return res.status(200).send({ message: "You've been signed out!" });
  } catch (err) {
    this.next(err);
  }
};