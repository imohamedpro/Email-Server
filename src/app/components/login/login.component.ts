import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ControllerService } from 'src/app/services/controller/controller.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  invalid!: boolean;
  constructor(private apiService: ControllerService, private router: Router, private r: ActivatedRoute) {
   }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      userEmail!: new FormControl("",[Validators.required,Validators.email]),
      userPassword!: new FormControl("",[Validators.required, Validators.minLength(8), Validators.pattern('^[a-zA-z0-9]+$')])
    });
  }

  onSubmit(): void {
    console.log(this.loginForm.get("userEmail")?.value);
    console.log(this.loginForm.get("userPassword")?.value);
    if(this.loginForm.valid){
      this.apiService.logIn(this.loginForm.value)
      .subscribe(data =>{
        if(data == true){
          sessionStorage.setItem("user",this.loginForm.get("userEmail")?.value);
          this.router.navigate(['../home/inbox'], { relativeTo: this.r });
        }else{
          this.invalid = true;
        }
      });
    }else{
      this.invalid = true;
    }
  }
}
