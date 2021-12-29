import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { Email } from '../../classes/Email';
import { EmailBody } from '../../classes/EmailBody';
import { EmailHeader } from '../../classes/EmailHeader';
import { AttachmentResponse } from '../../classes/Responses/AttachmentsResponse';
import { ControllerService } from '../../services/controller/controller.service';

@Component({
  selector: 'app-email-editor',
  templateUrl: './email-editor.component.html',
  styleUrls: ['./email-editor.component.css']
})
export class EmailEditorComponent implements OnInit {

  form!: FormGroup; 
  receivers: string[];
  attachments: string[];
  changed: boolean = false;
  downloadLink = '';
  user: string;
  emailID: string;
  constructor(private fb: FormBuilder,
              private sanitizer:DomSanitizer,
              private controller: ControllerService,
              private r: ActivatedRoute) {
    this.receivers = [];
    this.attachments = [];
    this.user = 'moh@site.com';
    this.emailID = '0';
    r.params.subscribe(()=>{

      controller.getEmail(Number(this.emailID), this.user).subscribe((email)=>{
    //load draft   
          this.receivers = email.emailHeader.to;
          this.attachments = email.emailBody.attachments;
          this.form = this.fb.group({
            priority: email.emailHeader.priority,
            receiver: '',
            subject: email.emailHeader.subject,
            body: email.emailBody.body,
            file: null,
          });

          this.form.valueChanges.subscribe(()=>{
            this.changed = true;
            console.log;
          });
          setInterval(()=>{
            if(this.changed){
              console.log("time is up");

              this.saveDraft();
              this.changed = false;

            }
          }, 3000);
            });
    });


   }

  ngOnInit(): void {

    // //load draft   
    // this.form = this.fb.group({
    //   priority: '2',
    //   receiver: '',
    //   subject: '',
    //   body: '',
    //   file: null,
    // });

    //  this.form.valueChanges.subscribe(()=>{
    //    this.changed = true;
    //    console.log;
    //  });
    //  setInterval(()=>{
    //    if(this.changed){
    //     console.log("time is up");

    //     this.saveDraft();
    //     this.changed = false;

    //    }
    //  }, 3000);
  }


  addRecievers(event: any):void{
    if(event.key == ' ' || event.key == 'Enter'){
      event.preventDefault();
      if(event.target.value != ''){
        this.receivers.push(event.target.value);
        event.target.value = '';
        this.changed = true;
      }

    }

  }

  deleteItem(i: number, arr: Array<any>):void{
    console.log("delete " + i);
    arr.splice(i,1);
    this.changed = true;
  }

  onFileChange(event: any){
    this.attachments.push(event.target.files.item(0).name);
    this.controller.uploadAttachment(event.target.files.item(0),
                                      this.emailID,
                                      this.user)?.subscribe(()=>{console.log('uploaded')});
    console.log(event.target.files.item(0));
  }
  deleteAttachment(i: number){
    let name = this.attachments.splice(i, 1);
    this.controller.deleteAttachment(name[0],
                                      this.emailID,
                                      this.user).subscribe(()=>{console.log('deleted ' + name)});
  }
  viewAttachment(attachment: string){
    // if(attachment.link == ''){
    //   return this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(attachment.file));+
    // }else{
    //   return attachment.link;
    // }
    console.log(attachment);
    this.controller.downloadAttachment(
      attachment,
      this.emailID,
      this.user
    ).subscribe((file)=>{
      this.downloadLink = file;
      console.log('hello');
      // const download = document.createElement('a');
      // download.setAttribute('href', file);
      // return this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(file));
      // download.click();


    });
  }
  send(event: any){
    console.log(event);
    if(event.key == 'Enter'){
      return;
    }
    console.log("send");
    let email = this.buildEmail();
    this.controller.sendEmail(email).subscribe();


    //go back to home
  }

  saveDraft(){
    console.log("saving...");

    let email = this.buildEmail();
    this.controller.saveDraft(email).subscribe();
  }

  getAttachmentName(attachment: AttachmentResponse): string{
    if(attachment.name != undefined){
      return attachment.name
    }else if(attachment.file != undefined){
      return attachment.file.name;
    }else{
      return "no-name";
    }
  }

  buildEmail(){
    let email = new Email();
    email.emailHeader = new EmailHeader();
    email.emailBody = new EmailBody();
    email.id = Number(this.emailID);
    console.log(this.form.controls['priority'].value);
    // email.emailHeader.priority = this.form.controls['priority'].value;
    email.emailHeader.priority = this.form.controls['priority'].value;
    email.emailHeader.from = this.user;
    email.emailHeader.to = [];
    this.receivers.forEach((receiver)=>{
      console.log(receiver);
      email.emailHeader.to.push(receiver);
    });
    console.log(this.form.controls['subject'].value);
    email.emailHeader.subject = this.form.controls['subject'].value;
    console.log(this.form.controls['body'].value);
    email.emailBody.body = this.form.controls['body'].value;
    email.emailBody.attachments = [];
    this.attachments.forEach((attachment)=>{
      console.log(attachment);
      email.emailBody.attachments.push(attachment);
    });
    // this.controller.saveDraft(email).subscribe();
    return email;
  }

  disableEnter(event: any){
    console.log(event.key);
    if(event.key == 'Enter'){
      event.preventDefault();
    }
  }
  getEmailID(){
    return this.r.snapshot.paramMap.get("id");
  }
}
