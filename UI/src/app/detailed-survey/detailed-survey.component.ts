import { cloneDeep } from 'lodash';
import { DbModel } from './../shared/models/db.model';
import { FetchDataService } from './../fetch-data.service';
import { Component, OnInit,ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

export interface TableData{
  creation_date:Date
  question:string
  answer:string
  analysis:number
}

@Component({
  selector: 'app-detailed-survey',
  templateUrl: './detailed-survey.component.html',
  styleUrls: ['./detailed-survey.component.css']
})
export class DetailedSurveyComponent implements OnInit {
  displayedColumns: string[] = ['creation_date', 'question','answer','analysis'];
  dataSource: MatTableDataSource<TableData>;
  dataReceived:DbModel[]
  @ViewChild(MatPaginator,{static:false}) set content1(paginator:MatPaginator){
    this.dataSource.paginator=paginator;
  }
  @ViewChild(MatSort, {static: false}) set content(sort: MatSort) {
    this.dataSource.sort = sort;
  }

  constructor(private fetchData:FetchDataService) { }

  ngOnInit(): void {
    this.fetchData.watchServerData.subscribe(res=>{
      this.dataReceived=cloneDeep(res);
      console.log("Data received in review table through server data");
      console.log(this.dataReceived);
      
      if(this.dataReceived!=null && this.dataReceived.length!==0){
        var newTableData:TableData[]=  this.createTable();
        this.dataSource = new MatTableDataSource(newTableData);
      }
    })
    this.fetchData.watchFilertedData.subscribe(res=>{
      this.dataReceived=cloneDeep(res);
      console.log("Data received in review  table through filter");
      console.log(this.dataReceived);
      
      if(this.dataReceived!=null && this.dataReceived.length!==0){
        var newTableData:TableData[]=  this.createTable();
        this.dataSource = new MatTableDataSource(newTableData);
      }

    })
    // this.dataSource.paginator = this.paginator;
    // this.dataSource.sort = this.sort;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  createTable(){
    var createdTableData:TableData[]=[];
    console.log("Data received in create Table function");
    console.log(this.dataReceived);
    
    this.dataReceived.forEach(data => {
      data.Records.forEach( res => {
        var dt = new Date(res.creationDate)
        res.questions.forEach( ques => {
          createdTableData.push({creation_date:dt,question:ques.question,answer:ques.answer,analysis:ques.analysis})
        } )
      })
    })
    return createdTableData;
  }

}
