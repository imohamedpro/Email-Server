import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ComposeComponent } from './components/compose/compose.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';

const routes: Routes = [
  {path:'', redirectTo:'login', pathMatch:'full'},
  {path:'login', component:LoginComponent},
  {path:'signup', component:SignupComponent},
  {
    path: 'home',
    component: HomeComponent,
    children: [
      {
        path: 'compose',
        component: ComposeComponent
      }
    ]
  },
  {
    path: '**',
    component: HomeComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
