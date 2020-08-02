import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FullDetailedTableComponent } from './full-detailed-table.component';

describe('FullDetailedTableComponent', () => {
  let component: FullDetailedTableComponent;
  let fixture: ComponentFixture<FullDetailedTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FullDetailedTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FullDetailedTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
