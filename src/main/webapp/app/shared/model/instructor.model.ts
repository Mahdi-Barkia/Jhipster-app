import { ICourse } from 'app/shared/model/course.model';

export interface IInstructor {
  id?: number;
  name?: string;
  age?: number;
  skills?: string;
  courses?: ICourse[];
}

export class Instructor implements IInstructor {
  constructor(public id?: number, public name?: string, public age?: number, public skills?: string, public courses?: ICourse[]) {}
}
