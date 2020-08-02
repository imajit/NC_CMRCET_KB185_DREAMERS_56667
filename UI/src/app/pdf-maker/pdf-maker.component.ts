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
export interface TableData{
  creation_date:Date
  review:string
  school_name:string
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
  OverallScore = 0
  OverallPrevScore = 0
  HygeneScore = 0
  HygenePrevScore = 0
  constructor(private fetchData:FetchDataService,private router:Router) { }

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
      }
    })
  }
  startDateSelected:string="";
  startDate(val){
    this.startDateSelected = this.startDateFilterForm.value;    
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
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

  loadImage(url) {
    return new Promise((resolve) => {
      console.log("sasas")
      let img = new Image()
      img.onload = () => resolve(img)
      img.src = url
      console.log("sasas")
    })
  }

  convetToPDF()
  {
    console.log(this.dataReceived)
    console.log("I was clicked")
    var data = document.getElementById('contentToConvert')
    console.log(data)
    html2canvas(data).then(canvas => {
    var imgWidth = 208
    var pageHeight = 295
    console.log("sasas")
    var imgHeight = canvas.height * imgWidth / canvas.width
    var heightLeft = imgHeight
    this.loadImage(canvas.toDataURL('image/png')).then((logo) => {
      console.log("sasas")
      let pdf = new jspdf('p', 'mm', 'a4')
      var position = 0
      pdf.addImage(logo, 'PNG', 0, position, imgWidth, imgHeight)
      pdf.save('new-file.pdf')
    })
    
  })
  }
  reloadTheSite(){
    
    this.fetchData.changeClearFilteredData(true);
    this.router.navigate(['']);
  }

  }

