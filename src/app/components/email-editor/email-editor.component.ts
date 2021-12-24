import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-email-editor',
  templateUrl: './email-editor.component.html',
  styleUrls: ['./email-editor.component.css']
})
export class EmailEditorComponent implements OnInit {

  form!: FormGroup; 
  receiversArr: string[];
  constructor(private fb: FormBuilder) {
    this.receiversArr = [];
   }

  ngOnInit(): void {
    this.form = this.fb.group({
      receiver: '',
      subject: '',
      body: '',
      attachments: this.fb.array([])
    });
    // this.receivers.push(this.fb.group({ receiver: ''}));
     this.form.valueChanges.subscribe(console.log);
  }

  // get receivers(): FormArray{
  //   return this.form.get('receivers') as FormArray;
  // }

  addRecievers(event: any):void{
    console.log(event.target);
    if(event.key == ' ' || event.key == 'Enter'){
      event.preventDefault();
      if(event.target.value != ''){
        this.receiversArr.push(event.target.value);
        event.target.value = '';
      }

    }

  }

  deleteReceiver(i: number):void{
    console.log("delete " + i);
    this.receiversArr.splice(i,1);
  }

}
