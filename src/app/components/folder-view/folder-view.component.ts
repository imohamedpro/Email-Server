import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EmailHeaderResponse } from '../../classes/Responses/EmailHeaderResponse';

@Component({
  selector: 'app-folder-view',
  templateUrl: './folder-view.component.html',
  styleUrls: ['./folder-view.component.css']
})
export class FolderViewComponent implements OnInit {

  emails: EmailHeaderResponse[];
  selected: Set<number>;
  constructor(private router: Router, private r: ActivatedRoute) {
    this.emails = [];
    this.selected = new Set<number>();
   }

  ngOnInit(): void {
    this.emails = [
      {
        id: 0,
        from: 'ggg',
        date: new Date("2019-10-12"),
        priority: 2,
        subject: 'eee',
        isRead: false,
      },
      {
        id: 2,
        from: 'herrg',
        date: new Date("2020-06-05"),
        priority: 1,
        subject: 'fff',
        isRead: true,
      },
    ]
  }

  select(index: number){
    if(this.selected.has(index)){
      this.selected.delete(index);
      console.log(index + " is deselected");

    }else{
      this.selected.add(index);
      console.log(index + " is selected");

    }
  }

  goToEmail(id: number){
    this.router.navigate([id],{relativeTo: this.r});
  }

  delete(index: number){
    console.log(index + " is deleted");
    //call api
    this.emails.splice(index, 1);
  }
  toggleRead(index: number){
    this.emails[index].isRead = !this.emails[index].isRead;
    //call api
  }
}
