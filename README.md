# Selenium Assignment 2026

**Student:** Emanuel Nzinga Maimona  
**Neptun:** GAU5OE
**Website under test:** https://app.incharge.education

For the full assignment description, task list, and deadlines, see the course materials.

Fill in `points.yml` to track your progress. The CI will calculate your grade on every push.

## Running the Tests

### With Docker (recommended)

```bash
docker-compose up --build
```

This starts a Selenium Grid (Chrome) container and a test runner container. Test results land in `build/reports/tests/`.

### Locally

Ensure a Selenium Grid is running at `http://selenium:4444/wd/hub` (or update `selenium.grid.url` in `src/test/resources/test.properties`), then:

```bash
./gradlew test
```

## Project Structure

```
src/test/
├── java/
│   ├── base/           # BasePage, BaseTest
│   ├── config/         # TestConfig (reads test.properties)
│   ├── listeners/      # ScreenshotListener (screenshot on failure)
│   ├── pages/          # Page Object classes
│   └── tests/          # Test classes
└── resources/
    └── test.properties # Configuration (URL, credentials, grid, headless)
```
