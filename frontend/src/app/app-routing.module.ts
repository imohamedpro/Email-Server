import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ComposeComponent } from './components/compose/compose.component';
import { ContactEditComponent } from './components/contact-edit/contact-edit.component';
import { ContactsFolderComponent } from './components/contacts-folder/contacts-folder.component';
import { EmailViewComponent } from './components/email-view/email-view.component';
import { FolderViewComponent } from './components/folder-view/folder-view.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { AuthGuard } from './services/guard/auth.guard';

const routes: Routes = [
  {path:'', redirectTo:'login', pathMatch:'full'},
  {path:'login', component:LoginComponent},
  {path:'signup', component:SignupComponent},
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: 'compose',
        component: ComposeComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'contacts',
        component: ContactsFolderComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'contacts/:id',
        component: ContactEditComponent,
        canActivate: [AuthGuard]
      },
      {
        path: ':folder',
        component: FolderViewComponent,
        canActivate: [AuthGuard]
      },
      {
        path: ':folder/drafteditor/:id',
        component: ComposeComponent,
        canActivate: [AuthGuard]
      },
      {
        path: ':folder/:id',
        component: EmailViewComponent,
        canActivate: [AuthGuard]
      },
      
    ]
  },
  {
    path: '**',
    component: LoginComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
