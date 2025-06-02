import {Component, Input, Output, EventEmitter, OnChanges, SimpleChanges} from '@angular/core';

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.css'],
  standalone: false
})
export class EventDetailsComponent implements OnChanges {
  @Input() event: any;
  @Input() isLeader: boolean = false;
  @Input() username: string | null = null;

  @Output() joinEvent = new EventEmitter<number>();
  @Output() exitEvent = new EventEmitter<number>();
  @Output() deleteEvent = new EventEmitter<number>();
  @Output() modifyEvent = new EventEmitter<any>();
  eventLoaded = false;
  ngOnChanges(changes: SimpleChanges) {
    if (changes['event']){
      this.eventLoaded = true;
    }
  }

  isParticipant(): boolean {
    return this.event?.participants?.some((p: any) => p.username === this.username);
  }

  isCreator(): boolean {
    return this.event?.createdBy?.username === this.username;
  }

  join() {
    this.joinEvent.emit(this.event.id);
  }

  exit() {
    this.exitEvent.emit(this.event.id);
  }

  delete() {
    this.deleteEvent.emit(this.event.id);
  }

  modify() {
    this.modifyEvent.emit(this.event);
  }
}
