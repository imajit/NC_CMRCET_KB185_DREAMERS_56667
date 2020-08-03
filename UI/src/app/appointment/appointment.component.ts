import { Router } from '@angular/router';
import { FetchDataService } from './../fetch-data.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css']
})
export class AppointmentComponent implements OnInit {

  schoolFilterForm = new FormControl()
  DateFilterForm = new FormControl();
  constructor(private snackbar:MatSnackBar,private fetchData:FetchDataService,private router:Router) { }

  ngOnInit(): void {
  }

  bookDate(){
    if(this.schoolFilterForm.value==""){
      this.snackBarMessage("Please enter a School ID","Dismiss")
    }
    else if(this.DateFilterForm.value==""){
      this.snackBarMessage("Please select a Date","Dismiss");
    }
    else{
      this.snackBarMessage("Appointment Booked","Okay")
    }

  }

  snackBarMessage(message:string,action:string){
    this.snackbar.open(message,action,{
      duration:2000,
    })
  }

  reloadTheSite(){
    this.fetchData.changeClearFilteredData(true);
    this.router.navigate(['']);
  }

}
