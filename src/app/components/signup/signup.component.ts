import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormControlName, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  signupForm!: FormGroup;
  isSubmitted: boolean = false;
  isMatched: boolean = true;
  constructor() { }

  ngOnInit(): void {
    this.signupForm = new FormGroup({
      firstName: new FormControl("", [Validators.required, Validators.pattern('^[a-zA-z]+$')]),
      lastName: new FormControl("", [Validators.required, Validators.pattern('^[a-zA-Z]+$')]),
      email: new FormControl("", [Validators.required, Validators.email]),
      password: new FormControl("", [Validators.required, Validators.minLength(8), Validators.pattern('^[a-zA-z0-9]+$')]),
      confirmedPassword: new FormControl("",[Validators.required])
    });
  }

  onSubmit(): void{
    this.isSubmitted = true;
    if(!Object.is(this.password?.value,this.confirmedPassword?.value) && !this.isEmpty(this.confirmedPassword?.value)){
      console.log(this.password?.value);
      console.log(this.confirmedPassword?.value);
      this.password?.reset();
      this.confirmedPassword?.reset();
      this.isMatched = false;
      console.log(this.signupForm);
    }else{
      this.isMatched = true;
    }
  }


  isEmpty(tester: string): boolean{ return tester ==""; }

  get firstName(){ return this.signupForm.get('firstName'); }

  get lastName(){ return this.signupForm.get('lastName'); }

  get email(){ return this.signupForm.get('email'); }

  get password(){ return this.signupForm.get('password'); }

  get confirmedPassword(){ return this.signupForm.get('confirmedPassword'); }
}
