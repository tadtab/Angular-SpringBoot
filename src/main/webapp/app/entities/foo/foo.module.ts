import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Jhipster10062018SharedModule } from 'app/shared';
import {
    FooComponent,
    FooDetailComponent,
    FooUpdateComponent,
    FooDeletePopupComponent,
    FooDeleteDialogComponent,
    fooRoute,
    fooPopupRoute
} from './';

const ENTITY_STATES = [...fooRoute, ...fooPopupRoute];

@NgModule({
    imports: [Jhipster10062018SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [FooComponent, FooDetailComponent, FooUpdateComponent, FooDeleteDialogComponent, FooDeletePopupComponent],
    entryComponents: [FooComponent, FooUpdateComponent, FooDeleteDialogComponent, FooDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Jhipster10062018FooModule {}
