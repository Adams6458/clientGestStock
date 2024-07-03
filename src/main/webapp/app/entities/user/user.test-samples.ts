import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 25161,
  login: 'wZqI+m@DwsK8',
};

export const sampleWithPartialData: IUser = {
  id: 28560,
  login: 'KBn@U\\{CL7\\vW00l\\)PWa\\\\QTcX',
};

export const sampleWithFullData: IUser = {
  id: 15116,
  login: '1OR9B',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
