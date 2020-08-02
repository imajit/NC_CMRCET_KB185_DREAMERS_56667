import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScoreLineChartComponent } from './score-line-chart.component';

describe('ScoreLineChartComponent', () => {
  let component: ScoreLineChartComponent;
  let fixture: ComponentFixture<ScoreLineChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScoreLineChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScoreLineChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
