const express = require("express");
const screenController = require("../controllers/screen.controller")

const router = express.Router();

router.get("/splash_screen", screenController.splache_screen);

module.exports = router