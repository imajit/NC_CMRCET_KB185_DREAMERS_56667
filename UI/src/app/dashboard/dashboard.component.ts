import { FetchDataService } from './../fetch-data.service';
import { Component, OnInit } from '@angular/core';
import { DbModel} from '../shared/models/db.model';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private fetchData:FetchDataService) { }
   ngOnInit(): void {
    console.log("Dashboard initialized");
      
  }

}
