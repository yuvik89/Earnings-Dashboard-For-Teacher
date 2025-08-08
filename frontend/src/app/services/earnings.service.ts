import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EarningsSummary, StudentEarnings, UpcomingSession } from '../models/models';

@Injectable({
  providedIn: 'root'
})
export class EarningsService {
  private apiUrl = 'http://localhost:8080/api/earnings';

  constructor(private http: HttpClient) {}

  getMonthlyBreakdown(studentId?: number): Observable<{[key: string]: number}> {
    let params = new HttpParams();
    if (studentId) {
      params = params.set('studentId', studentId.toString());
    }
    
    return this.http.get<{[key: string]: number}>(`${this.apiUrl}/monthly-breakdown`, { params });
  }

  getEarningsSummary(studentId?: number, month?: number, year?: number): Observable<EarningsSummary> {
    let params = new HttpParams();
    if (studentId) {
      params = params.set('studentId', studentId.toString());
    }
    if (month && year) {
      params = params.set('month', month.toString());
      params = params.set('year', year.toString());
    }
    return this.http.get<EarningsSummary>(`${this.apiUrl}/summary`, { params });
  }

  getStudentEarnings(): Observable<StudentEarnings[]> {
    return this.http.get<StudentEarnings[]>(`${this.apiUrl}/students`);
  }

  getUpcomingSessions(studentId?: number): Observable<UpcomingSession[]> {
    let params = new HttpParams();
    if (studentId) {
      params = params.set('studentId', studentId.toString());
    }
    return this.http.get<UpcomingSession[]>(`${this.apiUrl}/upcoming-sessions`, { params });
  }
}
