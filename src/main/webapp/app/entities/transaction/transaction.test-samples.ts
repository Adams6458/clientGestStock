import { ITransaction, NewTransaction } from './transaction.model';

export const sampleWithRequiredData: ITransaction = {
  id: 31167,
};

export const sampleWithPartialData: ITransaction = {
  id: 7515,
  userID: 'combiner sitôt que',
  code: 'élaborer émérite tsoin-tsoin',
  message: 'dense dans la mesure où',
};

export const sampleWithFullData: ITransaction = {
  id: 1599,
  userID: 'broum constater',
  code: 'gens peindre',
  message: 'à la faveur de croâ miaou',
  transactionId: 'prout',
  montant: 8334.33,
};

export const sampleWithNewData: NewTransaction = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
