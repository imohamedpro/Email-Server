import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Email } from 'src/app/classes/Email';
import { EmailBody } from 'src/app/classes/EmailBody';
import { EmailHeader } from 'src/app/classes/EmailHeader';
import { ControllerService } from '../../services/controller/controller.service';

@Component({
  selector: 'app-email-view',
  templateUrl: './email-view.component.html',
  styleUrls: ['./email-view.component.css']
})
export class EmailViewComponent implements OnInit {
  email!: Email;
  downloadLink = '';
  user: string;
  emailID: string;
  from!: string;
  constructor(private controller: ControllerService, private r: ActivatedRoute) {
      this.email = new Email();
      this.user = sessionStorage.getItem("user") as string;
      this.emailID = this.r.snapshot.paramMap.get("id") as string
    }

  ngOnInit(): void {
    //api here
    this.controller.getEmail(this.emailID, this.user).subscribe((email)=>{
      //load email  
        this.email.id = email.id;
        this.email.isRead = email.isRead;
        this.email.folders = email.folders;
        this.email.emailHeader = email.emailHeader;
        this.email.emailBody = email.emailBody;
        this.email.deleteDate = email.deleteDate;
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
