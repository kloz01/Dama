import mongoose from 'mongoose';
import config from './config';
import {mapOneOrManyArgs} from "rxjs/internal/util/mapOneOrManyArgs";

const db = async () => {
  await mongoose
    .connect(config.mongoUrl as string)
    .then(() => {
      console.log('Connesso al DB');
    })
    .catch((error) => {
      console.error('Errore nella connessione col DB', error);
    });
};

export default db;
