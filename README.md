# Earnings Dashboard for Teachers - 2025 Edition

A comprehensive web application that allows teachers to log in and view detailed, personalized earnings reports for the 2025 academic year. Built with Angular frontend, Spring Boot backend, and H2 database with enhanced UI and comprehensive analytics.

## Features

### Authentication
- Secure login system with JWT tokens
- 10 pre-configured teacher accounts
- Session management with automatic logout

### Enhanced Earnings Dashboard
- **2025 Data Focus**: Complete data coverage from January to September 2025
- **Monthly Breakdown Chart**: Visual representation of earnings across all months
- **Multiple Earnings Views**: Year-to-Date (YTD), Monthly, and Weekly earnings
- **Monthly Filter**: Filter earnings by specific month (Jan-Sep 2025)
- **Student Filtering**: View earnings for all students or filter by individual student
- **Detailed Student Information**: Name, grade, subject, country, contact number, and registration ID
- **Grade-based Hourly Rates**: Different rates based on student grade levels
- **Upcoming Sessions**: View September 2025 sessions with date/time (UTC), duration, and expected earnings

### Advanced Analytics & Insights
- **Performance Statistics**: Total students, completed sessions, average session duration
- **Upcoming Earnings Tracker**: Real-time calculation of potential September earnings
- **Top Performing Student**: Identify highest-earning student relationships
- **Average Performance Metrics**: Per-student earnings and session averages
- **Monthly Comparison**: Visual chart comparing earnings across all 2025 months
- **Responsive Design**: Optimized layout that utilizes screen space effectively

### Data Management
- **2025 Academic Year Focus**: January-August completed sessions, September upcoming sessions
- **Realistic Session Distribution**: 3-7 sessions per month per student
- **Grade-based Hourly Rates**: $10-$18/hr based on student grade levels (5th-12th grade)
- **Comprehensive Student Profiles**: Each teacher manages 4-6 students across different countries

## Technology Stack

### Backend
- **Spring Boot 3.2.0** - REST API framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database operations
- **H2 Database** - In-memory database for development
- **JWT** - Token-based authentication
- **Maven** - Dependency management

### Frontend
- **Angular 18** - Frontend framework
- **Angular Material** - UI component library
- **TypeScript** - Type-safe JavaScript
- **RxJS** - Reactive programming
- **CSS3** - Styling and responsive design

## Project Structure

```
earning dashboard/
├── backend/                 # Spring Boot backend
│   ├── src/main/java/
│   │   └── com/earnings/dashboard/
│   │       ├── config/      # Configuration classes
│   │       ├── controller/  # REST controllers
│   │       ├── dto/         # Data Transfer Objects
│   │       ├── entity/      # JPA entities
│   │       ├── repository/  # Data repositories
│   │       ├── security/    # Security configuration
│   │       └── service/     # Business logic
│   └── src/main/resources/
│       └── application.yml  # Application configuration
├── frontend/                # Angular frontend
│   ├── src/app/
│   │   ├── components/      # Angular components
│   │   ├── guards/          # Route guards
│   │   ├── interceptors/    # HTTP interceptors
│   │   ├── models/          # TypeScript interfaces
│   │   └── services/        # Angular services
│   └── src/styles.css       # Global styles
└── README.md
```

## Getting Started

### Prerequisites
- Java 17 or higher
- Node.js 18 or higher
- npm or yarn
- Maven 3.6 or higher

### Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. Install dependencies and run the application:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. The backend will start on `http://localhost:8080`

4. Access H2 Console (optional): `http://localhost:8080/api/h2-console`
   - JDBC URL: `jdbc:h2:mem:earnings_db`
   - Username: `sa`
   - Password: `password`

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm start
   ```

4. The frontend will start on `http://localhost:4200`

## Demo Credentials

The application comes with 10 pre-configured teacher accounts:

| Username | Password | Subject | 
|----------|----------|---------|
| teacher1 | password1 | Mathematics |
| teacher2 | password2 | Science |
| teacher3 | password3 | English |
| teacher4 | password4 | History |
| teacher5 | password5 | Physics |
| teacher6 | password6 | Chemistry |
| teacher7 | password7 | Biology |
| teacher8 | password8 | Geography |
| teacher9 | password9 | Art |
| teacher10 | password10 | Music |

## API Endpoints

### Authentication
- `POST /api/auth/login` - Teacher login

### Earnings
- `GET /api/earnings/summary` - Get earnings summary (YTD, monthly, weekly)
- `GET /api/earnings/students` - Get student earnings details
- `GET /api/earnings/upcoming-sessions` - Get upcoming sessions

### Query Parameters
- `studentId` (optional) - Filter results by specific student
- `month` (optional) - Filter results by specific month (1-12)
- `year` (optional) - Filter results by specific year (both month and year required for monthly filtering)

## Grade-based Hourly Rates

| Grade Level | Hourly Rate |
|-------------|-------------|
| 5th-6th Grade | $10.00 |
| 7th-8th Grade | $12.00 |
| 9th-10th Grade | $15.00 |
| 11th-12th Grade | $18.00 |

## Features in Detail

### Dashboard Overview
- Clean, responsive design with Material Design components
- Real-time earnings calculations
- Intuitive navigation and filtering options
- **Monthly Filter**: Select specific month/year combinations to view historical earnings data
- **Dynamic Summary Cards**: When a specific month is selected, the summary shows only that month's data

### Filtering Options
- **Student Filter**: View earnings for all students or filter by individual student
- **Monthly Filter**: Select any month/year combination from 2020 onwards
- **Combined Filters**: Use both student and monthly filters together for precise data analysis
- **Clear Filters**: Easy reset functionality to return to current period view

### Security
- JWT-based authentication
- Protected routes with Angular guards
- Secure password storage with BCrypt
- CORS configuration for cross-origin requests

### Data Visualization
- Tabular display of student information and earnings
- Upcoming sessions with UTC timestamps
- Currency formatting for all monetary values
- Responsive design for mobile and desktop

## Development

### Running Tests
```bash
# Backend tests
cd backend
mvn test

# Frontend tests
cd frontend
npm test
```

### Building for Production
```bash
# Backend
cd backend
mvn clean package

# Frontend
cd frontend
npm run build
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License.

## Support

For questions or issues, please create an issue in the repository or contact the development team.
