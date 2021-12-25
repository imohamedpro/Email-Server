import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { AttachmentResponse } from '../../classes/Responses/AttachmentsResponse';

@Component({
  selector: 'app-email-editor',
  templateUrl: './email-editor.component.html',
  styleUrls: ['./email-editor.component.css']
})
export class EmailEditorComponent implements OnInit {

  form!: FormGroup; 
  receivers: string[];
  attachments: AttachmentResponse[];
  changed: boolean = false;
  // files: File[];
  constructor(private fb: FormBuilder, private sanitizer:DomSanitizer) {
    this.receivers = [];
    this.attachments = [];
   }

  ngOnInit(): void {
    this.form = this.fb.group({
      priority: '2',
      receiver: '',
      subject: '',
      body: '',
      file: null,
    });
    // this.receivers.push(this.fb.group({ receiver: ''}));
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
  }

  // get receivers(): FormArray{
  //   return this.form.get('receivers') as FormArray;
  // }

  addRecievers(event: any):void{
    // console.log(event.target);
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
    this.attachments.push(new AttachmentResponse(event.target.files.item(0)));
    console.log(event.target.files.item(0));
  }

  send(event: any){
    console.log(event);
    if(event.key == 'Enter'){
      return;
    }
    console.log("send");
    let email = this.buildEmail();
    // console.log(this.form.controls.get('priority'));
  }

  saveDraft(){
    console.log("saving...");

    let email = this.buildEmail();
  }
  viewAttachment(attachment: AttachmentResponse){
    if(attachment.link == ''){
      return this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(attachment.file));
    }else{
      return attachment.link;
    }
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
    console.log(this.form.controls['priority'].value);
    this.receivers.forEach((receiver)=>{
      console.log(receiver);
    });
    console.log(this.form.controls['subject'].value);
    console.log(this.form.controls['body'].value);
    this.attachments.forEach((attachment)=>{
      console.log(attachment.file);
    });
    return null;
  }

  disableEnter(event: any){
    console.log(event.key);
    if(event.key == 'Enter'){
      event.preventDefault();
    }
  }
}
