import { HttpHelperService } from './../http-helper.service'
import {ChartDataSets,ChartType,ChartOptions} from 'chart.js'
import {Label} from 'ng2-charts'
import { DbModel } from './../shared/models/db.model'
import { FetchDataService } from './../fetch-data.service'
import { Component, OnInit,ViewChild } from '@angular/core'
import {MatTableDataSource} from '@angular/material/table'
import {cloneDeep} from 'lodash';

export interface TableData{
  oanswer1:Date
  oanswer2:string
  sanalysis1:string
  sanalysis2:string
  avg_score:string
}

@Component({
  selector: 'app-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.css']
})

export class BarChartComponent implements OnInit {

  dataReceived:DbModel[]=[];
  dataSource: MatTableDataSource<TableData>
  hygiene_score = 0
  happiness_score = 0
  infrastructure_score = 0
  teacher_student_score = 0

  barChartOptions: ChartOptions = {
    responsive: true,
    scales: { xAxes: [{}], yAxes: [{
      ticks:{
        beginAtZero:true,
        min:0,
      }
    }] },
    maintainAspectRatio:false,
  };
  barChartLabels: Label[] = ['2013', '2014', '2015', '2016', '2017', '2018','2019','2013', '2014', '2015', '2016', '2017', '2018','2019'];
  barChartType: ChartType = 'bar';
  barChartLegend = true;
  barChartPlugins = [];

  barChartData: ChartDataSets[] = [
    { data: [2500, 5900, 6000, 8100, 8600, 8050, 1200,2500, 5900, 6000, 8100, 8600, 8050, 1200], label: 'Company A',barPercentage:1,barThickness:10 ,categoryPercentage:1},
  ];
  constructor(private fetchData:FetchDataService) { }

  ngOnInit(): void {
    this.fetchData.watchServerData.subscribe(res=>{
      this.dataReceived=cloneDeep(res);
      console.log("Data received in bar chart through server");
      console.log(this.dataReceived);
      
      if(this.dataReceived!=null && this.dataReceived.length!==0){
        this.fillData();
      }
    })
    this.fetchData.watchFilertedData.subscribe(res=>{
      this.dataReceived=cloneDeep(res);
      console.log("Data received in bar chart by filter");
      console.log(this.dataReceived);
      
      if(this.dataReceived!=null && this.dataReceived.length!==0){
        this.fillData();
       
      }

    })
  }

  fillData(){
    interface BarGraphData{
      [UploadedDate:string]:number
    }
    var dataFilled:Map<string,number> = new Map();

    this.dataReceived.forEach(res=>{
      res.Records.forEach(data =>{
        var dateval = data.creationDate;
        if(dataFilled.has(dateval)===false){
          dataFilled.set(dateval,1);
        }
        else{
          var prevVal = dataFilled.get(dateval);
          prevVal = prevVal +1;
          dataFilled.set(dateval,prevVal);
        }
      })
    })
    console.log("Printing values");
    var dataArray:number []=[];
    var labelArray:string []=[];
    dataFilled.forEach((value:number,key:string)=>{
      console.log(key,value);
      dataArray.push(value);
      labelArray.push(key);
    })

    this.barChartData[0].data = dataArray;
    this.barChartData[0].label = 'Number of Surveys Recorded on a Particular Day'
    this.barChartLabels = labelArray;
  }

}
