import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Contact } from '../../classes/Contact';
import { ContactAndUsername } from '../../classes/ContactAndUsername';
import { contactsRequest } from '../../classes/Requests/ContactsRequest';
import { ControllerService } from '../../services/controller/controller.service';

@Component({
  selector: 'app-contact-edit',
  templateUrl: './contact-edit.component.html',
  styleUrls: ['./contact-edit.component.css']
})
export class ContactEditComponent implements OnInit {
  contact!: Contact;
  hasChanged: boolean = false;
  hasContactName: boolean = true;
  constructor(private r: ActivatedRoute, private apiService: ControllerService) {
    //this.r.params.subscribe(val =>{});
    if(sessionStorage.getItem("contact")){
      this.contact = JSON.parse(sessionStorage.getItem("contact") as string);
      console.log(this.contact);
    }
    else{
      this.hasContactName = false;
      this.contact ={
        name: "",
        usernames: [],
        id: Number.parseInt(this.r.snapshot.paramMap.get("id") as string),
      };
      console.log(this.contact);
    }
   }

  ngOnInit(): void {
  }
  removeContactName(){
    this.contact.name = "";
    this.hasContactName = false;
    console.log(this.contact);
    this.hasChanged = true;
  }
  deleteEmail(index: number){
    this.contact.usernames.splice(index, 1);
    console.log(this.contact);
  }
  editContactName(event: any){
    if(event.key == " " || event.key == "Enter"){
      event.preventDefault();
      if(event.target.value != ""){
        this.contact.name = event.target.value;
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
        this.contact.usernames.push(event.target.value);
        console.log(this.contact);
        event.target.value = "";
        this.hasChanged = true;
      }
    }
  }
  send(e: any){
    if(this.hasChanged){
      let contactAndUsername = new ContactAndUsername();
      contactAndUsername = {
        user: sessionStorage.getItem('user') as string,
        contact: this.contact
      };
      this.apiService.addContact(contactAndUsername).subscribe();
    }
  }

}
