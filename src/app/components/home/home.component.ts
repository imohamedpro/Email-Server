import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  constructor(private router: Router, private r: ActivatedRoute) { }

  ngOnInit(): void {
  }

  goToCompose(){
    this.router.navigate(['compose'],{relativeTo: this.r});
  }
  goToFolder(folderNumber: number){
    let folderName: string = ""; 
    switch(folderNumber){
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
        folderName="contacts";
        break;
    }
    this.router.navigate([folderName],{relativeTo: this.r});
  }
}
