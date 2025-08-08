export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  username: string;
  firstName: string;
  lastName: string;
  subject: string;
}

export interface EarningsSummary {
  yearToDate: number;
  monthly: number;
  weekly: number;
  period: string;
}

export interface StudentEarnings {
  studentId: number;
  firstName: string;
  lastName: string;
  grade: number;
  subject: string;
  country: string;
  contactNumber: string;
  registrationId: string;
  totalEarnings: number;
  totalSessions: number;
}

export interface UpcomingSession {
  sessionId: number;
  studentId: number;
  studentName: string;
  sessionDateTime: string;
  durationMinutes: number;
  expectedEarnings: number;
  subject: string;
}

export interface Teacher {
  id: number;
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  subject: string;
}
