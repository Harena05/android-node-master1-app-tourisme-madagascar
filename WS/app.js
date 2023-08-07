const express = require('express');
const mongoose = require('mongoose');
const cookieSession = require('cookie-session');
const userRoutes = require('./routes/user.routes');
const roleModel = require("./models/role.model");
const authRoutes = require('./routes/auth.routes');
const screenRoutes = require('./routes/screen.routes');
const path = require('path');
const placeTouristicRoutes = require('./routes/placeTouristic.routes');

const app = express();

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.use(
  cookieSession({
    name: "123456",
    keys: ["COOKIE_SECRET"], // should use as secret environment variable
    httpOnly: true
  })
);
app.use(express.static(path.join(__dirname, 'public')));

// Charger les variables d'environnement
require('dotenv').config();

// Récupérer les informations de connexion en fonction de l'environnement
const dbURI = process.env.STATUS === 'prod'
  ? process.env.DB_CONNECTION_PROD
  : process.env.DB_CONNECTION_DEV;

  // const db = require("./app/models/db");
// Connexion à MongoDB
// const dbURI = `mongodb://${dbUser}:${dbPass}@${dbHost}/${dbName}`;
mongoose.connect(dbURI, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
})
  .then(() => {
    console.log('MongoDB connected');
    initial();
  })
  .catch(err => console.error('MongoDB connection error:', err));

// Le reste de votre configuration Express et de vos routes...


app.use('/user', userRoutes);
app.use('/auth',authRoutes);
app.use('/screen',screenRoutes);
app.use('/place',placeTouristicRoutes);

// require('./app/routes/auth.routes')(app);
// require('./app/routes/user.routes')(app);


function initial() {
  roleModel.estimatedDocumentCount().then((count)=>{
    if(count ===0){
      new roleModel({
        rolename: "user"
      }).save().then(()=>{
        console.log("added 'user' to roles collection");
      }).catch((err)=>{
        console.log("error", err);
      });

      new roleModel({
        rolename: "moderator"
      }).save().then(()=>{
        console.log("added 'moderator' to roles collection");
      }).catch((err)=>{
        console.log("error", err);
      });

      new roleModel({
        rolename: "admin"
      }).save().then(()=>{
        console.log("added 'admin' to roles collection");
      }).catch((err)=>{
        console.log("error", err);
      });
    }
  })
  .catch((err)=>{
    new roleModel({
      rolename: "user"
    }).save().then(()=>{
      console.log("added 'user' to roles collection");
    }).catch((err)=>{
      console.log("error", err);
    });

    new roleModel({
      rolename: "moderator"
    }).save().then(()=>{
      console.log("added 'moderator' to roles collection");
    }).catch((err)=>{
      console.log("error", err);
    });

    new roleModel({
      rolename: "admin"
    }).save().then(()=>{
      console.log("added 'admin' to roles collection");
    }).catch((err)=>{
      console.log("error", err);
    });
  });
}

// Démarrer le serveur
const port = process.env.PORT || 3000;
app.listen(port, () => {
  console.log(`Serveur en cours d'exécution sur le port ${port}`);
});


