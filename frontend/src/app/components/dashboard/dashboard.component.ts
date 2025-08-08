import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatDividerModule } from '@angular/material/divider';
import { MatMenuModule } from '@angular/material/menu';
import { FormsModule } from '@angular/forms';

import { AuthService } from '../../services/auth.service';
import { EarningsService } from '../../services/earnings.service';
import { 
  LoginResponse, 
  EarningsSummary, 
  StudentEarnings, 
  UpcomingSession 
} from '../../models/models';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatToolbarModule,
    MatButtonModule,
    MatCardModule,
    MatSelectModule,
    MatTableModule,
    MatTabsModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatSidenavModule,
    MatDividerModule,
    MatMenuModule
  ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  currentUser: LoginResponse | null = null;
  earningsSummary: EarningsSummary | null = null;
  studentEarnings: StudentEarnings[] = [];
  allStudents: StudentEarnings[] = []; // Keep all students for dropdown
  upcomingSessions: UpcomingSession[] = [];
  monthlyBreakdown: {[key: string]: number} = {};
  selectedStudentId: number | null = null;
  selectedMonth: number | null = null;
  selectedYear: number | null = null;
  isLoading = true;
  
  // Sidenav control
  sidenavOpened = true;
  
  // Statistics data
  totalStudents = 0;
  totalSessions = 0;
  upcomingEarnings = 0;
  
  // Navigation menu items
  menuItems = [
    { icon: 'dashboard', label: 'Dashboard', active: true },
    { icon: 'people', label: 'Students', active: false },
    { icon: 'event', label: 'Sessions', active: false },
    { icon: 'attach_money', label: 'Earnings', active: false },
    { icon: 'schedule', label: 'Schedule', active: false },
    { icon: 'analytics', label: 'Reports', active: false },
    { icon: 'settings', label: 'Settings', active: false }
  ];
  
  // Generate months and years for dropdowns - Only 2025 Jan-Sep
  months = [
    { value: 1, name: 'January' },
    { value: 2, name: 'February' },
    { value: 3, name: 'March' },
    { value: 4, name: 'April' },
    { value: 5, name: 'May' },
    { value: 6, name: 'June' },
    { value: 7, name: 'July' },
    { value: 8, name: 'August' },
    { value: 9, name: 'September' }
  ];
  
  years: number[] = [2025]; // Only 2025

  studentColumns: string[] = [
    'name', 'grade', 'country', 'contactNumber', 
    'registrationId', 'totalSessions', 'totalEarnings'
  ];
  
  sessionColumns: string[] = [
    'studentName', 'sessionDateTime', 'durationMinutes', 'expectedEarnings'
  ];

  constructor(
    private authService: AuthService,
    private earningsService: EarningsService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.loadDashboardData();
  }

  loadDashboardData(): void {
    this.isLoading = true;
    
    // Load earnings summary with optional student and month/year filter
    this.earningsService.getEarningsSummary(
      this.selectedStudentId || undefined,
      this.selectedMonth || undefined,
      this.selectedYear || undefined
    ).subscribe(summary => {
      this.earningsSummary = summary;
    });

    // Load monthly breakdown
    this.earningsService.getMonthlyBreakdown(this.selectedStudentId || undefined)
      .subscribe(breakdown => {
        this.monthlyBreakdown = breakdown;
      });

    // Always load all students for dropdown, but filter display data
    this.earningsService.getStudentEarnings().subscribe(students => {
      this.allStudents = students; // Keep all for dropdown
      
      // Filter displayed data based on selected student
      if (this.selectedStudentId) {
        this.studentEarnings = students.filter(student => student.studentId === this.selectedStudentId);
      } else {
        this.studentEarnings = students;
      }
      
      this.calculateStatistics();
    });

    // Load upcoming sessions with optional student filter
    this.earningsService.getUpcomingSessions(this.selectedStudentId || undefined)
      .subscribe(sessions => {
        this.upcomingSessions = sessions;
        this.calculateUpcomingEarnings();
        this.isLoading = false;
      });
  }
  
  calculateStatistics(): void {
    this.totalStudents = this.studentEarnings.length;
    this.totalSessions = this.studentEarnings.reduce((sum, student) => sum + student.totalSessions, 0);
  }
  
  calculateUpcomingEarnings(): void {
    this.upcomingEarnings = this.upcomingSessions.reduce((sum, session) => sum + session.expectedEarnings, 0);
  }

  onStudentFilterChange(): void {
    this.loadDashboardData();
  }
  
  onMonthYearFilterChange(): void {
    // Auto-set year to 2025 when month is selected
    if (this.selectedMonth && !this.selectedYear) {
      this.selectedYear = 2025;
    }
    
    // Clear year if month is cleared
    if (!this.selectedMonth) {
      this.selectedYear = null;
    }
    
    this.loadDashboardData();
  }
  
  clearMonthYearFilter(): void {
    this.selectedMonth = null;
    this.selectedYear = null;
    this.loadDashboardData();
  }
  
  getFilterDisplayText(): string {
    if (this.selectedMonth && this.selectedYear) {
      const monthName = this.months.find(m => m.value === this.selectedMonth)?.name;
      return `${monthName} ${this.selectedYear}`;
    }
    return 'Current Period';
  }
  
  dataValidationPassed(): boolean {
    if (!this.earningsSummary) return true;
    const ytdFromSummary = this.earningsSummary.yearToDate;
    const ytdFromMonthly = this.getTotalFromMonthlyBreakdown();
    const difference = Math.abs(ytdFromSummary - ytdFromMonthly);
    return difference < 0.01; // Allow for small rounding differences
  }
  
  getMonthlyBreakdownArray(): {name: string, earnings: number, isCurrent: boolean}[] {
    const months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August'];
    const currentMonth = 'August'; // Current month is August 2025
    
    return months.map(month => ({
      name: month,
      earnings: this.monthlyBreakdown[month] || 0,
      isCurrent: month === currentMonth
    }));
  }
  
  getTotalFromMonthlyBreakdown(): number {
    return Object.values(this.monthlyBreakdown).reduce((total, amount) => total + amount, 0);
  }
  
  getSelectedStudentName(): string {
    if (!this.selectedStudentId) return '';
    const student = this.allStudents.find(s => s.studentId === this.selectedStudentId);
    return student ? `${student.firstName} ${student.lastName}` : '';
  }
  
  getTopPerformingStudent(): StudentEarnings | null {
    if (this.studentEarnings.length === 0) return null;
    return this.studentEarnings.reduce((top, student) => 
      student.totalEarnings > top.totalEarnings ? student : top
    );
  }
  
  getAverageEarningsPerStudent(): number {
    if (this.studentEarnings.length === 0) return 0;
    const total = this.studentEarnings.reduce((sum, student) => sum + student.totalEarnings, 0);
    return total / this.studentEarnings.length;
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD'
    }).format(amount);
  }

  formatDateTime(dateTime: string): string {
    return new Date(dateTime).toLocaleString();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
  
  toggleSidenav(): void {
    this.sidenavOpened = !this.sidenavOpened;
  }
  
  selectMenuItem(selectedItem: any): void {
    // Reset all items to inactive
    this.menuItems.forEach(item => item.active = false);
    // Set selected item as active
    selectedItem.active = true;
  }
}
