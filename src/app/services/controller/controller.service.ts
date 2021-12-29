import { Injectable } from '@angular/core';
import { HttpClient,HttpClientModule, HttpHeaders, HttpParams} from '@angular/common/http'
import { FormGroup } from '@angular/forms';
import { EmailBodyResponse } from '../../classes/Responses/EmailBodyResponse';
import { Email } from '../../classes/Email';
import { Contact } from '../../classes/Contact';
import { ContactSearch } from '../../classes/ContactSearch';
import { ContactAndUsername } from '../../classes/ContactAndUsername';
import { EmailUserClass } from '../../classes/EmailUserClass';
import { MoveEmailClass } from '../../classes/MoveEmailClass';
import { SetFolder } from '../../classes/SetFolder';
import { LoadFolderClass } from '../../classes/LoadFolderClass';
import { Folder } from '../../classes/Folder';
import { Observable } from 'rxjs';
import { UserInfo } from '../../classes/UserInfo';

@Injectable({
  providedIn: 'root'
})
export class ControllerService {
  private readonly apiUrl = 'http://localhost:8082/api/'; 

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
    params.append('user',user);
    return this.http.get<Array<string>>(this.apiUrl + '/home-folders/', {params});
  }

  getContactPages(user: string, perPage: number){
    let params = new HttpParams();
    params.append('user',user);
    params.append('perPage', perPage);
    return this.http.get<number>(this.apiUrl + "contact/pages",{params});
  }

  loadContacts(user: string, pageNumber: number, perPage: number, sorted: boolean){
    let params = new HttpParams();
    params.append('user', user);
    params.append('pageNumber', pageNumber);
    params.append('perPage', perPage);
    params.append('sorted', sorted);
    return this.http.get<Array<Contact>>(this.apiUrl + 'contact/load', {params});
  }

  getContact(user: string, contactId: number){  //not needed since we can get it with session storage
    let params = new HttpParams();
    params.append('user',user);
    params.append('id', contactId);
    return this.http.delete<Contact>(this.apiUrl + "contact/get", {params});
  }

  /*searchContact(contactSearch: ContactSearch){
    const body = JSON.stringify(contactSearch);
    return this.http.get<Array<Contact>>(this.apiUrl + "contact/search", body);
  }*/

  deleteContact(user: string, contactId: number){
    let params = new HttpParams();
    params.append('user',user);
    params.append('id', contactId);
    return this.http.delete<Contact>(this.apiUrl + "contact/delete",{params});
  }

  addContact(contactAndUsername: ContactAndUsername){ //done
    const body = JSON.stringify(contactAndUsername);
    return this.http.post(this.apiUrl + "/contact/add", body);
  }

  sendEmail(email: Email){
    const body = JSON.stringify(email);
    return this.http.post(this.apiUrl + 'email/send', body);
  }

  saveDraft(email: Email){ 
    const body = JSON.stringify(email);
    return this.http.post(this.apiUrl + 'email/save-draft', body);
  }

  /*moveToTrash(email: EmailUserClass){ 
    const body = JSON.stringify(email);
    return this.http.delete(this.apiUrl + 'email/trash', body);
  }*/

  /*deleteEmails(emailId: number, user: string){
    let params = new HttpParams();
    params.append('emailId',emailId);
    params.append('user', user);
    return this.http.post(this.apiUrl + 'email/delete', {params});
  }*/
  uploadAttachment(file: File | undefined, emailID: string  | null, user: string | null){
    // const params = new HttpParams();
    if(user === null || emailID === null || file === undefined) return;
    const params = new FormData();
    // f.append('file', file);
    params.append("file", file);
    params.append('user', user);
    params.append('emailID', emailID);
    params.append('fileName', file.name);
    return this.http.post(`${this.apiUrl}attachment/upload`, params);
  }

  downloadAttachment(fileName: string, emailID: string, user: string){
    let params = new HttpParams();
    params = params.append("user", user);
    params = params.append("emailID", emailID);
    params = params.append("fileName", fileName);
    return this.http.get<any>(`${this.apiUrl}attachment/download`, {params});
  }

  deleteAttachment(fileName: string, emailID: string, user: string): Observable<string>{
    // let headers =  new HttpHeaders({
    //   'Content-Type':  'application/json',
    // });
    let params = new HttpParams();
    params = params.append("user", user);
    params = params.append("emailID", emailID);
    params = params.append("fileName", fileName);
    console.log(params);
    return this.http.delete<string>(`${this.apiUrl}attachment/delete`, {params});
  }
  getEmail(emailId: number, user: string){
    let params = new HttpParams();
    params.append('id', emailId);
    params.append('user', user);
    return this.http.get<Email>(this.apiUrl + 'email/get', {params});
  }

  restorEmails(email: EmailUserClass){
    const body = JSON.stringify(email);
    return this.http.post(this.apiUrl + 'email/restore', body);
  }

  markAsRead(email: EmailUserClass){
    const body = JSON.stringify(email);
    return this.http.post(this.apiUrl + 'email/read', body);
  }

  markAsUnread(email: EmailUserClass){
    const body = JSON.stringify(email);
    return this.http.post(this.apiUrl + 'email/unread', body);
  }

  moveEmails(moveEmailObject: MoveEmailClass){
    const body = JSON.stringify(moveEmailObject);
    return this.http.post(this.apiUrl + 'email/move', body);
  }

  setFoder(setFolder: SetFolder){
    const body = JSON.stringify(setFolder);
    return this.http.post(this.apiUrl + 'folder/set', body);
  }

  getFolder(folderId: number, user: string){
    let params = new HttpParams();
    params.append('id', folderId);
    params.append('user', user);
    return this.http.get<Folder>(this.apiUrl + 'folder/set', {params});
  }

  /*loadFolder(loadFolderClass: LoadFolderClass){
    const body = JSON.stringify(loadFolderClass);
    return this.http.get(this.apiUrl + 'folder/load', body);
  }*/

  getFolderPages(folderId: number, perPage: number, user: string){
    let params = new HttpParams();
    params.append('id', folderId);
    params.append('perPage', perPage);
    params.append('user', user);
    return this.http.get<number>(this.apiUrl + 'folder/pages', {params});
  }
}

