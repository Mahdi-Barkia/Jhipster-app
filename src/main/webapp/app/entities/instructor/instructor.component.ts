import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInstructor } from 'app/shared/model/instructor.model';
import { InstructorService } from './instructor.service';
import { InstructorDeleteDialogComponent } from './instructor-delete-dialog.component';

@Component({
  selector: 'jhi-instructor',
  templateUrl: './instructor.component.html',
})
export class InstructorComponent implements OnInit, OnDestroy {
  instructors?: IInstructor[];
  eventSubscriber?: Subscription;

  constructor(protected instructorService: InstructorService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.instructorService.query().subscribe((res: HttpResponse<IInstructor[]>) => (this.instructors = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInstructors();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInstructor): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInstructors(): void {
    this.eventSubscriber = this.eventManager.subscribe('instructorListModification', () => this.loadAll());
  }

  delete(instructor: IInstructor): void {
    const modalRef = this.modalService.open(InstructorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.instructor = instructor;
  }
}
