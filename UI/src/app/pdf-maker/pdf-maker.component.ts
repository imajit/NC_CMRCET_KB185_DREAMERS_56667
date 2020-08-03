import { Component, OnInit, ElementRef ,ViewChild} from '@angular/core';
import * as jspdf from 'jspdf';
import html2canvas from 'html2canvas';
import { Color, Label } from 'ng2-charts';
import { DbModel } from '../shared/models/db.model';
import { MatTableDataSource } from '@angular/material/table';
import { FetchDataService } from '../fetch-data.service';
import { cloneDeep } from 'lodash';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { timeout } from 'rxjs/operators';
import { DatePipe } from '@angular/common';
import 'chartjs-plugin-annotation';
import { ChartDataSets, ChartOptions, plugins } from 'chart.js';

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
  
  public lineChartData: ChartDataSets[] = [
    { data: [], label: 'Scores' },
  ]
  public lineChartLabels: Label[] = [];
  public lineChartColors: Color[] = [
    {
      borderColor: '#BACAFE',
      backgroundColor: '#097BFC',
    },
  ]
  public lineChartLegend = true
  public lineChartType = 'line'
  public lineChartPlugins = [{
    beforeDraw(chart, easing) {
      const ctx = chart.ctx;
      const chartArea = chart.chartArea;
      const top = chartArea.top; // Use a value of 0 here to include the legend

      ctx.save();
      ctx.fillStyle = '#F3F5FF';

      ctx.fillRect(chartArea.left, top, chartArea.right - chartArea.left, chartArea.bottom - top);
      ctx.restore();
    }
  }]
  public lineChartOptions:(ChartOptions & {annotation:any})={
    scales:{
      yAxes:[{
        ticks:{
          max:10,
          min:0,
          stepSize:1,
          beginAtZero:true,
          showLabelBackdrop:true,
          fontColor:'#097BFC',
          fontSize:15
        },
        gridLines:{
          zeroLineColor: '#097BFC',
          zeroLineWidth: 2.25
        }
      }],
      xAxes:[{
        ticks:{
          fontColor:'#097BFC',
          fontSize:15
        },
        gridLines:{
          zeroLineColor: '#097BFC',
          zeroLineWidth: 2.25,
        }
      }]
    },
    elements:{
      point:{
        radius:5.5,
        hitRadius:1,
        hoverRadius:4,
        hoverBorderWidth:1,
        borderWidth:2.25,
      },
      line:{
        tension:0,
        fill:false,

      }
    },
    responsive:false,
    maintainAspectRatio:false,
    annotation: {
      drawTime: 'beforeDatasetsDraw',
      annotations: [{
      }]
  }
  }

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
        this.fillOverallData()
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
        this.fillOverallData()
      }
    })
  }

  fillOverallData(){
    var overallArray:number[]=[];
    var labelArray:string[]=[];
    if(this.dataReceived.length===1){
      this.dataReceived[0].Records.forEach(res=>{
        var score=0;
        res.questions.forEach(data=>{
          score= score + data.analysis;
        })
        score=score/(res.questions.length);
        overallArray.push(score);
        var dd:Date = new Date(res.creationDate);
        var dateString:string = dd.toLocaleDateString();
        labelArray.push(dateString);

      })
    }
    this.lineChartData[0].data=overallArray;
    this.lineChartData[0].label="Overall Score";
    this.lineChartLabels=labelArray;
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

