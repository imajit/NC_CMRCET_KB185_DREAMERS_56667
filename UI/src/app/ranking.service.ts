import { DbModel } from './shared/models/db.model';
import { cloneDeep } from 'lodash';
import { FetchDataService } from './fetch-data.service';
import { Injectable, OnInit } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RankingService {

  constructor(private fetchData:FetchDataService) { }

  // hygieneMap:Map<number,number> = new Map();
  // sortedHygieneMap:Map<number,number> = new Map();
  // hygieneRankingMap:Map<number,number> = new Map();

  // interactionMap:Map<number,number> = new Map();
  // sortedInteractionMap:Map<number,number> = new Map();
  // interactionRankingMap:Map<number,number> = new Map();
  // severData:DbModel[] = cloneDeep(this.fetchData.getServerData());


  // fillHygieneMap(){
  //   this.severData.forEach(res=>{
  //     var score = 0  ;
  //     res.Records.forEach(data =>{
  //       data.questions.forEach(dd =>{
  //         if(dd.category==='hygiene'){
  //           score = score + dd.analysis;
  //         }
  //       })
  //     })
  //     score = +((score/res.Records.length).toFixed(2));
  //     this.hygieneMap.set(+res.SchoolID,score);
  //     this.sortedHygieneMap = new Map([...this.hygieneMap.entries()].sort((a,b)=> b[1]-a[1]));
  //     var pos=1;
  //     this.sortedHygieneMap.forEach((value:number,key:number)=>{
  //       this.hygieneRankingMap.set(key,pos);
  //       pos = pos+1;
  //     })
  //   })
  // }

  // fillInteractionMap(){
  //   this.severData.forEach(res=>{
  //     var score = 0  ;
  //     res.Records.forEach(data =>{
  //       data.questions.forEach(dd =>{
  //         if(dd.category==='interaction'){
  //           score = score + dd.analysis;
  //         }
  //       })
  //     })
  //     score = +((score/res.Records.length).toFixed(2));
  //     this.interactionMap.set(+res.SchoolID,score);
  //     this.sortedInteractionMap = new Map([...this.interactionMap.entries()].sort((a,b)=> b[1]-a[1]));
  //     var pos = 1 ;
  //     this.sortedInteractionMap.forEach((value:number,key:number)=>{
  //       this.interactionRankingMap.set(key,pos);
  //       pos = pos+1;
  //     })
  //   })

  // }

  // getHygieneRankingMap(){
  //   return this.hygieneRankingMap;
  // }

  // getInteractionRankingMap(){
  //   return this.interactionRankingMap;
  // }
}
