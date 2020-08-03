import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OtherReviewsComponent } from './other-reviews.component';

describe('OtherReviewsComponent', () => {
  let component: OtherReviewsComponent;
  let fixture: ComponentFixture<OtherReviewsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OtherReviewsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OtherReviewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
