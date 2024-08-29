import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IClient, NewClient } from '../client.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IClient for edit and NewClientFormGroupInput for create.
 */
type ClientFormGroupInput = IClient | PartialWithRequiredKeyOf<NewClient>;

type ClientFormDefaults = Pick<NewClient, 'id'>;

type ClientFormGroupContent = {
  id: FormControl<IClient['id'] | NewClient['id']>;
  codeAgence: FormControl<IClient['codeAgence']>;
  telephone: FormControl<IClient['telephone']>;
  nom: FormControl<IClient['nom']>;
  prenom: FormControl<IClient['prenom']>;
  sexe: FormControl<IClient['sexe']>;
  titre: FormControl<IClient['titre']>;
  piece: FormControl<IClient['piece']>;
  reference: FormControl<IClient['reference']>;
  autoriteDelivre: FormControl<IClient['autoriteDelivre']>;
  typeClient: FormControl<IClient['typeClient']>;
  user: FormControl<IClient['user']>;
};

export type ClientFormGroup = FormGroup<ClientFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ClientFormService {
  createClientFormGroup(client: ClientFormGroupInput = { id: null }): ClientFormGroup {
    const clientRawValue = {
      ...this.getFormDefaults(),
      ...client,
    };
    return new FormGroup<ClientFormGroupContent>({
      id: new FormControl(
        { value: clientRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      codeAgence: new FormControl(clientRawValue.codeAgence),
      telephone: new FormControl(clientRawValue.telephone),
      nom: new FormControl(clientRawValue.nom),
      prenom: new FormControl(clientRawValue.prenom),
      sexe: new FormControl(clientRawValue.sexe),
      titre: new FormControl(clientRawValue.titre),
      piece: new FormControl(clientRawValue.piece),
      reference: new FormControl(clientRawValue.reference),
      autoriteDelivre: new FormControl(clientRawValue.autoriteDelivre),
      typeClient: new FormControl(clientRawValue.typeClient),
      user: new FormControl(clientRawValue.user),
    });
  }

  getClient(form: ClientFormGroup): IClient | NewClient {
    return form.getRawValue() as IClient | NewClient;
  }

  resetForm(form: ClientFormGroup, client: ClientFormGroupInput): void {
    const clientRawValue = { ...this.getFormDefaults(), ...client };
    form.reset(
      {
        ...clientRawValue,
        id: { value: clientRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ClientFormDefaults {
    return {
      id: null,
    };
  }
}
