import {Component, EventEmitter, Input, OnChanges, SimpleChanges, Output, OnInit} from '@angular/core';
import { EventService } from '../../services/events.service';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.css'],
  standalone: false
})export class EventListComponent implements OnInit, OnChanges {

  @Input() guildId!: number;
  @Input() events: any[] = [];
  @Output() selectEvent = new EventEmitter<any>();
  dataLoaded: boolean = false;

  constructor(private eventService: EventService) {}

  ngOnInit(): void {
    if (this.guildId) {
      this.loadEvents();
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['events']) {
      this.dataLoaded = true;
    }
  }

  loadEvents() {
    this.dataLoaded = false;
    this.eventService.getEventsForGuild(this.guildId).subscribe(events => {
      this.events = events;
      this.dataLoaded = true;
    });
  }

  select(event: any) {
    this.selectEvent.emit(event);
  }
}

