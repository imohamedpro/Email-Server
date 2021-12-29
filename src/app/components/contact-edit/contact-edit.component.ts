import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { contactsRequest } from '../../classes/Requests/ContactsRequest';

@Component({
  selector: 'app-contact-edit',
  templateUrl: './contact-edit.component.html',
  styleUrls: ['./contact-edit.component.css']
})
export class ContactEditComponent implements OnInit {
  contact!: contactsRequest;
  hasChanged: boolean = false;
  hasContactName: boolean = true;
  constructor(private r: ActivatedRoute) {
    //this.r.params.subscribe(val =>{});
    if(sessionStorage.getItem("contact")){
      this.contact = JSON.parse(sessionStorage.getItem("contact") as string);
      console.log(this.contact);
    }
    else{
      this.hasContactName = false;
      this.contact ={
        contactName: "",
        emails: [],
        id: Number.parseInt(this.r.snapshot.paramMap.get("id") as string),
      };
      console.log(this.contact);
    }
   }

  ngOnInit(): void {
  }
  removeContactName(){
    this.contact.contactName = "";
    this.hasContactName = false;
    console.log(this.contact);
  }
  deleteEmail(index: number){
    this.contact.emails.splice(index, 1);
    console.log(this.contact);
  }
  editContactName(event: any){
    if(event.key == " " || event.key == "Enter"){
      event.preventDefault();
      if(event.target.value != ""){
        this.contact.contactName = event.target.value;
        console.log(this.contact);
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
        console.log(this.contact);
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
