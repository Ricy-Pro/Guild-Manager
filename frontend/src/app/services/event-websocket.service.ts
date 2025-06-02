import { Injectable } from '@angular/core';
import { Client, IMessage } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EventWebSocketService {
  private stompClient: Client;
  private eventUpdatesSubject = new Subject<any>();

  public eventUpdates$ = this.eventUpdatesSubject.asObservable();

  constructor() {
    this.stompClient = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
      reconnectDelay: 5000,
      debug: (str) => console.log(str)
    });

    this.stompClient.onConnect = () => {
      console.log('Connected to WebSocket');
      this.stompClient.subscribe('/topic/events', (message: IMessage) => {
        const body = JSON.parse(message.body);
        this.eventUpdatesSubject.next(body);
      });
    };

    this.stompClient.onStompError = (frame) => {
      console.error('Broker reported error:', frame);
    };
  }

  connect() {
    this.stompClient.activate();
  }

  disconnect() {
    this.stompClient.deactivate(); // corect pentru noile versiuni
  }
}
