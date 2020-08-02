import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
@Injectable({
  providedIn: 'root'
})
export class HttpHelperService {

  constructor(private http: HttpClient) { }
  serverRes:any
  async getData(){
    return await this.http.get('https://raw.githubusercontent.com/rounak06/test/master/example.json').toPromise();
    
  }

}
