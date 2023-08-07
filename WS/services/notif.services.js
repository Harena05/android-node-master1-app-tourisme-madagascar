const admin = require('firebase-admin');

// Charger les informations de configuration depuis le fichier serviceAccountKey.json téléchargé depuis Firebase
const serviceAccount = require('../projet-finale-android-firebase-adminsdk-5b9bd-13582404a6.json');

// Initialiser Firebase Admin
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});


// Envoyer une notification à un utilisateur spécifique en utilisant son FCM token
async function sendNotificationToUser(userId, title, body) {
    try {
      // Récupérer le FCM token de l'utilisateur depuis la base de données (vous devrez avoir stocké le token lors de l'inscription ou la connexion)
      const user = await User.findById(userId);
      const fcmToken = user.fcmToken;
  
      // Définir le message de notification
      const message = {
        token: fcmToken,
        notification: {
          title: title,
          body: body,
        },
      };
  
      // Envoyer la notification à l'utilisateur spécifié
      const response = await admin.messaging().send(message);
      console.log('Notification envoyée avec succès :', response);
      return response;
    } catch (error) {
      console.error('Erreur lors de l\'envoi de la notification :', error);
      throw error;
    }
  }

// Envoyer une notification à un sujet (topic) spécifique (tous les utilisateurs abonnés au sujet recevront la notification)
async function sendNotificationToUsers(fcmTokens, title, body) {
    try {
      // Définir le message de notification
      const message = {
        // topic: topic,
        notification: {
          title: title,
          body: body,
        },
      };
  
    //   const fcmTokens = [
    //     'token_android_device_1',
    //     'token_android_device_2',
    //   ];

      // Envoyer la notification au sujet spécifié
    //   const response = await admin.messaging().send(message);



      const response =await admin.messaging().sendToDevice(fcmTokens, message);

      console.log('Notification envoyée avec succès :', response);
      return response;
    } catch (error) {
      console.error('Erreur lors de l\'envoi de la notification :', error);
      throw error;
    }
  }
  
  const notif = {
    sendNotificationToUser,
    sendNotificationToUsers,
  };

  module.exports = notif;
  
  