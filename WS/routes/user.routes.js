const authJwt = require("../middlewares/authJwt");
const controller = require("../controllers/user.controller");
const express = require('express');
// const authController = require('../controllers/user.controller');

const router = express.Router();

// // Registration
// router.post('/register', authController.register);

// // Login
// router.post('/login', authController.login);



// module.exports = function(app) {
  router.use(function(req, res, next) {
    res.header(
      "Access-Control-Allow-Headers",
      "Origin, Content-Type, Accept"
    );
    next();
  });

  router.get("/all", controller.allAccess);

  router.get("/user", [authJwt.verifyToken], controller.userBoard);

  router.get(
    "/mod",
    [authJwt.verifyToken, authJwt.isModerator],
    controller.moderatorBoard
  );

  router.get(
    "/admin",
    [authJwt.verifyToken, authJwt.isAdmin],
    controller.adminBoard
    );
// };
    module.exports = router;