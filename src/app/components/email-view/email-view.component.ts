import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EmailRequest } from '../../classes/Requests/EmailRequest';
import { ControllerService } from '../../services/controller/controller.service';

@Component({
  selector: 'app-email-view',
  templateUrl: './email-view.component.html',
  styleUrls: ['./email-view.component.css']
})
export class EmailViewComponent implements OnInit {
  email!: EmailRequest;
  downloadLink = '';
  user: string;
  emailID: string;
  constructor(private controller: ControllerService, private r: ActivatedRoute) {
      this.user = 'admin';
      this.emailID = '0';
      r.params.subscribe(()=>{

      controller.getEmail(Number(this.emailID), this.user).subscribe((email)=>{
      //load draft   
        this.email.attachments = email.emailBody.attachments;
        this.email.content = email.emailBody.body;
        this.email.date = new Date(email.emailHeader.date);
        this.email.from = email.emailHeader.from;
        this.email.priority = email.emailHeader.priority;
        this.email.subject = email.emailHeader.subject;
        });
      });
    }


  ngOnInit(): void {
    //api here
    this.email = 
      {
        date: new Date("2019-10-12"),
        from: "youssef",
        priority: 1,
        subject: "nothing",
        content: "bla bla bla",
        attachments: ["first link"]
      }
  }

  viewAttachment(attachment: string){
    console.log(attachment);
    this.controller.downloadAttachment(
      attachment,
      this.emailID,
      this.user
    ).subscribe((file)=>{
      this.downloadLink = file;
      console.log('hello');
    });
  }
}
