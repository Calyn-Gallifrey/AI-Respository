# Full-stack Business Card Demo

## Database setup
1. Ensure MySQL is running locally with user `root`/`root`.
2. Create schema and seed data:
   ```bash
   mysql -uroot -proot < db/schema.sql
   mysql -uroot -proot < db/seed.sql
   ```

## Backend (Spring Boot + MyBatis)
1. Navigate to `backend` and install dependencies/build:
   ```bash
   cd backend
   mvn clean package
   ```
2. Start the server (port `8080`):
   ```bash
   mvn spring-boot:run
   ```

APIs:
- `GET /api/user/me`
- `GET /api/cards`
- `GET /api/cards/{cardId}/settings`
- `PUT /api/cards/{cardId}/settings`

## Frontend (React Native with Expo)
1. Install dependencies:
   ```bash
   cd frontend
   npm install
   ```
2. Start in iOS Simulator:
   ```bash
   npm run ios
   ```
   Ensure the backend is running at `http://localhost:8080`.

The app includes four pages reflecting the provided prototypes:
- Page 1: Dashboard (avatar navigates to profile; top stats loaded from backend)
- Page 2: Personal detail ("My Business Card" opens management page)
- Page 3: Business Card Management (Standard/Elite cards, Edit navigates to settings)
- Page 4: Link Card settings (switches map to backend configuration; Save persists and returns to management)
