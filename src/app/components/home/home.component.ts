import { FoldersInfo } from './../../classes/FoldersInfo';
import { SetFolder } from './../../classes/SetFolder';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ControllerService } from 'src/app/services/controller/controller.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  foldersInfo!: FoldersInfo;
  customFoldersNames!: string[];
  customFoldersIDs!: number[];
  isSelected!: boolean[];
  temp: string = "";
  doNotEdit: number = -1;
  constructor(private router: Router, private r: ActivatedRoute, private apiServie: ControllerService) {
    this.updateCustomFolders();
    // console.log(sessionStorage);
    // r.params.subscribe(val =>{
    //   this.foldersInfo = JSON.parse(sessionStorage.getItem("customPages") as string);
    //   console.log(this.foldersInfo);
    //   if(this.foldersInfo.folderIDs.length > 4){
    //     this.customFoldersNames = this.foldersInfo.folderNames.slice(4, this.foldersInfo.folderNames.length);
    //     this.customFoldersIDs = this.foldersInfo.folderIDs.slice(4, this.foldersInfo.folderIDs.length);
    //   }else{
    //     this.customFoldersNames = [];
    //     this.customFoldersIDs = [];
    //   }
    //   this.isSelected = [];
    // });
  }

  ngOnInit(): void {
  }
  addNewFolder() {
    let folderName: string = `Folder ${this.customFoldersNames.length + 1}`;
    this.customFoldersNames.push(folderName);
    let setFolder: SetFolder = {folderID: -1, folderName: folderName, filterTokens: [], user: sessionStorage.getItem("user") as string};
    this.apiServie.setFolder(setFolder).subscribe(() => {this.updateCustomFolders();});
    //for(let i = 0; i < this.customFolders.length -1 ; ++i){
    this.deSelect(this.customFoldersNames.length - 1);
    //}
    this.isSelected[this.customFoldersNames.length - 1] = true;
  }
  /*editFolderName(event: any){
    if(event.key == "Enter"){
      if(event.target.value == ""){
        this.customFolders[this.customFolders.length-1] = `Folder ${this.customFolders.length}`;
      }else{
        this.customFolders[this.customFolders.length-1] = event.target.value;
      }
      this.isNewFolder[this.customFolders.length-1] = false;
    }
  }*/
  deleteFolder(index: number, e: any) {
    if (index != this.doNotEdit) {
      e.stopPropagation();
      this.customFoldersNames.splice(index, 1);
      this.apiServie.deleteFolder(this.customFoldersIDs[index], sessionStorage.getItem("user") as string).subscribe(() => {this.updateCustomFolders();});
    }
  }
  toggleInput(index: number, e: any) {
    if (index != this.doNotEdit) {
      e.stopPropagation();
      if (this.isSelected[index] == true) {
        if (this.temp == "" && this.customFoldersNames[index].includes("Folder")) {
          this.customFoldersNames[index] = `Folder ${index + 1}`;
        } else if (this.temp == "") {
          this.customFoldersNames[index] = `Folder ${index + 1}`;

        } else {
          this.customFoldersNames[index] = this.temp;
        }
        this.isSelected[index] = false;
        this.temp = "";
        let setFolder: SetFolder = {folderID: this.customFoldersIDs[index], folderName: this.customFoldersNames[index], filterTokens: [], user: sessionStorage.getItem("user") as string};
        this.apiServie.renameFolder(setFolder).subscribe(() => {this.updateCustomFolders();});
      } else {
        this.deSelect(index);
        this.customFoldersNames[index] = `Folder ${index + 1}`;
        this.isSelected[index] = true;
      }
      console.log(this.customFoldersNames[index]);
      //sessionStorage.setItem("customPages",JSON.stringify(this.customFoldersNames));
    }
  }
  deSelect(index: number) {
    for (let i = 0; i < this.customFoldersNames.length; ++i) {
      if (i != index) this.isSelected[i] = false;
    }
  }
  deselectAll() {
    for (let i = 0; i < this.customFoldersNames.length; ++i) {
      this.isSelected[i] = false;
    }
  }
  avoidP(e: any) {
    e.stopPropagation();
  }

  goToCompose() {
    this.deselectAll();
    this.doNotEdit = -1;
    this.router.navigate(['compose'], { relativeTo: this.r });
  }
  goToFolder(folderNumber: number) {
    this.deselectAll();
    this.doNotEdit = -1;
    this.router.navigate([folderNumber], { relativeTo: this.r });
  }
  goToContacts(){
    this.deselectAll();
    this.doNotEdit = -1;
    this.router.navigate(["contacts"], { relativeTo: this.r });
  }
  goToCustomFolder(index: number) {
    this.doNotEdit = index;
    this.deselectAll();
    this.router.navigate([this.customFoldersIDs[index]], { relativeTo: this.r });
  }
  updateCustomFolders(){
    this.apiServie.getHomeFolders(sessionStorage.getItem("user") as string).subscribe(val =>{
      this.foldersInfo = val;
      console.log(this.foldersInfo);
      if(this.foldersInfo.folderIDs.length > 4){
        this.customFoldersNames = this.foldersInfo.folderNames.slice(4, this.foldersInfo.folderNames.length);
        this.customFoldersIDs = this.foldersInfo.folderIDs.slice(4, this.foldersInfo.folderIDs.length);
      }else{
        this.customFoldersNames = [];
        this.customFoldersIDs = [];
      }
      this.isSelected = [];
      console.log(this.foldersInfo);
      console.log(this.customFoldersNames);
      console.log(this.customFoldersIDs);
      sessionStorage.setItem("customFoldersNames", JSON.stringify(this.customFoldersNames));
      sessionStorage.setItem("customFoldersIDs", JSON.stringify(this.customFoldersIDs));
    });
  }
}
