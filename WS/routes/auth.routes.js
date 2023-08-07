const express = require('express');
const verifySignUp = require("../middlewares/verifySingUp");
const authController = require("../controllers/auth.controller");
// const authController = require('../controllers/user.controller');

const router = express.Router();

// module.exports = function(app) {
  router.use(function(req, res, next) {
    res.header(
      "Access-Control-Allow-Headers",
      "Origin, Content-Type, Accept"
    );
    next();
  });

  router.post(
    "/signup",
    [
      verifySignUp.checkDuplicateUsernameOrEmail,
      verifySignUp.checkRolesExisted
    ],
    authController.signup
  );

  router.post("/signin", authController.signin);

  router.post("/signout", authController.signout);

  module.exports = router;
// };