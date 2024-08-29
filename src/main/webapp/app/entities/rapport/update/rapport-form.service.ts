import { inject, Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRapport, NewRapport } from '../rapport.model';
import { AccountService } from 'app/core/auth/account.service';
import { IUser } from 'app/entities/user/user.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRapport for edit and NewRapportFormGroupInput for create.
 */
type RapportFormGroupInput = IRapport | PartialWithRequiredKeyOf<NewRapport>;

type RapportFormDefaults = Pick<NewRapport, 'id'>;

type RapportFormGroupContent = {
  id: FormControl<IRapport['id'] | NewRapport['id']>;
  userID: FormControl<IRapport['userID']>;
  message: FormControl<IRapport['message']>;
  codeErr: FormControl<IRapport['codeErr']>;
  textExpl: FormControl<IRapport['textExpl']>;
  errcode: FormControl<IRapport['errcode']>;
  transactionId: FormControl<IRapport['transactionId']>;
  user: FormControl<IRapport['user']>;
};

export type RapportFormGroup = FormGroup<RapportFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RapportFormService {
  account = inject(AccountService);
  userID?: Pick<IUser, 'id'> | null;

  createRapportFormGroup(rapport: RapportFormGroupInput = { id: null }): RapportFormGroup {
    this.account.getUserID().subscribe(value => (this.userID = value));
    const rapportRawValue = {
      ...this.getFormDefaults(),
      ...rapport,
    };
    return new FormGroup<RapportFormGroupContent>({
      id: new FormControl(
        { value: rapportRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      userID: new FormControl(rapportRawValue.userID),
      message: new FormControl(rapportRawValue.message),
      codeErr: new FormControl(rapportRawValue.codeErr),
      textExpl: new FormControl(rapportRawValue.textExpl),
      errcode: new FormControl(rapportRawValue.errcode),
      transactionId: new FormControl(rapportRawValue.transactionId),
      user: new FormControl({ value: this.userID, disabled: true }),
    });
  }

  getRapport(form: RapportFormGroup): IRapport | NewRapport {
    return form.getRawValue() as IRapport | NewRapport;
  }

  resetForm(form: RapportFormGroup, rapport: RapportFormGroupInput): void {
    const rapportRawValue = { ...this.getFormDefaults(), ...rapport };
    form.reset(
      {
        ...rapportRawValue,
        id: { value: rapportRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RapportFormDefaults {
    return {
      id: null,
    };
  }
}
