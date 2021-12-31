import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactsFolderComponent } from './contacts-folder.component';

describe('ContactsFolderComponent', () => {
  let component: ContactsFolderComponent;
  let fixture: ComponentFixture<ContactsFolderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContactsFolderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContactsFolderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
