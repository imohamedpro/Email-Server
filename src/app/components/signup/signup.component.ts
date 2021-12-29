import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormControlName, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfo } from '../../classes/UserInfo';
import { ControllerService } from '../../services/controller/controller.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  signupForm!: FormGroup;
  isSubmitted: boolean = false;
  isMatched: boolean = true;
  userInfo!: UserInfo;
  invalid!: boolean;
  constructor(private apiService: ControllerService, private router: Router, private r: ActivatedRoute) {
    this.userInfo = {
      email: "",
      password: ""
    }
   }

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
    if(Object.is(this.password?.value,this.confirmedPassword?.value) && this.signupForm.valid){
      this.userInfo.email = this.signupForm.get('email')?.value;
      this.userInfo.password = this.signupForm.get('password')?.value;
      this.apiService.signUp(this.userInfo)
      .subscribe(data =>{
        if(data == true){
          sessionStorage.setItem("user",this.userInfo.email);
          this.router.navigate(['../home/inbox'], { relativeTo: this.r });
        }else{
          this.invalid = true;
          this.signupForm.get('email')?.reset();
        }
      });
    }
  }


  isEmpty(tester: string): boolean{ return tester ==""; }

  get firstName(){ return this.signupForm.get('firstName'); }

  get lastName(){ return this.signupForm.get('lastName'); }

  get email(){ return this.signupForm.get('email'); }

  get password(){ return this.signupForm.get('password'); }

  get confirmedPassword(){ return this.signupForm.get('confirmedPassword'); }
}
