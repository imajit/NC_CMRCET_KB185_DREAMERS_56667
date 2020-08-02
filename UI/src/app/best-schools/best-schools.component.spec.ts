import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BestSchoolsComponent } from './best-schools.component';

describe('BestSchoolsComponent', () => {
  let component: BestSchoolsComponent;
  let fixture: ComponentFixture<BestSchoolsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BestSchoolsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BestSchoolsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
