import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInstructor, Instructor } from 'app/shared/model/instructor.model';
import { InstructorService } from './instructor.service';

@Component({
  selector: 'jhi-instructor-update',
  templateUrl: './instructor-update.component.html',
})
export class InstructorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    age: [null, [Validators.required, Validators.min(18), Validators.max(65)]],
    skills: [null, [Validators.required]],
  });

  constructor(protected instructorService: InstructorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ instructor }) => {
      this.updateForm(instructor);
    });
  }

  updateForm(instructor: IInstructor): void {
    this.editForm.patchValue({
      id: instructor.id,
      name: instructor.name,
      age: instructor.age,
      skills: instructor.skills,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const instructor = this.createFromForm();
    if (instructor.id !== undefined) {
      this.subscribeToSaveResponse(this.instructorService.update(instructor));
    } else {
      this.subscribeToSaveResponse(this.instructorService.create(instructor));
    }
  }

  private createFromForm(): IInstructor {
    return {
      ...new Instructor(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      age: this.editForm.get(['age'])!.value,
      skills: this.editForm.get(['skills'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInstructor>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
