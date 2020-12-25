import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { SoaAppSharedModule } from 'app/shared/shared.module';
import { SoaAppCoreModule } from 'app/core/core.module';
import { SoaAppAppRoutingModule } from './app-routing.module';
import { SoaAppHomeModule } from './home/home.module';
import { SoaAppEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    SoaAppSharedModule,
    SoaAppCoreModule,
    SoaAppHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    SoaAppEntityModule,
    SoaAppAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class SoaAppAppModule {}
