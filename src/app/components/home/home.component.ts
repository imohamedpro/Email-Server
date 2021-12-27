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
    console.log("clicked");
    this.router.navigate(['compose'],{relativeTo: this.r});
  }
  goToFolder(){
    console.log("clicked");
    this.router.navigate(['folder'],{relativeTo: this.r});
  }


}
