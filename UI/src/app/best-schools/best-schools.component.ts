import { DbModel } from './../shared/models/db.model';
import { FetchDataService } from './../fetch-data.service';
import { Component, OnInit,ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {cloneDeep} from 'lodash';

export interface TableData{
  school_name:string
  school_score:Number
  creation_date:Date
  school_id:string
  district:string
}

@Component({
  selector: 'app-best-schools',
  templateUrl: './best-schools.component.html',
  styleUrls: ['./best-schools.component.css']
})
export class BestSchoolsComponent implements OnInit {
  displayedColumns: string[] = ['id','school_name', 'school_id','school_score','creation_date','district'];
  dataSource: MatTableDataSource<TableData>;
  serverData:DbModel[];
  @ViewChild(MatPaginator,{static:false}) set content1(paginator:MatPaginator){
    this.dataSource.paginator=paginator;
  }
  @ViewChild(MatSort, {static: false}) set content(sort: MatSort) {
    this.dataSource.sort = sort;
  }
  dataReceived:DbModel[]=[]

  constructor(private fetchData:FetchDataService) { }

  ngOnInit() {
    console.log("Creating best school table");

    this.fetchData.watchServerData.subscribe(res=>{
      this.dataReceived=cloneDeep(res);
      console.log("Data received in best school table");
      console.log(this.dataReceived);
      
      if(this.dataReceived!=null && this.dataReceived.length!==0){
        var newTableData:TableData[]=  this.createTable();
        this.dataSource = new MatTableDataSource(newTableData);
        // this.dataSource.paginator = this.paginator;
        // this.dataSource.sort = this.sort;
      }
    })
    this.fetchData.watchFilertedData.subscribe(res=>{
      this.dataReceived=cloneDeep(res);
      console.log("Data received in review  table through filter");
      console.log(this.dataReceived);
      
      if(this.dataReceived!=null && this.dataReceived.length!==0){
        var newTableData:TableData[]=  this.createTable();
        this.dataSource = new MatTableDataSource(newTableData);
        // this.dataSource.paginator = this.paginator;
        // this.dataSource.sort = this.sort;
      }

    })
    // this.dataSource.paginator = this.paginator;
    // this.dataSource.sort = this.sort;

    this.fetchData.watchFilertedData.subscribe(res=>{
      console.log("Best school through filter");
      this.dataReceived=cloneDeep(res);
      console.log(this.dataReceived);
      
      if(this.dataReceived!=null && this.dataReceived.length!==0){
        var newTableData:TableData[]=  this.createTable();
        this.dataSource = new MatTableDataSource(newTableData);
        // this.dataSource.paginator = this.paginator;
        // this.dataSource.sort = this.sort;
      }
    })
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
    this.dataReceived.forEach(data => {
      var schoolName = data.SchoolName
      var school_score = 0
      var creationDate
      var id = data.SchoolID;
      var schoolDistrict = data.SchoolDistrict;
      data.Records.forEach( res => {
        var quesOverallScore=0;
        res.questions.forEach(ques =>{
          quesOverallScore = quesOverallScore + ques.analysis;
        })
        school_score = school_score + quesOverallScore/(res.questions.length)
        creationDate= new Date(res.creationDate)
      })
      school_score=+((school_score/(data.Records.length)).toFixed(2));
      createdTableData.push({creation_date:creationDate,school_name:schoolName,school_score:school_score,school_id:id,district:schoolDistrict})
    })

    createdTableData.sort((a:TableData,b:TableData)=>{
      return (+b.school_score - (+a.school_score));
    })

    return createdTableData;
  }
}
