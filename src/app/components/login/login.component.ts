import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  constructor() { }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      userEmail!: new FormControl(),
      userPassword!: new FormControl()
    });
  }

  onSubmit(): void {
    //console.log(this.loginForm.controls.userEmail.value);
    console.log(this.loginForm.get("userEmail")?.value);
    console.log(this.loginForm.controls['userPassword'].value);
    console.log(this.loginForm.get('userPassword')?.value);
  }
}
