## Course

Link to the course: https://www.udemy.com/course/hibernate-and-spring-data-jpa-beginner-to-guru

## How to run the app:

1. Install mysql workbench.
2. Run the `SdjpaIntroApplication` class in order to start the app. Run it with `local,clean` Spring profiles.
3. About maven, there are 3 maven profiles, `h2,liquibase,flyway` `h2` should be used always, as we use this in
   memory db always. Whole course is based on `flyway` migration scripts as this is the choice of the author.
   `liquibase` is just for educational purposes, to show second db migration tool.
