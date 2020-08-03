import { DatePipe } from '@angular/common';
import { Component , OnInit,ViewChild, ElementRef } from '@angular/core';
import { AngularFireDatabase } from '@angular/fire/database'
import { ChartDataSets, ChartOptions, plugins } from 'chart.js';
import { Color, Label } from 'ng2-charts';
import { DbModel } from '../shared/models/db.model';
import { MatTableDataSource } from '@angular/material/table';
import { TableData } from '../bar-chart/bar-chart.component';
import { FetchDataService } from '../fetch-data.service';
import { cloneDeep } from 'lodash';
import 'chartjs-plugin-annotation';

@Component({
  selector: 'app-score-line-chart',
  templateUrl: './score-line-chart.component.html',
  styleUrls: ['./score-line-chart.component.css']
})
export class ScoreLineChartComponent implements OnInit {
  selected:string="overall";
  public lineChartData: ChartDataSets[] = [
    { data: [], label: 'Scores' },
  ];
  public lineChartLabels: Label[] = [];
  public lineChartColors: Color[] = [
    {
      borderColor: '#BACAFE',
      backgroundColor: '#097BFC',
    },
  ];
  public lineChartLegend = true;
  public lineChartType = 'line';
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
  }];
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
    responsive:true,
    maintainAspectRatio:false,
    annotation: {
      drawTime: 'beforeDatasetsDraw',
      annotations: [{
      }]
  }
  }
  dataReceived:DbModel[]
  dataSource: MatTableDataSource<TableData>
  SchoolName = "Dummy School"
  SchoolID = "-1"
  SchoolAddress = "address of school"
  OverallReview = "overall review"
  constructor(private fetchData:FetchDataService,private datepipe:DatePipe) { }

  ngOnInit(): void {
    this.fetchData.watchServerData.subscribe(res=>{
      this.dataReceived=cloneDeep(res);
      console.log("Data received in review table through server data");
      console.log(this.dataReceived);
      
      if(this.dataReceived!=null && this.dataReceived.length!==0){
        this.SchoolName = this.dataReceived[0].SchoolName
        this.SchoolID = this.dataReceived[0].SchoolID
        this.SchoolAddress = this.dataReceived[0].SchoolAddress
        this.OverallReview = this.dataReceived[0].Records[this.dataReceived[0].Records.length-1].overallReview

        if(this.dataReceived.length===1){
          this.fillOverallData();
        }

        // var hygieneArray:number[]=[];
        // var labelArray:string[]=[];
        // if(this.dataReceived.length==1){
        //   this.dataReceived[0].Records.forEach( res =>{
        //     res.questions.forEach(data =>{
        //       if(data.category=="hygiene"){
        //         hygieneArray.push(data.analysis);
        //         labelArray.push(res.creationDate);
        //       }
        //     })
        //   })
        // }
        // this.lineChartData[0].data=hygieneArray;
        // this.lineChartData[0].label="Hygiene"
        // this.lineChartLabels=labelArray;
        
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
        this.OverallReview = this.dataReceived[0].Records[this.dataReceived[0].Records.length-1].overallReview

        if(this.dataReceived.length===1){
          this.fillOverallData();
        }

        // var hygieneArray:number[]=[];
        // var labelArray:string[]=[];
        // if(this.dataReceived.length==1){
        //   this.dataReceived[0].Records.forEach( res =>{
        //     res.questions.forEach(data =>{
        //       if(data.category=="hygiene"){
        //         hygieneArray.push(data.analysis);
        //         var dd:Date = new Date(res.creationDate);
        //         var dateString:string = dd.toLocaleDateString();
        //         labelArray.push(dateString.toString());
        //       }
        //     })
        //   })
        // }
        // this.lineChartData[0].data=hygieneArray;
        // this.lineChartData[0].label="Hygiene";
        // this.lineChartLabels=labelArray;
      }
    })
  }

  valueChanged(){
    if(this.selected==='overall'){
      this.fillOverallData();
    }
    else{
      this.fillCategoricalData(this.selected);
    }
    
  }

  fillHygieneData(){
    var hygieneArray:number[]=[];
        var labelArray:string[]=[];
        if(this.dataReceived.length==1){
          this.dataReceived[0].Records.forEach( res =>{
            res.questions.forEach(data =>{
              if(data.category=="hygiene"){
                hygieneArray.push(data.analysis);
                var dd:Date = new Date(res.creationDate);
                var dateString:string = dd.toLocaleDateString();
                labelArray.push(dateString.toString());
              }
            })
          })
        }
        this.lineChartData[0].data=hygieneArray;
        this.lineChartData[0].label="Hygiene";
        this.lineChartLabels=labelArray;
  }

  fillInteractionData(){
    var interactionArray:number[]=[];
        var labelArray:string[]=[];
        if(this.dataReceived.length==1){
          this.dataReceived[0].Records.forEach( res =>{
            res.questions.forEach(data =>{
              if(data.category=="interaction"){
                interactionArray.push(data.analysis);
                var dd:Date = new Date(res.creationDate);
                var dateString:string = dd.toLocaleDateString();
                labelArray.push(dateString.toString());
              }
            })
          })
        }
        this.lineChartData[0].data=interactionArray;
        this.lineChartData[0].label="Interaction";
        this.lineChartLabels=labelArray;

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
        score=+((score/(res.questions.length)).toFixed(2));
        overallArray.push(score);
        var dd:Date = new Date(res.creationDate);
        var dateString:string = this.datepipe.transform(dd,'MMM d, y');
        labelArray.push(dateString);

      })
    }
    this.lineChartData[0].data=overallArray;
    this.lineChartData[0].label="Overall Score";
    this.lineChartLabels=labelArray;
  }

  fillCategoricalData(category:string){
    var dataArray:number[]=[];
        var labelArray:string[]=[];
        var finalScore:number=0;
        if(this.dataReceived.length==1){
          this.dataReceived[0].Records.forEach( res =>{
            var analysisValue:number=0;
            var occur:number = 0;
            res.questions.forEach(data =>{
              if(data.category==category){
                // dataArray.push(data.analysis);
                analysisValue = analysisValue + data.analysis;
                occur = occur + 1;
                
              }
            })
            finalScore = +((analysisValue/occur).toFixed(2));
            var dd:Date = new Date(res.creationDate);
            var dateString:string = dd.toLocaleDateString();
            labelArray.push(dateString.toString());
            dataArray.push(finalScore);
          })
        }
        this.lineChartData[0].data=dataArray;
        this.lineChartData[0].label=category;
        this.lineChartLabels=labelArray;

  }

}
