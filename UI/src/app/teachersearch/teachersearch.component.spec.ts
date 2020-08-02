import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TeachersearchComponent } from './teachersearch.component';

describe('TeachersearchComponent', () => {
  let component: TeachersearchComponent;
  let fixture: ComponentFixture<TeachersearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TeachersearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TeachersearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
