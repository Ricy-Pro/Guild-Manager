import { TestBed } from '@angular/core/testing';

import { EventWebsocketService } from './event-websocket.service';

describe('EventWebsocketService', () => {
  let service: EventWebsocketService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventWebsocketService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
