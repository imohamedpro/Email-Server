import { NgModule } from '@angular/core';

import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { ComposeComponent } from './components/compose/compose.component';
import { HomeComponent } from './components/home/home.component';
import { EmailEditorComponent } from './components/email-editor/email-editor.component';
import { FolderViewComponent } from './components/folder-view/folder-view.component';

import { ReactiveFormsModule } from '@angular/forms';
import { EmailViewComponent } from './components/email-view/email-view.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    ComposeComponent,
    HomeComponent,
    EmailEditorComponent,
    FolderViewComponent,
    EmailViewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
