import { NgModule } from '@angular/core';

import { Jhipster10062018SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [Jhipster10062018SharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [Jhipster10062018SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class Jhipster10062018SharedCommonModule {}
