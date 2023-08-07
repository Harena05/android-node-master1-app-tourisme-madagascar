const express = require('express');
const authJwt = require("../middlewares/authJwt");
const placeTouristicController = require("../controllers/placeTouristic.controller");

const router = express.Router();

router.use(function(req, res, next) {
    res.header(
      "Access-Control-Allow-Headers",
      "Origin, Content-Type, Accept"
    );
    next();
  });

router.post("/save",[authJwt.verifyToken, authJwt.isModerator],placeTouristicController.save);

router.get("/all",[authJwt.verifyToken], placeTouristicController.all);

router.get("/:id",[authJwt.verifyToken],placeTouristicController.findeById);

router.put("/:id",[authJwt.verifyToken, authJwt.isModerator],placeTouristicController.update);

router.delete("/:id",[authJwt.verifyToken, authJwt.isAdmin],placeTouristicController.delete);

module.exports = router;
