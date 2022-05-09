import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfo } from '../../classes/UserInfo';
import { ControllerService } from '../../services/controller/controller.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  invalid!: boolean;
  userInfo!: UserInfo;
  constructor(private apiService: ControllerService, private router: Router, private r: ActivatedRoute) {
    this.userInfo = {
      email: "",
      password: ""
    }
  }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      userEmail!: new FormControl("",[Validators.required,Validators.email]),
      userPassword!: new FormControl("",[Validators.required, Validators.minLength(8), Validators.pattern('^[a-zA-z0-9]+$')])
    });
  }

  onSubmit(): void {
    if(this.loginForm.valid){
      this.userInfo.email = this.loginForm.get('userEmail')?.value;
      this.userInfo.password = this.loginForm.get('userPassword')?.value;
      this.apiService.logIn(this.userInfo)
      .subscribe(data =>{
        if(data == true){
          sessionStorage.setItem("user",this.userInfo.email);
          this.router.navigate(['../home/0'], { relativeTo: this.r });
        }else{
          this.invalid = true;
        }
      });
    }else{
      this.invalid = true;
    }
  }
}
