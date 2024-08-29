import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '6143ea39-6d5c-4a1d-ba49-a6102cca7ef1',
};

export const sampleWithPartialData: IAuthority = {
  name: '77a2418c-cef8-457d-b142-0eef9f5562c7',
};

export const sampleWithFullData: IAuthority = {
  name: 'c1dbef96-5e5a-4cd3-9f0c-136de8923510',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
