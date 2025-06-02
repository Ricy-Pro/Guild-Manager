import {Component, EventEmitter, Input, Output} from '@angular/core';
import { EventService } from '../../services/events.service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.css'],
  standalone: false
})
export class CreateEventComponent {
  @Output() close = new EventEmitter<void>();
  @Input() guildId!: number
  name = '';
  description = '';
  dateTime = '';
  attendanceLimit: number | null = null;

  constructor(
    private eventService: EventService,
    private userService: UserService
  ) {}

  create() {
    console.log('Create clicked', {
      name: this.name,
      dateTime: this.dateTime,
      guild: this.guildId
    });

    const event = {
      name: this.name,
      description: this.description,
      dateTime: this.dateTime,
      attendanceLimit: this.attendanceLimit,
      guildId: this.guildId
    };

    this.eventService.createEvent(event).subscribe(() => {
      console.log('Event created!');
      this.close.emit();
    });
  }

}
