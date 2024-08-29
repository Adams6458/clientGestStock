import { IUser } from 'app/entities/user/user.model';

export interface IClient {
  id: number;
  codeAgence?: string | null;
  telephone?: string | null;
  nom?: string | null;
  prenom?: string | null;
  sexe?: string | null;
  titre?: string | null;
  piece?: string | null;
  reference?: string | null;
  autoriteDelivre?: string | null;
  typeClient?: string | null;
  user?: Pick<IUser, 'id'> | null;
}

export type NewClient = Omit<IClient, 'id'> & { id: null };
