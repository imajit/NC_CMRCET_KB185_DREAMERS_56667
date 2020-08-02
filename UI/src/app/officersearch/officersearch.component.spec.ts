import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OfficersearchComponent } from './officersearch.component';

describe('OfficersearchComponent', () => {
  let component: OfficersearchComponent;
  let fixture: ComponentFixture<OfficersearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OfficersearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OfficersearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
