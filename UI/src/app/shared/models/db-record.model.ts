import { DbQuestionsModel } from './db-questions.model';
export class DbRecordModel{
    creationDate:string;
    gpslocation:{
        lat:number;
        long:number;
    }
    officerID:number;
    overallReview:string;
    questions:DbQuestionsModel[];

}