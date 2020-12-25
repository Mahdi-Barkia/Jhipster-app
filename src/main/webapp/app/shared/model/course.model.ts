import { IInstructor } from 'app/shared/model/instructor.model';

export interface ICourse {
  id?: number;
  title?: string;
  description?: string;
  level?: string;
  tutor?: IInstructor;
}

export class Course implements ICourse {
  constructor(public id?: number, public title?: string, public description?: string, public level?: string, public tutor?: IInstructor) {}
}
