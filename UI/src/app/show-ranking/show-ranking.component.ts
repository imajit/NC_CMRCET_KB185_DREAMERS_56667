import { cloneDeep } from 'lodash';
import { DbModel } from './../shared/models/db.model';
import { RankingService } from './../ranking.service';
import { FetchDataService } from './../fetch-data.service';
import { Component, OnInit } from '@angular/core';
import { ChartOptions, ChartType, ChartDataSets } from 'chart.js';
import { Label } from 'ng2-charts';
import { TOUCH_BUFFER_MS } from '@angular/cdk/a11y';

@Component({
  selector: 'app-show-ranking',
  templateUrl: './show-ranking.component.html',
  styleUrls: ['./show-ranking.component.css']
})
export class ShowRankingComponent implements OnInit {

  constructor(private fetchData:FetchDataService,private rankings:RankingService) { }

  dataReceived:DbModel[]=[];
  categoriesArray:string[]=["Infrastructure", 
                            "Academic Excellence",
                            "Extra Curricular activities",
                            "Individual attention",
                            "Life Skills education",
                            "Percentage of female students and faculty",
                            "Facilities for differently abled persons",
                            "Values Education",
                            "Hygiene"]

  scoreArray:number[]=[];
  hygieneRankingMapForGraph:Map<number,number> = new Map();
  interactionRankingMapForGraph:Map<number,number> = new Map();


  hygieneMap:Map<number,number> = new Map();
  sortedHygieneMap:Map<number,number> = new Map();
  hygieneRankingMap:Map<number,number> = new Map();

  interactionMap:Map<number,number> = new Map();
  sortedInteractionMap:Map<number,number> = new Map();
  interactionRankingMap:Map<number,number> = new Map();

  hygieneScore:number=0;
  interactionScore:number=0;

  barChartOptions: ChartOptions = {
    responsive: true,
    scales: { xAxes: [{
      ticks:{
        beginAtZero:true,
        stepSize:1,
        min:0,
        max:10
      }
    }], yAxes: [{
      ticks:{
        beginAtZero:true,
        min:0,
      }
    }] },
    maintainAspectRatio:false,
  };
  barChartLabels: Label[] = ['2013', '2014', '2015', '2016', '2017', '2018','2019'];
  barChartType: ChartType = 'horizontalBar';
  barChartLegend = true;
  barChartPlugins = [];

  barChartData: ChartDataSets[] = [
    { data: [2500, 5900, 6000, 8100, 8600, 8050, 1200], label: 'Company A',barThickness:10,backgroundColor:['#13185c','#de28de','red','#19751a','yellow','#32e634','#4fd4e0','#0f5c26','#783412']},
  ];

  ngOnInit(): void {
    this.fetchData.watchServerData.subscribe(res=>{
      // console.log("Initial data got");
      // console.log(res);
      
      this.dataReceived = cloneDeep(res);
      // if(this.dataReceived!=null){
      // this.fillHygieneMap();
      // this.fillInteractionMap();
      // console.log("Show ranking called");
      // }
      
      // this.hygieneRankingMapForGraph = cloneDeep(this.rankings.getHygieneRankingMap());
    })

    this.fetchData.watchFilertedData.subscribe(res=>{
      // console.log("res got");
      // console.log(res);
      
      
      this.dataReceived = cloneDeep(res);
      // this.hygieneRankingMapForGraph = cloneDeep(this.rankings.getHygieneRankingMap());
      // this.interactionRankingMapForGraph = cloneDeep(this.rankings.getInteractionRankingMap());
      if(this.dataReceived!=null && this.dataReceived.length===1){
        this.fillCategoricalData();
      }

    })

  }



  fillHygieneMap(){
    this.dataReceived.forEach(res=>{
      var score = 0  ;
      res.Records.forEach(data =>{
        data.questions.forEach(dd =>{
          if(dd.category==='hygiene'){
            score = score + dd.analysis;
          }
        })
      })
      score = +((score/res.Records.length).toFixed(2));
      this.hygieneMap.set(+res.SchoolID,score);
      this.sortedHygieneMap = new Map([...this.hygieneMap.entries()].sort((a,b)=> b[1]-a[1]));
      var pos=1;
      this.sortedHygieneMap.forEach((value:number,key:number)=>{
        this.hygieneRankingMap.set(key,pos);
        pos = pos+1;
      })
    })
  }

  fillInteractionMap(){
    this.dataReceived.forEach(res=>{
      var score = 0  ;
      res.Records.forEach(data =>{
        data.questions.forEach(dd =>{
          if(dd.category==='interaction'){
            score = score + dd.analysis;
          }
        })
      })
      score = +((score/res.Records.length).toFixed(2));
      this.interactionMap.set(+res.SchoolID,score);
      this.sortedInteractionMap = new Map([...this.interactionMap.entries()].sort((a,b)=> b[1]-a[1]));
      var pos = 1 ;
      this.sortedInteractionMap.forEach((value:number,key:number)=>{
        this.interactionRankingMap.set(key,pos);
        pos = pos+1;
      })
    })

  }

  fillCategoricalData(){
    this.scoreArray=[];
    var dataArray:number[]=[];
    var labelArray:string[] = [];
    this.categoriesArray.forEach(category=>{
      var finalScore:number = 0;
      this.dataReceived[0].Records.forEach(res =>{
        var localScore:number = 0;
        var occur:number=0
        res.questions.forEach(data=>{
          if(data.category==category){
            localScore = localScore + data.analysis;
            occur = occur + 1;
          }
        })
        finalScore = finalScore + (+(localScore/occur).toFixed(2));
      })
      finalScore = +((finalScore/this.dataReceived[0].Records.length).toFixed(2));
      dataArray.push(finalScore);
      labelArray.push(category);
    })
    this.barChartData[0].data=dataArray;
    this.barChartData[0].label=this.dataReceived[0].SchoolName;
    this.barChartLabels = labelArray;

  }

}
