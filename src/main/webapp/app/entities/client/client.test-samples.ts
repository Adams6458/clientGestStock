import { IClient, NewClient } from './client.model';

export const sampleWithRequiredData: IClient = {
  id: 16810,
};

export const sampleWithPartialData: IClient = {
  id: 6246,
  codeAgence: 'ouille équipe',
  nom: 'au-dessous de bien que en faveur de',
  sexe: 'aussitôt que trancher',
  titre: 'raide',
  piece: 'large',
  reference: 'longtemps',
};

export const sampleWithFullData: IClient = {
  id: 9277,
  codeAgence: 'assez',
  telephone: '+33 370527332',
  nom: 'si bien que lentement reconnaître',
  prenom: 'jusqu’à ce que neutre turquoise',
  sexe: 'même si à moins de prestataire de services',
  titre: 'miam énergique',
  piece: 'à condition que',
  reference: 'admirablement secours',
  autoriteDelivre: 'cot cot',
  typeClient: 'guide biathlète pendant',
};

export const sampleWithNewData: NewClient = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
