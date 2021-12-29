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
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { EmailViewComponent } from './components/email-view/email-view.component';
import { ContactsFolderComponent } from './components/contacts-folder/contacts-folder.component';
import { ContactEditComponent } from './components/contact-edit/contact-edit.component';
import { HttpClient, HttpClientModule, HttpParams } from '@angular/common/http';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    ComposeComponent,
    HomeComponent,
    EmailEditorComponent,
    FolderViewComponent,
    EmailViewComponent,
    ContactsFolderComponent,
    ContactEditComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
