import { Component, OnInit, ElementRef ,ViewChild} from '@angular/core';
import * as jspdf from 'jspdf';
import html2canvas from 'html2canvas';
import { DbModel } from '../shared/models/db.model';
import { MatTableDataSource } from '@angular/material/table';
import { FetchDataService } from '../fetch-data.service';
import { cloneDeep } from 'lodash';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { timeout } from 'rxjs/operators';
import { DatePipe } from '@angular/common';
export interface TableData{
  creation_date:Date
  review:string
  school_name:string
}

export interface Review {
  question: string,
  answer: string,
  analysis: number
}

@Component({
  selector: 'app-pdf-maker',
  templateUrl: './pdf-maker.component.html',
  styleUrls: ['./pdf-maker.component.css']
})

export class PdfMakerComponent implements OnInit {
  dataReceived:DbModel[]
  dataSource: MatTableDataSource<TableData>
  SchoolName = "Dummy School"
  SchoolID = "-1"
  SchoolAddress = "address of school"
  OverallReview = "overall review"
  Options:Date[] = []
  SchoolDistrict = "district"
  startDateFilterForm = new FormControl()
  startDateSelected:string=""
  detailedSurveyTable: string[] = ['Question', 'Answer', 'Anaylsis']
  detailedSurveyData:Review[]
  
  improvementTable: string[] = ['Category', 'CurrentScore', 'PrevScore','Improve']
  catergory:string[] = ['Overall Score','Infrastructure','Academic Excellence',
                          'Extra Curricular Activities','Individual Attention',
                          'Life Skills Education','Hygiene','Percentage of female students and faculty',
                          'Facilities for differently abled persons','Values Education']
  ovs:number=0
  inf:number=0
  ace:number=0
  eca:number=0
  ind:number=0
  lyf:number=0
  hyg:number=0
  fem:number=0
  dis:number=0
  val:number=0
  povs:number=0
  pinf:number=0
  pace:number=0
  peca:number=0
  pind:number=0
  plyf:number=0
  phyg:number=0
  pfem:number=0
  pdis:number=0
  pval:number=0

  constructor(private fetchData:FetchDataService,private router:Router,private datePipe: DatePipe) { }

  ngOnInit(): void {
    this.fetchData.watchServerData.subscribe(res=>{
      this.dataReceived=cloneDeep(res);
      console.log("Data received in review table through server data");
      console.log(this.dataReceived);
      
      if(this.dataReceived!=null && this.dataReceived.length!==0){
        this.SchoolName = this.dataReceived[0].SchoolName
        this.SchoolID = this.dataReceived[0].SchoolID
        this.SchoolAddress = this.dataReceived[0].SchoolAddress
        this.SchoolDistrict = this.dataReceived[0].SchoolDistrict
        this.OverallReview = this.dataReceived[0].Records[this.dataReceived[0].Records.length-1].overallReview
        this.Options = this.getOptions()
        this.detailedSurveyData = this.getReview()
        this.setImproveVals()
      }
    })

    this.fetchData.watchFilertedData.subscribe(res=>{
      this.dataReceived=cloneDeep(res);
      console.log("Data received in review  table through filter");
      console.log(this.dataReceived);
      
      if(this.dataReceived!=null && this.dataReceived.length!==0){
        this.SchoolName = this.dataReceived[0].SchoolName
        this.SchoolID = this.dataReceived[0].SchoolID
        this.SchoolAddress = this.dataReceived[0].SchoolAddress
        this.SchoolDistrict = this.dataReceived[0].SchoolDistrict
        this.OverallReview = this.dataReceived[0].Records[this.dataReceived[0].Records.length-1].overallReview
        this.Options = this.getOptions()
        this.detailedSurveyData = this.getReview()
        this.setImproveVals()
      }
    })
  }
  // startDate(val){
  //   this.startDateSelected = this.startDateFilterForm.value;    
  // }
   valueChanged(){
     //console.log(this.startDateSelected)
    this.detailedSurveyData = this.getReview()
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  setImproveVals()
  {
      if(this.dataReceived[0].Records.length==1)
      {
        this.dataReceived[0].Records.forEach(res=>{
          res.questions.forEach(ques=>{
            var cnt=0
            if(ques.category==="Infrastructure"){
            this.inf=this.inf+ques.analysis
            cnt=cnt+1
            }
            this.inf=this.inf/cnt})
          
            
          
        })
      }
  }

  getReview(){
    var ans:Review[] = []
    this.dataReceived.forEach( data => {
      data.Records.forEach(rec=> {
        //console.log(rec.creationDate)
        if(rec.creationDate===this.datePipe.transform(this.startDateSelected,"yyyy-MM-dd"))
        {rec.questions.forEach(ques => {
          ans.push({question:ques.question,answer:ques.answer,analysis:ques.analysis})
        })}
      })
    })
    return ans
  }

  getOptions(): Date[] {
    var ans:Date[] = []
    this.dataReceived.forEach(data => {
        data.Records.forEach( rec => {
          ans.push(new Date(rec.creationDate))
        })
    })
    return ans
  }

  reloadTheSite(){
    
    this.fetchData.changeClearFilteredData(true);
    this.router.navigate(['']);
  }

  }

