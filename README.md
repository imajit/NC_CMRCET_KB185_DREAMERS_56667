# NC_CMRCET_KB185_DREAMERS_56667
Smart India Hackacthon Problem Statement.<br>
PPT Link :-[Click here](https://docs.google.com/presentation/d/12xgqJj5NMbCtr-TLWluyenP1ngB59AIcaHkh0HDI1Ws/edit?usp=sharing)<br>
Demo Video Link :- [Click here](https://youtu.be/nyINPDJ6I-U)

# Problem Statement
Design of GPS enabled online Academic monitoring system to track the classroom transaction and to provide feedback and the review to the teachers to improve their pedagogical practices by the monitoring authorities.

# Need for the project
1.Currently there’s no central monitoring and feedback system for schools.

2.The offline feedback system is very error prone and susceptible for dishonest and fraudulent reviews.

3.To counter that we are proposing an Online centralized feedback system, which solves all the shortcomings of the offline system.  

4.We have designed and made both an Android App and an iOS App to conduct out the surveys.

5.The App uses the combination of Face Picture and biometrics to authenticate the user. We also capture the GPS location of the user while he/she is submitting the survey. If he’s/she’s within the School GPS range, we approve of the survey and records that survey, otherwise we can tell that he’s trying to cheat the system. The Combination of GPS+Face Photo + Biometric is unbreakable and can’t be bypassed.

6.If the user goes to a remote location and records a survey, the app still works and uploads the survey once internet connection becomes available.

7.This way the system can’t be cheated and we have solved the problem of the lack of a proper online feedback system.

8.Impact of the Project : A trustworthy online monitoring and feedback system, can be used to rank and evaluate the performance of the school.


# How to use application?

1.User needs to register firstly giving details like name,mobile number,face image and finger print.

2.Next go to Fill Survey Method, add a valid School ID and take a photo of yourself,this photo will be matched with the photo uploaded at the time of registration. Also the user can’t select a photo from gallery, he has to open the camera and click the photo in real time.GPS snapshot of the current location is also taken so that it is verified that user is present in the school.

3.Then user has to go to questionnaires where all the questions will be shown to user where they can submit answers.There are 9 categories of questions which are 
* infrastructure
* academic excellence
* extra-curricular activities
* individual attention
* life skills education
* percentage of female students and faculty
* facilities for differently abled persons
* values education
* hygiene.

4.Questions are both subjective as well as objective type.

5.FAQs are also there which will help user regarding the use of application.

6.There is separate app for parents also where parents can give review to schools.

7.There is offline capabilities as well which stores the survey data when offline and the users can upload the survey when the internet connectivity restores

# Dashboard

## Build

Run `npm install` to build the project.


## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Highlights of the Dashboard
1) You can see the cumulative score of all schools.

2) A Recent Reviews table is there which shows the results of recently conducted surveys.

3) Best School table is also there, here you can view the ranking of all schools, either by total score or by each category or by district.

4) Performance tracker for each school. We draw a dynamic line chart which tracks the performance of a school across all categories.

5) A PDF Report can be generated for each survey and can be used to compare the performance across each academic year.
