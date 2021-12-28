import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { contactsRequest } from 'src/app/classes/Requests/ContactsRequest';

@Component({
  selector: 'app-contact-edit',
  templateUrl: './contact-edit.component.html',
  styleUrls: ['./contact-edit.component.css']
})
export class ContactEditComponent implements OnInit {
  contact!: contactsRequest;
  hasChanged: boolean = false;
  hasContactName: boolean = true;
  constructor() {
    //this.r.params.subscribe(val =>{});
    this.contact = JSON.parse(sessionStorage.getItem("contact") as string);
    console.log(this.contact);
    if(this.contact.contactName == "") this.hasContactName = false;
   }

  ngOnInit(): void {
  }
  removeContactName(){
    this.contact.contactName = "";
    this.hasContactName = false;
  }
  deleteEmail(index: number){
    this.contact.emails.splice(index, 1);
  }
  editContactName(event: any){
    if(event.key == " " || event.key == "Enter"){
      event.preventDefault();
      if(event.target.value != ""){
        this.contact.contactName = event.target.value;
        event.target.value = "";
        this.hasChanged = true;
        this.hasContactName = true;
      }
    }
  }
  addEmail(event: any){
    if(event.key == " " || event.key == "Enter"){
      event.preventDefault();
      if(event.target.value != ""){
        this.contact.emails.push(event.target.value);
        console.log(this.contact.emails);
        event.target.value = "";
        this.hasChanged = true;
      }
    }
  }
  send(e: any){
    if(this.hasChanged){
      //api to update the contact
    }
  }

}
