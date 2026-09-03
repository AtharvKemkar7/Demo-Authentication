import { Component, inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export interface StatCard {
  label: string;
  value: string;
}

export interface DashboardResponse {
  message: string;
  user: string;
  serverTime: string;
  stats: StatCard[];
}

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);
  private readonly authService = inject(AuthService);

  protected data: DashboardResponse | null = null;
  protected loading = true;
  protected errorMessage = '';

  ngOnInit(): void {
    this.fetchData();
  }

  private fetchData(): void {
    this.loading = true;
    this.errorMessage = '';
    this.http.get<DashboardResponse>('/api/dashboard/data').subscribe({
      next: (response) => {
        this.data = response;
        this.loading = false;
      },
      error: (err) => {
        this.loading = false;
        if (err.status === 401) {
          this.authService.logout();
          void this.router.navigate(['/login']);
        } else {
          this.errorMessage = 'Failed to load dashboard data. Please try again.';
        }
      },
    });
  }
}
