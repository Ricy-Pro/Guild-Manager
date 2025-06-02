// src/app/services/event.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private apiUrl = 'http://localhost:8080/api/events';

  constructor(private http: HttpClient, private userService: UserService) {}

  private getHeaders(): HttpHeaders {
    const userId = this.userService.getUserId();
    return new HttpHeaders({ 'userId': userId || '' });
  }

  getEventsForGuild(guildId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/guild/${guildId}`, {
      headers: this.getHeaders()
    });
  }

  createEvent(event: any): Observable<any> {
    return this.http.post(this.apiUrl, event, {
      headers: this.getHeaders()

    });
  }

  joinEvent(eventId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/${eventId}/join`, {}, {
      headers: this.getHeaders()
    });
  }

  exitEvent(eventId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/${eventId}/exit`, {}, {
      headers: this.getHeaders()
    });
  }

  deleteEvent(eventId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${eventId}`, {
      headers: this.getHeaders()
    });
  }

  updateEvent(eventId: number, updated: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${eventId}`, updated, {
      headers: this.getHeaders()
    });
  }
  getEventById(eventId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${eventId}`, {
      headers: this.getHeaders()
    });
  }

}
