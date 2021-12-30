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
  from!: string;
  constructor(private controller: ControllerService, private r: ActivatedRoute) {
      this.user = sessionStorage.getItem("user") as string;
      this.emailID = this.r.snapshot.paramMap.get("id") as string
    }


  ngOnInit(): void {
    //api here
    this.controller.getEmail(this.emailID, this.user).subscribe((email)=>{
      //load email  
        this.email.attachments = email.emailBody.attachments;
        this.email.content = email.emailBody.body;
        this.email.date = email.emailHeader.date;
        this.email.from = email.emailHeader.from;
        this.from = email.emailHeader.from;
        this.email.priority = email.emailHeader.priority;
        this.email.subject = email.emailHeader.subject;
        });
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
