import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoaAppTestModule } from '../../../test.module';
import { InstructorDetailComponent } from 'app/entities/instructor/instructor-detail.component';
import { Instructor } from 'app/shared/model/instructor.model';

describe('Component Tests', () => {
  describe('Instructor Management Detail Component', () => {
    let comp: InstructorDetailComponent;
    let fixture: ComponentFixture<InstructorDetailComponent>;
    const route = ({ data: of({ instructor: new Instructor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SoaAppTestModule],
        declarations: [InstructorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(InstructorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InstructorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load instructor on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.instructor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
