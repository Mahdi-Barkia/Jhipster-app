import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'instructor',
        loadChildren: () => import('./instructor/instructor.module').then(m => m.SoaAppInstructorModule),
      },
      {
        path: 'course',
        loadChildren: () => import('./course/course.module').then(m => m.SoaAppCourseModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SoaAppEntityModule {}
