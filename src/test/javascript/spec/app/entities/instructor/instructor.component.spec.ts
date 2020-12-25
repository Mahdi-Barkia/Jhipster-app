import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SoaAppTestModule } from '../../../test.module';
import { InstructorComponent } from 'app/entities/instructor/instructor.component';
import { InstructorService } from 'app/entities/instructor/instructor.service';
import { Instructor } from 'app/shared/model/instructor.model';

describe('Component Tests', () => {
  describe('Instructor Management Component', () => {
    let comp: InstructorComponent;
    let fixture: ComponentFixture<InstructorComponent>;
    let service: InstructorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SoaAppTestModule],
        declarations: [InstructorComponent],
      })
        .overrideTemplate(InstructorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InstructorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InstructorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Instructor(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.instructors && comp.instructors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
