const config = require("../config/auth.config");
const db = require("../models");
const PlaceTouristic =require("../models/placeTouristic.model")
const User = db.user;
const Role = db.role;
const notifServices = require("../services/notif.services")

var jwt = require("jsonwebtoken");
var bcrypt = require("bcryptjs");
const placeTouristicModel = require("../models/placeTouristic.model");

exports.save = async (req, res) => {
  const place = new PlaceTouristic({
    placeName: req.body.placeName,
    description: req.body.description,
    types: req.body.types,
    images: req.body.images,
    latitude: req.body.latitude,
    longitude: req.body.longitude
  });

  place.save()
    .then(() => {
        User.find().then((users)=>{
            const fcmTokens = users.map((user) => user.fcmToken);
            // notifServices.sendNotificationToUsers(fcmTokens,"NEW Place To Visite",'"${placeName}" is our new place and you can visite it now');
            return res.status(200).send({ message: 'Place was registered successfully!' });
        }).catch((err)=>{
            return res.status(500).send({ message: err.message });
        })
    })
    .catch((err) => {
        return res.status(500).send({ message: err.message });
    });
};

exports.all = async (req,res) =>{
  placeTouristicModel.find().then((result)=>{
    return res.status(200).send(result);
  }).catch((err)=>{
    return res.status(500).send({ message: err.message });
  });
};

exports.findeById = async (req,res) => {
    const id = req.params.id;
    placeTouristicModel.findById(id).then((result)=>{
        if(!result){
            return res.status(404).send({ message: 'Place Not found.' });
        }
        return res.status(200).send(result);
    }).catch((err)=>{
        return res.status(500).send({ message: err.message });
    });
};

exports.update = async (req,res) => {

    const id = req.params.id;
    const {placeName ,description,types ,images,latitude,longitude}= req.body;

    placeTouristicModel.findByIdAndUpdate(id,{
        placeName,
        description,
        types,
        images,
        latitude,
        longitude
    }).then((result)=>{
        if(!result){
            return res.status(404).send({ message: 'Place Not found.' });
        }
        return res.status(200).send({ message:"update successfuly"});
    }).catch((err)=>{
        return res.status(500).send({ message: err.message });
    });
};


exports.delete = async (req,res) => {
    placeTouristicModel.findByIdAndRemove(req.body.id).then((result)=>{
        if(!result){
            return res.status(404).send({ message: 'Place Not found.' });
        }
        return res.status(200).send({ message:"Place deleted"});
    }).catch((err)=>{
        return res.status(500).send({ message: err.message });
    });
};
