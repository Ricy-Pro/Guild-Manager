import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { EventService } from '../../services/events.service';
import { EventWebSocketService } from '../../services/event-websocket.service';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  standalone: false
})
export class DashboardComponent implements OnInit {
  events: any[] = [];
  isLeader = false;
  selectedEvent: any;
  showCreateForm = false;
  guildId = 1;
  username: string | null = null;
  guildName: string | null = null;
  constructor(
    private userService: UserService,
    private eventService: EventService,
    private wsService: EventWebSocketService
  ) {}

  ngOnInit() {
    this.isLeader = this.userService.isLeader();
    this.username = this.userService.getUsername();
    this.guildName = this.userService.getGuildName();
    const guildId = this.userService.getGuildId();

    if (guildId) {
      this.guildId = parseInt(guildId);
      this.loadEvents();
    }

    this.wsService.connect(); // conectează WS-ul

    this.wsService.eventUpdates$.subscribe(update => {
      console.log('Event WebSocket update received:', update);

      // Poți personaliza logica în funcție de update.action (add/update/delete)
      this.loadEvents();

      if (this.selectedEvent?.id === update.zbor?.id || this.selectedEvent?.id === update.id) {
        this.refreshSelectedEvent(this.selectedEvent.id);
      }
    });
  }



  loadEvents() {
    this.eventService.getEventsForGuild(this.guildId).subscribe(events => {
      this.events = events;
    });
  }

  onSelectEvent(event: any) {
    this.selectedEvent = event;
  }

  openCreateEvent() {
    this.showCreateForm = true;
  }

  closeCreateEventForm() {
    this.showCreateForm = false;
    this.loadEvents();
  }

  join(eventId: number) {
    this.eventService.joinEvent(eventId).subscribe(() => {
      this.loadEvents();

      if (this.selectedEvent?.id === eventId) {
        this.selectedEvent=null;
        this.refreshSelectedEvent(eventId);
      }
    });
  }

  exit(eventId: number) {
    this.eventService.exitEvent(eventId).subscribe(() => {
      this.loadEvents();

      if (this.selectedEvent?.id === eventId) {
        this.selectedEvent=null;
        this.selectedEvent=
        this.refreshSelectedEvent(eventId);
      }
    });
  }


  refreshSelectedEvent(eventId: number) {
    this.eventService.getEventById(eventId).subscribe(event => {
      this.selectedEvent = event;
    });
  }

  delete(eventId: number) {
    this.eventService.deleteEvent(eventId).subscribe(() => {
      this.loadEvents();
      this.selectedEvent = null;
    });

  }
  onModify(event: any) {
    // Poți seta un formular de editare sau naviga către componenta de update
    console.log('Modify requested for event:', event);
    // exemplu: this.editingEvent = event;
  }

}
