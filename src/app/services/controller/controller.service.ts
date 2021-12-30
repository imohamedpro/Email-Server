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
import { EmailHeader } from '../../classes/EmailHeader';
import { FoldersInfo } from 'src/app/classes/FoldersInfo';

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
    params = params.append('user',user);
    return this.http.get<Array<FoldersInfo>>(this.apiUrl + 'home-folders/', {params});
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
    return this.http.post(this.apiUrl + "contact/add", body, this.config);
  }

  sendEmail(email: Email){
    const body = JSON.stringify(email);
    console.log(body);
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
  getEmail(emailId: string, user: string){
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
    params = params.append('folderID', folderId);
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

  renameFolder(folder: SetFolder){
    const body = JSON.stringify(folder);
    return this.http.put(this.apiUrl + 'folder/rename', body);
  }

  editFolderToken(folder: SetFolder){
    const body = JSON.stringify(folder);
    return this.http.put(this.apiUrl + 'folder/editFilterTokens', body);
  }

  getFilterTokens(folderId: number, user: string){
    let params = new HttpParams();
    params = params.append('id', folderId);
    params = params.append('user',user)
    return this.http.get(this.apiUrl + 'folder/getFilterTokens');
  }


}

