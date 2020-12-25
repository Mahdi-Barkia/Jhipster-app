import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInstructor } from 'app/shared/model/instructor.model';
import { InstructorService } from './instructor.service';

@Component({
  templateUrl: './instructor-delete-dialog.component.html',
})
export class InstructorDeleteDialogComponent {
  instructor?: IInstructor;

  constructor(
    protected instructorService: InstructorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.instructorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('instructorListModification');
      this.activeModal.close();
    });
  }
}
