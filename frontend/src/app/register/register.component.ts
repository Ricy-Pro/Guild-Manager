import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  standalone:false
})
export class RegisterComponent {
  username: string = '';
  password: string = '';
  guildName: string = '';
  asLeader: boolean = true;
  message: string = '';

  constructor(private http: HttpClient, private router: Router) {}
  goToLogin() {
    this.router.navigate(['/login']);
  }
  register() {
    const registerRequest = {
      username: this.username,
      password: this.password,
      guildName: this.guildName,
      asLeader: this.asLeader
    };

    this.http.post<any>('http://localhost:8080/api/auth/register', registerRequest)
      .subscribe({
        next: (response) => {
          this.message = response.message;
          this.router.navigate(['/login']);
        },
        error: (error) => {
          this.message = 'Registration failed. Please try again.';
        }
      });
  }
}
