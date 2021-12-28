import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  customFolders!: string[];
  isSelected!: boolean[];
  temp: string = "";
  doNotEdit: number = -1;
  constructor(private router: Router, private r: ActivatedRoute) {
    this.customFolders = [];
    this.isSelected = [];
  }

  ngOnInit(): void {
  }
  addNewFolder() {
    this.customFolders.push(`Folder ${this.customFolders.length + 1}`);
    console.log(this.customFolders);
    //for(let i = 0; i < this.customFolders.length -1 ; ++i){
    this.deSelect(this.customFolders.length - 1);
    //}
    this.isSelected[this.customFolders.length - 1] = true;
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
      this.customFolders.splice(index, 1);
    }
  }
  toggleInput(index: number, e: any) {
    if (index != this.doNotEdit) {
      e.stopPropagation();
      if (this.isSelected[index] == true) {
        if (this.temp == "" && this.customFolders[index].includes("Folder")) {
          this.customFolders[index] = `Folder ${index + 1}`;
        } else if (this.temp == "") {
          this.customFolders[index] = `Folder ${index + 1}`;

        } else {
          this.customFolders[index] = this.temp;
        }
        this.isSelected[index] = false;
        this.temp = "";
      } else {
        this.deSelect(index);
        this.customFolders[index] = `Folder ${index + 1}`;
        this.isSelected[index] = true;
      }
    }
  }
  deSelect(index: number) {
    for (let i = 0; i < this.customFolders.length; ++i) {
      if (i != index) this.isSelected[i] = false;
    }
  }
  deselectAll() {
    for (let i = 0; i < this.customFolders.length; ++i) {
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
    let folderName: string = "";
    switch (folderNumber) {
      case 1:
        folderName = "inbox";
        break;
      case 2:
        folderName = "sent";
        break;
      case 3:
        folderName = "trash";
        break;
      case 4:
        folderName = "draft";
        break;
      case 5:
        folderName = "contacts";
        break;
    }
    this.router.navigate([folderName], { relativeTo: this.r });
  }
  goToCustomFolder(folderNumber: number) {
    this.doNotEdit = folderNumber;
    this.deselectAll();
    this.router.navigate([folderNumber], { relativeTo: this.r });
  }
}
