import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Contact } from '../../classes/Contact';
import { contactsRequest } from '../../classes/Requests/ContactsRequest';
import { ControllerService } from '../../services/controller/controller.service';

@Component({
  selector: 'app-contacts-folder',
  templateUrl: './contacts-folder.component.html',
  styleUrls: ['./contacts-folder.component.css']
})
export class ContactsFolderComponent implements OnInit {
  contacts!: Contact[];
  pageNumber!: number;
  totalPageNumber!: number;
  selectedSearching!: string;
  sorted!: boolean;

  constructor(private router: Router, private r: ActivatedRoute, private apiService: ControllerService) {
    this.selectedSearching = "";
    this.pageNumber = 1;
    this.sorted = false;
    this.r.params.subscribe(val => {
      this.apiService.getContactPages(sessionStorage.getItem('user') as string, 4)
        .subscribe(data => {
          this.totalPageNumber = data;
        });
      this.apiService.loadContacts(sessionStorage.getItem('user') as string, this.pageNumber, 4, this.sorted)
        .subscribe(data => {
          this.contacts = data;
        });
    });
  }

  ngOnInit(): void { }

  updateRadio(b: boolean) {
    if (b == true) {
      this.sorted = true;
      if (this.selectedSearching.length != 0) {
        this.apiService.searchContact(sessionStorage.getItem('user') as string, this.selectedSearching, this.pageNumber, 4, this.sorted)
          .subscribe(data => {
            this.contacts = data;
          });
      } else {
        this.apiService.loadContacts(sessionStorage.getItem('user') as string, this.pageNumber, 4, this.sorted)
          .subscribe(data => {
            this.contacts = data;
          });
      }
    } else {
      this.sorted = false;
      if (this.selectedSearching.length != 0) {
        this.apiService.searchContact(sessionStorage.getItem('user') as string, this.selectedSearching, this.pageNumber, 4, this.sorted)
          .subscribe(data => {
            this.contacts = data;
          });
      } else {
        this.apiService.loadContacts(sessionStorage.getItem('user') as string, this.pageNumber, 4, this.sorted)
          .subscribe(data => {
            this.contacts = data;
          });
      }
    }
  }

  updateSearching(e: any) {
    this.selectedSearching = e.target.value;
    console.log(this.selectedSearching);
    this.apiService.searchContact(sessionStorage.getItem('user') as string, this.selectedSearching, this.pageNumber, 4, this.sorted)
      .subscribe(data => {
        this.contacts = data;
      });
    this.apiService.getContactPages(sessionStorage.getItem('user') as string, 4)
      .subscribe(data => {
        this.totalPageNumber = data;
        console.log(this.totalPageNumber);
      });
  }

  goToContact(index: number) {
    sessionStorage.setItem("contact", JSON.stringify(this.contacts[index]));
    return this.router.navigate([this.contacts[index].id], { relativeTo: this.r });
  }

  createNewContact() {
    let index = -1;
    sessionStorage.removeItem("contact");
    return this.router.navigate([index], { relativeTo: this.r });
  }

  delete(index: number) {
    this.apiService.deleteContact(sessionStorage.getItem('user') as string, this.contacts[index].id).subscribe();
    if (this.selectedSearching.length != 0) {
      this.apiService.searchContact(sessionStorage.getItem('user') as string, this.selectedSearching, this.pageNumber, 4, this.sorted)
        .subscribe(data => {
          this.contacts = data;
        });
    } else {
      this.apiService.loadContacts(sessionStorage.getItem('user') as string, this.pageNumber, 4, this.sorted)
        .subscribe(data => {
          this.contacts = data;
        });
    }
    this.apiService.getContactPages(sessionStorage.getItem('user') as string, 4)
      .subscribe(data => {
        this.totalPageNumber = data;
      });
  }

  moveForward() {
    if (this.pageNumber < this.totalPageNumber) {
      ++this.pageNumber;
      if (this.selectedSearching.length != 0) {
        this.apiService.searchContact(sessionStorage.getItem('user') as string, this.selectedSearching, this.pageNumber, 4, this.sorted)
          .subscribe(data => {
            this.contacts = data;
          });
      } else {
        this.apiService.loadContacts(sessionStorage.getItem('user') as string, this.pageNumber, 4, this.sorted)
          .subscribe(data => {
            this.contacts = data;
          });
      }
    }
  }

  moveBackward() {
    if (this.pageNumber > 1) {
      --this.pageNumber;
      if (this.selectedSearching.length != 0) {
        this.apiService.searchContact(sessionStorage.getItem('user') as string, this.selectedSearching, this.pageNumber, 4, this.sorted)
          .subscribe(data => {
            this.contacts = data;
          });
      } else {
        this.apiService.loadContacts(sessionStorage.getItem('user') as string, this.pageNumber, 4, this.sorted)
          .subscribe(data => {
            this.contacts = data;
          });
      }
    }

  }
}
