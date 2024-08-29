import { IUser } from 'app/entities/user/user.model';

export interface IRapport {
  id: number;
  userID?: string | null;
  message?: string | null;
  codeErr?: string | null;
  textExpl?: string | null;
  errcode?: string | null;
  transactionId?: string | null;
  user?: Pick<IUser, 'id'> | null;
}

export type NewRapport = Omit<IRapport, 'id'> & { id: null };
