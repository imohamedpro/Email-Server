import { Injectable } from '@angular/core';
import { HttpClient,HttpClientModule, HttpHeaders, HttpParams} from '@angular/common/http'
import { FormGroup } from '@angular/forms';
import { EmailBodyResponse } from 'src/app/classes/Responses/EmailBodyResponse';
import { Email } from 'src/app/classes/Email';
import { Contact } from 'src/app/classes/Contact';
import { ContactSearch } from 'src/app/classes/ContactSearch';
import { ContactAndUsername } from 'src/app/classes/ContactAndUsername';
import { EmailUserClass } from 'src/app/classes/EmailUserClass';
import { MoveEmailClass } from 'src/app/classes/MoveEmailClass';
import { SetFolder } from 'src/app/classes/SetFolder';
import { LoadFolderClass } from 'src/app/classes/LoadFolderClass';
import { Folder } from 'src/app/classes/Folder';
import { UserInfo } from 'src/app/classes/UserInfo';
import { EmailHeader } from 'src/app/classes/EmailHeader';

@Injectable({
  providedIn: 'root'
})
export class ControllerService {
  private readonly apiUrl = 'http://localhost:4050/api/'; 

  constructor(private http: HttpClient) { }
  config = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
  signUp(userInfo: UserInfo){    //done                  
    const body = JSON.stringify(userInfo);                   
    return this.http.post<boolean>(this.apiUrl + 'signup', body, this.config);
  }                                                      

  logIn(userInfo: UserInfo){   //done
    const body = JSON.stringify(userInfo);
    return this.http.post<boolean>(this.apiUrl + 'login', body, this.config);
  }

  getHomeFolders(user: string){ 
    let params = new HttpParams();
    params = params.append('user',user);
    return this.http.get<Array<string>>(this.apiUrl + '/home-folders/', {params});
  }

  getContactPages(user: string, perPage: number){ //done
    let params = new HttpParams();
    params = params.append('user',user);
    params = params.append('perPage', perPage);
    return this.http.get<number>(this.apiUrl + "contact/pages",{params});
  }

  loadContacts(user: string, pageNumber: number, perPage: number, sorted: boolean){ //done
    let params = new HttpParams();
    params = params.append('user', user);
    params = params.append('pageNumber', pageNumber);
    params = params.append('perPage', perPage);
    params = params.append('sorted', sorted);
    return this.http.get<Array<Contact>>(this.apiUrl + 'contact/load', {params});
  }

  getContact(user: string, contactId: number){  //not needed since we can get it with session storage
    let params = new HttpParams();
    params = params.append('user',user);
    params = params.append('id', contactId);
    return this.http.delete<Contact>(this.apiUrl + "contact/get", {params});
  }

  searchContact(user: string, tokens: string, pageNumber: number, perPage: number, sorted: boolean){  //done
    let params = new HttpParams();
    params = params.append('user',user);
    params = params.append('tokens',tokens);
    params = params.append('pageNumber',pageNumber);
    params = params.append('perPage',perPage);
    params = params.append('sorted',sorted);
    return this.http.get<Array<Contact>>(this.apiUrl + "contact/search", {params});
  }

  deleteContact(user: string, contactId: number){   //done
    let params = new HttpParams();
    params = params.append('user',user);
    params = params.append('id', contactId);
    return this.http.delete(this.apiUrl + "contact/delete",{params});
  }

  addContact(contactAndUsername: ContactAndUsername){ //done
    const body = JSON.stringify(contactAndUsername);
    return this.http.post(this.apiUrl + "/contact/add", body, this.config);
  }

  sendEmail(email: Email){
    const body = JSON.stringify(email);
    return this.http.post(this.apiUrl + 'email/send', body, this.config);
  }

  saveDraft(email: Email){ 
    const body = JSON.stringify(email);
    return this.http.post(this.apiUrl + 'email/save-draft', body, this.config);
  }

  /*moveToTrash(email: EmailUserClass){ 
    const body = JSON.stringify(email);
    return this.http.delete(this.apiUrl + 'email/trash', body);
  }*/

  /*deleteEmails(emailId: number, user: string){
    let params = new HttpParams();
    params = params.append('emailId',emailId);
    params = params.append('user', user);
    return this.http.post(this.apiUrl + 'email/delete', {params});
  }*/

  getEmail(emailId: number, user: string){
    let params = new HttpParams();
    params = params.append('id', emailId);
    params = params.append('user', user);
    return this.http.get<Email>(this.apiUrl + 'email/get', {params});
  }

  restorEmails(email: EmailUserClass){
    const body = JSON.stringify(email);
    return this.http.post(this.apiUrl + 'email/restore', body, this.config);
  }

  markAsRead(email: EmailUserClass){
    const body = JSON.stringify(email);
    return this.http.post(this.apiUrl + 'email/read', body, this.config);
  }

  markAsUnread(email: EmailUserClass){
    const body = JSON.stringify(email);
    return this.http.post(this.apiUrl + 'email/unread', body, this.config);
  }

  moveEmails(moveEmailObject: MoveEmailClass){
    const body = JSON.stringify(moveEmailObject);
    return this.http.post(this.apiUrl + 'email/move', body, this.config);
  }

  setFoder(setFolder: SetFolder){
    const body = JSON.stringify(setFolder);
    return this.http.post(this.apiUrl + 'folder/set', body, this.config);
  }

  getFolder(folderId: number, user: string){
    let params = new HttpParams();
    params = params.append('id', folderId);
    params = params.append('user', user);
    return this.http.get<Folder>(this.apiUrl + 'folder/set', {params});
  }

  loadFolder(folderId: number, sortBy: string, reverse: boolean, searchToken: string, pageNumber: number, perPage: number, user: string){
    let params = new HttpParams();
    params = params.append('folderId', folderId);
    params = params.append('sortBy', sortBy);
    params = params.append('reverse', reverse);
    params = params.append('searchToken', searchToken);
    params = params.append('pageNumber', pageNumber);
    params = params.append('perPage', perPage);
    params = params.append('user', user);
    return this.http.get<Array<EmailHeader>>(this.apiUrl + 'folder/load', {params});
  }

  getFolderPages(folderId: number, perPage: number, user: string){
    let params = new HttpParams();
    params = params.append('id', folderId);
    params = params.append('perPage', perPage);
    params = params.append('user', user);
    return this.http.get<number>(this.apiUrl + 'folder/pages', {params});
  }
}

