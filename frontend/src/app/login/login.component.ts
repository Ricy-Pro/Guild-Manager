import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: false

})
export class LoginComponent {
  username: string = '';
  password: string = '';
  message: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  login() {
    const loginRequest = {
      username: this.username,
      password: this.password
    };

    this.http.post<any>('http://localhost:8080/api/auth/login', loginRequest)
      .subscribe({
        next: (response) => {
          this.message = response.message;
          // Store user details (optional, depending on your implementation)
          localStorage.setItem('userId', response.userId);
          localStorage.setItem('role', response.role);
          this.router.navigate(['/dashboard']); // Redirect to a dashboard or home page
        },
        error: (error) => {
          this.message = 'Invalid credentials. Please try again.';
        }
      });
  }
}
