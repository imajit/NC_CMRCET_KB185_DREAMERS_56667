import { BarChartComponent } from './bar-chart/bar-chart.component';
import { AppMaterialModule } from './app-material/app-material.module';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AngularFireModule } from '@angular/fire';
import { AngularFireDatabaseModule } from '@angular/fire/database';
import {environment} from '../environments/environment';
import { NgxGaugeModule } from 'ngx-gauge';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { SidebarComponent } from './sidebar/sidebar.component';
import { Routes,RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ChartsModule} from 'ng2-charts'
import { SearchComponent } from './search/search.component'
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { MatTableExporterModule } from 'mat-table-exporter';
import { DatepickerComponent } from './datepicker/datepicker.component';
import { TeachersearchComponent } from './teachersearch/teachersearch.component';
import { OfficersearchComponent } from './officersearch/officersearch.component';
import { RecentReviewsComponent } from './recent-reviews/recent-reviews.component';
import { FirstTableComponent } from './first-table/first-table.component';
import { BestSchoolsComponent } from './best-schools/best-schools.component';
import { TitleHeaderComponent } from './title-header/title-header.component';
import { from } from 'rxjs';
import { CirclechartComponent } from './circlechart/circlechart.component';
import { GaugeChartModule } from 'angular-gauge-chart';
import { DetailedSurveyComponent } from './detailed-survey/detailed-survey.component';
import { ScoreLineChartComponent } from './score-line-chart/score-line-chart.component';
import { ShowRankingComponent } from './show-ranking/show-ranking.component'
import { PdfMakerComponent } from './pdf-maker/pdf-maker.component';
import { WrapperComponent } from './wrapper/wrapper.component';
import { FullDetailedTableComponent } from './full-detailed-table/full-detailed-table.component'
import { PDFExportModule } from '@progress/kendo-angular-pdf-export';
import {DatePipe} from '@angular/common';
import { AppointmentComponent } from './appointment/appointment.component';
import { OtherReviewsComponent } from './other-reviews/other-reviews.component';

const routes:Routes =[
  {path:'',component:WrapperComponent,pathMatch:'full'},
  {path:'download', component:PdfMakerComponent},
  {path:'appointment',component:AppointmentComponent},
  {path:'reviews',component:OtherReviewsComponent}
]

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    DashboardComponent,
    BarChartComponent,
    SearchComponent,
    DatepickerComponent,
    TeachersearchComponent,
    OfficersearchComponent,
    RecentReviewsComponent,
    FirstTableComponent,
    BestSchoolsComponent,
    TitleHeaderComponent,
    CirclechartComponent,
    DetailedSurveyComponent,
    ScoreLineChartComponent,
    ShowRankingComponent,
    PdfMakerComponent,
    WrapperComponent,
    FullDetailedTableComponent,
    AppointmentComponent,
    OtherReviewsComponent,
  ],
  imports: [
    GaugeChartModule,
    BrowserModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFireDatabaseModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppMaterialModule,
    RouterModule.forRoot(routes),
    ChartsModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFireDatabaseModule,
    NgxGaugeModule,
    MatTableExporterModule,
    PDFExportModule
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
