import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '4366cf90-c322-4be4-acec-9cfb0df8e39e',
};

export const sampleWithPartialData: IAuthority = {
  name: 'd0f98c8e-9677-46e2-a33e-e9ad6cea5687',
};

export const sampleWithFullData: IAuthority = {
  name: '35b7c15d-3520-4065-938a-0f34021f8691',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
