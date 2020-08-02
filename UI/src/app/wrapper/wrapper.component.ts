import { FetchDataService } from './../fetch-data.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-wrapper',
  templateUrl: './wrapper.component.html',
  styleUrls: ['./wrapper.component.css']
})
export class WrapperComponent implements OnInit {

  constructor(private fetchData:FetchDataService) { }

  ngOnInit(): void {
  }

  reloadTheSite(){
    this.fetchData.changeClearFilteredData(true);
  }
}
