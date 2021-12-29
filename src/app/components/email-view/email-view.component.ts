import { Component, Input, OnInit } from '@angular/core';
import { EmailRequest } from '../../classes/Requests/EmailRequest';

@Component({
  selector: 'app-email-view',
  templateUrl: './email-view.component.html',
  styleUrls: ['./email-view.component.css']
})
export class EmailViewComponent implements OnInit {
  email!: EmailRequest;

  constructor() { }

  ngOnInit(): void {
    //api here
    this.email = 
      {
        date: new Date("2019-10-12"),
        from: "youssef",
        priority: 1,
        subject: "nothing",
        content: "bla bla bla",
        attachmentsLinks: ["first link"]
      }
  }
}
