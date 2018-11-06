import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { Jhipster10062018FooModule } from './foo/foo.module';
import { Jhipster10062018RegionModule } from './region/region.module';
import { Jhipster10062018CountryModule } from './country/country.module';
import { Jhipster10062018LocationModule } from './location/location.module';
import { Jhipster10062018DepartmentModule } from './department/department.module';
import { Jhipster10062018TaskModule } from './task/task.module';
import { Jhipster10062018EmployeeModule } from './employee/employee.module';
import { Jhipster10062018JobModule } from './job/job.module';
import { Jhipster10062018JobHistoryModule } from './job-history/job-history.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        Jhipster10062018FooModule,
        Jhipster10062018RegionModule,
        Jhipster10062018CountryModule,
        Jhipster10062018LocationModule,
        Jhipster10062018DepartmentModule,
        Jhipster10062018TaskModule,
        Jhipster10062018EmployeeModule,
        Jhipster10062018JobModule,
        Jhipster10062018JobHistoryModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Jhipster10062018EntityModule {}
