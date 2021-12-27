import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http'
import { Observable } from 'rxjs';
import { EmailHeaderResponse } from 'src/app/classes/Responses/EmailHeaderResponse';
import { InitFolderResponse } from 'src/app/classes/Responses/InitFolderResponse';
@Injectable({
  providedIn: 'root'
})
export class ControllerService {
  private readonly apiUrl = 'http://localhost:4050/api/'; 

  constructor(private http: HttpClient) { }

  loadEmails(folderName: string, pageNumber: number, sortGenre: string): Observable<Array<EmailHeaderResponse>>{
    return this.http.get<Array<EmailHeaderResponse>>(`${this.apiUrl}loadEmails`);
  }
  loadInitFolder(folderName: string): Observable<InitFolderResponse>{
    return this.http.get<InitFolderResponse>(`${this.apiUrl}loadFolder`);
  }
}




