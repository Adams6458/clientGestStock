import { IRapport, NewRapport } from './rapport.model';

export const sampleWithRequiredData: IRapport = {
  id: 3636,
};

export const sampleWithPartialData: IRapport = {
  id: 2915,
  message: 'à condition que',
  transactionId: 'au point que',
};

export const sampleWithFullData: IRapport = {
  id: 32472,
  userID: 'en faveur de',
  message: 'fonctionnaire absolument',
  codeErr: 'si modifier croâ',
  textExpl: 'pourvu que combien',
  errcode: 'aimable de manière à ce que ailleurs',
  transactionId: 'touchant',
};

export const sampleWithNewData: NewRapport = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
