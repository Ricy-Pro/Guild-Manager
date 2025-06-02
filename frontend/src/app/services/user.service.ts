// user.service.ts
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userId: string | null = null;
  private role: string | null = null;
  private username: string | null = null;
  private guildId: string | null = null;
  private guildName: string | null = null;

  constructor() {
    this.loadFromStorage();
  }

  loadFromStorage() {
    this.userId = localStorage.getItem('userId');
    this.role = localStorage.getItem('role');
    this.username = localStorage.getItem('username');
    this.guildId = localStorage.getItem('guildId');
    this.guildName = localStorage.getItem('guildName');
  }

  getUserId(): string | null {
    return this.userId;
  }

  getRole(): string | null {
    return this.role;
  }

  isLeader(): boolean {
    return this.role === 'GUILD_LEADER'; // asigură-te că e exact cum e în backend
  }

  getUsername(): string | null {
    return this.username;
  }

  getGuildId(): string | null {
    return this.guildId;
  }

  getGuildName(): string | null {
    return this.guildName;
  }

  clearUser() {
    localStorage.clear();
    this.userId = null;
    this.role = null;
    this.username = null;
    this.guildId = null;
    this.guildName = null;
  }
}
