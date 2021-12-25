import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { AttachmentResponse } from '../../classes/AttachmentsResponse';

@Component({
  selector: 'app-email-editor',
  templateUrl: './email-editor.component.html',
  styleUrls: ['./email-editor.component.css']
})
export class EmailEditorComponent implements OnInit {

  form!: FormGroup; 
  receivers: string[];
  attachments: AttachmentResponse[];
  constructor(private fb: FormBuilder, private sanitizer:DomSanitizer) {
    this.receivers = [];
    this.attachments = [];
   }

  ngOnInit(): void {
    this.form = this.fb.group({
      priority: 2,
      receiver: '',
      subject: '',
      body: '',
    });
    // this.receivers.push(this.fb.group({ receiver: ''}));
     this.form.valueChanges.subscribe(console.log);
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
      }

    }

  }

  deleteItem(i: number, arr: Array<any>):void{
    console.log("delete " + i);
    arr.splice(i,1);
  }

  onFileChange(event: any){
    this.attachments.push(new AttachmentResponse(event.target.files.item(0)));
    console.log(event.target.files.item(0));
  }

  send(){

  }

  saveDraft(){

  }
  viewAttachment(attachment: AttachmentResponse){
    if(attachment.link == ''){
      return this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(attachment.file));
    }else{
      return attachment.link;
    }

  }
}
