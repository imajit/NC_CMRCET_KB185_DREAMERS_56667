import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CirclechartComponent } from './circlechart.component';

describe('CirclechartComponent', () => {
  let component: CirclechartComponent;
  let fixture: ComponentFixture<CirclechartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CirclechartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CirclechartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
