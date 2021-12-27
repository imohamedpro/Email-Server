import { Component, Input, OnInit } from '@angular/core';
import { EmailRequest } from 'src/app/classes/Requests/EmailRequest';

@Component({
  selector: 'app-email-view',
  templateUrl: './email-view.component.html',
  styleUrls: ['./email-view.component.css']
})
export class EmailViewComponent implements OnInit {
  email!: EmailRequest;

  constructor() { }

  ngOnInit(): void {
    //api to get the email
    this.email = 
      {
        date: new Date("2019-10-12"),
        from: "youssef",
        priority: 1,
        subject: "nothing",
        content: "Dear Folan,\n ta7ia tayeba w b3d bla bla bla\n bla bla bla bla bla bla\n bla bla blabla bla bla\n bla bla blabla bla bla\n bla bla blabla bla bla\n bla bla blabla bla bla\n bla bla blabla bla bla\n bla bla bla",
        attachmentsLinks: ["first link"]
      }
  }
}
