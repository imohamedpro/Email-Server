import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EmailHeader } from '../../classes/EmailHeader';
import { TokensRequest } from '../../classes/Requests/TokensRequest';
import { ControllerService } from '../../services/controller/controller.service';
import { EmailHeaderResponse } from '../../classes/Responses/EmailHeaderResponse';

@Component({
  selector: 'app-folder-view',
  templateUrl: './folder-view.component.html',
  styleUrls: ['./folder-view.component.css']
})
export class FolderViewComponent implements OnInit {
  selectedRadio!: boolean;
  selectedSearching!: any;
  selectedSorting!: string;
  settingsForm!: FormGroup;
  emailHeaders!: EmailHeader[];
  selected: Set<number>;
  pageNumber!: number;
  totalPages!: number;
  tokens!: TokensRequest;
  tokensHaveChanged: boolean = false;
  editingTokens: boolean = false;
  copyIsClicked: boolean = false;
  customPages!: string[];
  constructor(private router: Router, private r: ActivatedRoute, private apiService: ControllerService) {
    this.pageNumber = 1;
    this.selectedRadio = false;
    this.selectedSorting = "date";
    this.emailHeaders = [];
    this.selectedSearching = "";
    r.params.subscribe(val => {
      console.log(this.getPageName());
      this.apiService.getHomeFolders(sessionStorage.getItem('user') as string)
        .subscribe(data => {
          sessionStorage.setItem('customPages', JSON.stringify(data));
        });

      this.apiService.loadFolder(Number.parseInt(this.getPageName() as string), this.selectedSorting, this.selectedRadio, "", 1, 5,
        sessionStorage.getItem('user') as string)
        .subscribe(data => {
          this.emailHeaders = data;
        })
      this.apiService.getFolderPages(Number.parseInt(this.getPageName() as string), 5, sessionStorage.getItem('user') as string)
        .subscribe(data => {
          this.totalPages = data
        })
      this.customPages = JSON.parse(sessionStorage.getItem("customPages") as string);
      console.log(this.customPages);
    });
    this.tokens = {
      values: ["Karim", "Magdy", "Youssef", "Youhanna"],
    }
    this.selected = new Set<number>();
  }

  ngOnInit(): void {

  }
  addToken(event: any) {
    if (event.key == "Enter" || event.key == " ") {
      event.preventDefault();
      if (event.target.value != "") {
        this.tokens.values.push(event.target.value);
        event.target.value = "";
        this.tokensHaveChanged = true;
      }
    }
  }
  deleteToken(index: number) {
    this.tokens.values.splice(index, 1);
    this.tokensHaveChanged = true;
  }
  saveTokens() {
    if (this.tokensHaveChanged) {
      //api here
    }
    this.editingTokens = false;
  }
  updateSorting(e: any) {
    this.selectedSorting = e.target.value;
    console.log(this.selectedSorting);
  }
  updateSearching(e: any) {
    this.selectedSearching = e.target.value;
    this.apiService.loadFolder(Number.parseInt(this.getPageName() as string), this.selectedSorting, this.selectedRadio, this.selectedSearching, 1, 5,
      sessionStorage.getItem('user') as string)
      .subscribe(data => {
        this.emailHeaders = data;
      })
    this.apiService.getFolderPages(Number.parseInt(this.getPageName() as string), 5, sessionStorage.getItem('user') as string)
      .subscribe(data => {
        this.totalPages = data
      })
  }
  updateRadio(b: boolean) {
    this.selectedRadio = b;
  }
  select(index: number, e: any) {
    e.stopPropagation();
    if (this.selected.has(index)) {
      this.selected.delete(index);
      console.log(index + " is deselected");

    } else {
      this.selected.add(index);
      console.log(index + " is selected");

    }
  }
  copySelected(folderID: number) {
    if (this.selected.size != 0) {
      //api call
      console.log("move selected to", `${folderID}`);
      this.selected.clear();
    }
  }
  deleteSelected() {
    if (this.selected.size != 0) {
      //api call
      console.log("selected are deleted");
    }
  }
  clearSelected() {
    if (this.selected.size != 0) {
      this.selected.clear();
    }
  }
  getPageName() {
    return this.r.snapshot.paramMap.get("folder");
  }

  goToEmail(id: number) {
    // if (this.emailHeaders[id].isRead == false) this.emailHeaders[id].isRead = true;
    this.router.navigate([id], { relativeTo: this.r });
  }
  moveForward() {
    if (this.pageNumber < this.totalPages) {
      ++this.pageNumber;
      this.apiService.loadFolder(Number.parseInt(this.getPageName() as string), this.selectedSorting, this.selectedRadio,
       this.selectedSearching, this.pageNumber, 5, sessionStorage.getItem('user') as string)
      .subscribe(data => {
        this.emailHeaders = data;
      })
    this.apiService.getFolderPages(Number.parseInt(this.getPageName() as string), 5, sessionStorage.getItem('user') as string)
      .subscribe(data => {
        this.totalPages = data
      })
    }
  }
  moveBackward() {
    if (this.pageNumber > 1) {
      --this.pageNumber;
      if (this.pageNumber < this.totalPages) {
        ++this.pageNumber;
        this.apiService.loadFolder(Number.parseInt(this.getPageName() as string), this.selectedSorting, this.selectedRadio,
         this.selectedSearching, this.pageNumber, 5, sessionStorage.getItem('user') as string)
        .subscribe(data => {
          this.emailHeaders = data;
        })
      this.apiService.getFolderPages(Number.parseInt(this.getPageName() as string), 5, sessionStorage.getItem('user') as string)
        .subscribe(data => {
          this.totalPages = data
        })
      }
    }
  }
  isCustomFolder() {
    let pageName = this.getPageName();
    if (pageName != "0" && pageName != "1" && pageName != "2" && pageName != "3"
      && pageName != "compose") {
      return true;
    }
    return false;
  }
  delete(index: number, e: any) {
    e.stopPropagation();
    console.log(index + " is deleted");
    //call api
    this.emailHeaders.splice(index, 1);
  }
  toggleRead(index: number, e: any) {
    e.stopPropagation();
    // this.emailHeaders[index].isRead = !this.emailHeaders[index].isRead;
    //call api
  }
}
