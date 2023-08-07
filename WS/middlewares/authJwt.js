const jwt = require("jsonwebtoken");
const config = require("../config/auth.config.js");
const db = require("../models");
const User = db.user;
const Role = db.role;

verifyToken = (req, res, next) => {
  let token = req.session.token;

  if (!token) {
    return res.status(403).send({ message: "No token provided!" });
  }

  jwt.verify(token,
            config.secret,
            (err, decoded) => {
              if (err) {
                return res.status(401).send({
                  message: "Unauthorized!",
                });
              }
              req.userId = decoded.id;
              next();
            });
};

const isAdmin = (req, res, next) => {
  User.findById(req.userId)
    .exec()
    .then((user) => {
      if (!user) {
        return res.status(404).send({ message: 'User not found.' });
      }

      return Role.find({ _id: { $in: user.roles } }).exec();
    })
    .then((roles) => {
      if (!roles.some((role) => role.rolename === 'admin')) {
        return res.status(403).send({ message: 'Require Admin Role!' });
      }

      next();
    })
    .catch((err) => {
      res.status(500).send({ message: err.message });
    });
};


const isModerator = (req, res, next) => {
  User.findById(req.userId)
    .exec()
    .then((user) => {
      if (!user) {
        return res.status(404).send({ message: 'User not found.' });
      }

      return Role.find({ _id: { $in: user.roles } }).exec();
    })
    .then((roles) => {
      if (!roles.some((role) => role.rolename === 'moderator')) {
        return res.status(403).send({ message: 'Require Moderator Role!' });
      }

      next();
    })
    .catch((err) => {
      res.status(500).send({ message: err.message });
    });
};


const authJwt = {
  verifyToken,
  isAdmin,
  isModerator,
};

module.exports = authJwt;