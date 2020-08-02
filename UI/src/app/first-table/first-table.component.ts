import { cloneDeep } from 'lodash';
import { DbModel } from './../shared/models/db.model';
import { FetchDataService } from './../fetch-data.service';
import { Component, OnInit,ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
export interface TableData{
  serial_number:number
  review:string;
}

@Component({
  selector: 'app-first-table',
  templateUrl: './first-table.component.html',
  styleUrls: ['./first-table.component.css']
})
export class FirstTableComponent implements OnInit {
  displayedColumns: string[] = ['serial_number', 'review'];
  dataSource: MatTableDataSource<TableData>;
  serverData:DbModel[];
  dataReceived:DbModel[]=[];
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(private fetchData:FetchDataService) { }

  ngOnInit() {
    // console.log("Table in first table");
    
    this.fetchData.watchServerData.subscribe(res=>{
      this.serverData=cloneDeep(res);
      // console.log("Data received in first table");
      // console.log(this.serverData);
      
      if(this.serverData!=null && this.serverData.length!==0){
        var newTableData:TableData[]=  this.createTable();
        this.dataSource = new MatTableDataSource(newTableData);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      }
    })

    this.fetchData.watchFilertedData.subscribe(res=>{
      this.dataReceived = cloneDeep(res);
      if(this.dataReceived.length!==0){
        var newTableData:TableData[]=this.createTable();
      }
    })
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  createTable(){

    var singleInstance:DbModel =this.serverData[0];
    // var serial_number = 1;
    var createdTableData:TableData[]=[];
    // singleInstance.records.forEach(res=>{
    //   var field1 = serial_number;
    //   var field2 = res.overall_review;
    //   serial_number = serial_number +1;
    //   createdTableData.push({serial_number:field1,review:field2});
    // })
    return createdTableData;
  }
}
