import { IUser } from 'app/entities/user/user.model';
import { IClient } from 'app/entities/client/client.model';

export interface ITransaction {
  id: number;
  userID?: string | null;
  code?: string | null;
  message?: string | null;
  transactionId?: string | null;
  montant?: number | null;
  user?: Pick<IUser, 'id'> | null;
  client?: Pick<IClient, 'id'> | null;
}

export type NewTransaction = Omit<ITransaction, 'id'> & { id: null };
