import { ShowRankingComponent } from './../show-ranking/show-ranking.component';
import { cloneDeep } from 'lodash';
import { DbModel } from './../shared/models/db.model';
import { FetchDataService } from './../fetch-data.service';
import { Component, OnInit,ViewChild } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';


@Component({
  selector: 'app-circlechart',
  templateUrl: './circlechart.component.html',
  styleUrls: ['./circlechart.component.css']
})

export class CirclechartComponent implements OnInit {
  dataReceived:DbModel[]
  public canvasWidth = 300
  public needleValue = 65
  public centralLabel = ''
  public name = 'Score'
  public bottomLabel = '7.0'
  public options = {
      arcColors: ['rgb(63, 81, 181)', 'lightgray'],
      arcDelimiters: [75],
      rangeLabel: ['0', '10'],
      needleStartValue: 50,
  }
  constructor(private fetchData:FetchDataService,public dialog:MatDialog) { }

  openDialog():void{
    const dialogRef = this.dialog.open(ShowRankingComponent,{
      width:'75%',
      height:'75%'
    });
  }

  ngOnInit(): void {
    this.fetchData.watchServerData.subscribe(res=>{
      this.dataReceived=cloneDeep(res);
      console.log("Data received in review table through server data");
      console.log(this.dataReceived);
      
      if(this.dataReceived!=null && this.dataReceived.length!==0){
         this.bottomLabel=this.findScore().toString()
         this.options.arcDelimiters=[this.findScore()*10]
      }
    })
    this.fetchData.watchFilertedData.subscribe(res=>{
      this.dataReceived=cloneDeep(res);
      console.log("Data received in review  table through filter");
      console.log(this.dataReceived);
      
      if(this.dataReceived!=null && this.dataReceived.length!==0){
         this.bottomLabel=this.findScore().toString()
         this.options.arcDelimiters=[this.findScore()*10]
      }

    })
  }

  findScore(){
    var score = 0;
    this.dataReceived.forEach(data => {
      var ivs=0
      data.Records.forEach( res => {
        var pr=0
        res.questions.forEach( sc => {
          pr=pr+sc.analysis
        })
        console.log("the pr is "+pr)
        pr=pr/res.questions.length
        ivs=ivs+pr
      })
      console.log("the ivs is "+ivs)
      ivs=ivs/data.Records.length
      score=score+ivs;
    })  
    console.log("the score is "+score)
    score=+((score/this.dataReceived.length).toFixed(2))
    return score
  }



}
