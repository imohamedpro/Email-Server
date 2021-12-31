import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
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
  emailID!: string;
  constructor(private fb: FormBuilder,
              private sanitizer:DomSanitizer,
              private controller: ControllerService,
              private r: ActivatedRoute,
              private router: Router) {
    this.receivers = [];
    this.attachments = [];
    this.user = sessionStorage.getItem("user") as string;
    this.form = this.fb.group({
      priority: '2',
      receiver: '',
      subject: '',
      body: '',
      file: null,
    });
    r.params.subscribe(()=>{
      console.log(this.r.snapshot.paramMap.get("id") as string);
      console.log(this.router.url.includes('drafteditor'));
      if(this.router.url.includes('drafteditor')){
        controller.getEmail(this.r.snapshot.paramMap.get("id") as string, this.user)
       .subscribe((email)=>{
          this.receivers = email.emailHeader.to || new Array<string>();
          console.log(email.emailHeader.to);
          console.log(this.receivers);
          this.attachments = email.emailBody.attachments || new Array<string>();
          this.form = this.fb.group({
            priority: email.emailHeader.priority,
            receiver: '',
            subject: email.emailHeader.subject,
           body: email.emailBody.body,
           file: null,
         });
         this.form.valueChanges.subscribe(()=>{
          this.changed = true;
          console.log("values changed");
        });
        setInterval(()=>{
          if(this.changed){
            console.log("time is up");

            this.saveDraft();
            this.changed = false;

          }
        }, 3000);
        });
      }
    else{
      controller.createEmail(this.user).subscribe((id) => {
        this.emailID = id.toString();
        this.form.valueChanges.subscribe(()=>{
          this.changed = true;
          console.log("values changed");
        });
        setInterval(()=>{
          if(this.changed){
            console.log("time is up");

            this.saveDraft();
            this.changed = false;

          }
        }, 3000);
          });
    }
    
    });


   }

  ngOnInit(): void { 
    

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
    if(this.receivers.length == 0){
      alert("Cannot send email with no receivers!")
      return;
    }
    if(event.key == 'Enter'){
      return;
    }
    console.log("send");
    let email = this.buildEmail();
    this.controller.sendEmail(email).subscribe();
    if(this.router.url.includes('drafteditor')){
      this.router.navigate(['../../../0'],{relativeTo: this.r});
    }else{
      this.router.navigate(['../0'],{relativeTo: this.r});
    }
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
