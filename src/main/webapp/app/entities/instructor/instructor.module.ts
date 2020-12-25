import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoaAppSharedModule } from 'app/shared/shared.module';
import { InstructorComponent } from './instructor.component';
import { InstructorDetailComponent } from './instructor-detail.component';
import { InstructorUpdateComponent } from './instructor-update.component';
import { InstructorDeleteDialogComponent } from './instructor-delete-dialog.component';
import { instructorRoute } from './instructor.route';

@NgModule({
  imports: [SoaAppSharedModule, RouterModule.forChild(instructorRoute)],
  declarations: [InstructorComponent, InstructorDetailComponent, InstructorUpdateComponent, InstructorDeleteDialogComponent],
  entryComponents: [InstructorDeleteDialogComponent],
})
export class SoaAppInstructorModule {}
