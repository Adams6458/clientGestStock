import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';
import { RapportService } from '../service/rapport.service';
import { IRapport } from '../rapport.model';
import { RapportFormService } from './rapport-form.service';

import { RapportUpdateComponent } from './rapport-update.component';

describe('Rapport Management Update Component', () => {
  let comp: RapportUpdateComponent;
  let fixture: ComponentFixture<RapportUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let rapportFormService: RapportFormService;
  let rapportService: RapportService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RapportUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(RapportUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RapportUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    rapportFormService = TestBed.inject(RapportFormService);
    rapportService = TestBed.inject(RapportService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const rapport: IRapport = { id: 456 };
      const user: IUser = { id: 13519 };
      rapport.user = user;

      const userCollection: IUser[] = [{ id: 14146 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ rapport });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining),
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const rapport: IRapport = { id: 456 };
      const user: IUser = { id: 3890 };
      rapport.user = user;

      activatedRoute.data = of({ rapport });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.rapport).toEqual(rapport);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRapport>>();
      const rapport = { id: 123 };
      jest.spyOn(rapportFormService, 'getRapport').mockReturnValue(rapport);
      jest.spyOn(rapportService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rapport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: rapport }));
      saveSubject.complete();

      // THEN
      expect(rapportFormService.getRapport).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(rapportService.update).toHaveBeenCalledWith(expect.objectContaining(rapport));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRapport>>();
      const rapport = { id: 123 };
      jest.spyOn(rapportFormService, 'getRapport').mockReturnValue({ id: null });
      jest.spyOn(rapportService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rapport: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: rapport }));
      saveSubject.complete();

      // THEN
      expect(rapportFormService.getRapport).toHaveBeenCalled();
      expect(rapportService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRapport>>();
      const rapport = { id: 123 };
      jest.spyOn(rapportService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rapport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(rapportService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
