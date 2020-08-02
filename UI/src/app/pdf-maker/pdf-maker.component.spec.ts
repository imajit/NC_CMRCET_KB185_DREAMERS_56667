import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PdfMakerComponent } from './pdf-maker.component';

describe('PdfMakerComponent', () => {
  let component: PdfMakerComponent;
  let fixture: ComponentFixture<PdfMakerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PdfMakerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PdfMakerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
