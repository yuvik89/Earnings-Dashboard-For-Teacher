import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../models/models';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSnackBarModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  credentials: LoginRequest = {
    username: '',
    password: ''
  };
  isLoading = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  onSubmit(): void {
    if (!this.credentials.username || !this.credentials.password) {
      this.snackBar.open('Please fill in all fields', 'Close', { duration: 3000 });
      return;
    }

    this.isLoading = true;
    this.authService.login(this.credentials).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.snackBar.open(`Welcome, ${response.firstName}!`, 'Close', { duration: 3000 });
        this.router.navigate(['/dashboard']);
      },
      error: (error) => {
        this.isLoading = false;
        this.snackBar.open('Invalid username or password', 'Close', { duration: 3000 });
      }
    });
  }
}
