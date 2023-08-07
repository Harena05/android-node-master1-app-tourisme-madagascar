const express = require('express');
const path = require('path');

splache_screen = () => {
    return express.static(path.join(__dirname,'public'));
};
const screenServices ={
  splache_screen,  
}
module.exports = screenServices;