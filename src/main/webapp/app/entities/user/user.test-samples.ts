import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 5144,
  login: 'Jq*_@UhK',
};

export const sampleWithPartialData: IUser = {
  id: 11131,
  login: 'l',
};

export const sampleWithFullData: IUser = {
  id: 6830,
  login: 'z',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
