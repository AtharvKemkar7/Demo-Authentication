import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  protected isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  protected getUsername(): string | null {
    return this.authService.getUsername();
  }

  protected onLogout(): void {
    this.authService.logout();
    void this.router.navigate(['/login']);
  }
}
