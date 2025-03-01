---
menu: gradle
subtitle: Gradle - First Steps
redirect_from: /Getting Started/First Steps/First Steps - Gradle/
---
<div id="firstStepsGradle">
    <h1>First Steps: Gradle</h1>

    <h2>Prerequisites</h2>
    <ul>
        <li>Java 8, 9, 10, 11 or 12</li>
        <li>Gradle 3.0 or newer</li>
    </ul>

    <h2>Setting up the build file</h2>
    <p>Let's start by creating <code>build.gradle</code> that integrates and configures Flyway to connect to H2:</p>

    <pre class="prettyprint">buildscript {
    dependencies {
        classpath 'com.h2database:h2:1.4.197'
    }
}

plugins {
    id "org.flywaydb.flyway" version "{{ site.flywayVersion }}"
}

flyway {
    url = 'jdbc:h2:file:./target/foobar'
    user = 'sa'
}
</pre>

    <h2>Creating the first migration</h2>

    <p>We create a first migration called <code>src/main/resources/db/migration/V1__Create_person_table.sql</code>:</p>
    <pre class="prettyprint">create table PERSON (
    ID int not null,
    NAME varchar(100) not null
);</pre>

    <h2>Migrating the database</h2>

    <p>It's now time to execute Flyway to migrate our database:</p>
    <pre class="console"><span>&gt;</span> gradle flywayMigrate -i</pre>

    <p>If all went well, you should see the following output:</p>
    <pre class="console">Creating schema history table: "PUBLIC"."flyway_schema_history"
Current version of schema "PUBLIC": &lt;&lt; Empty Schema &gt;&gt;
Migrating schema "PUBLIC" to version 1 - Create person table
Successfully applied 1 migration to schema "PUBLIC" (execution time 00:00.062s).</pre>

    <h2>Adding a second migration</h2>

    <p>If we now add a second migration called <code>src/main/resources/db/migration/V2__Add_people.sql</code>:</p>
    <pre class="prettyprint">insert into PERSON (ID, NAME) values (1, 'Axel');
insert into PERSON (ID, NAME) values (2, 'Mr. Foo');
insert into PERSON (ID, NAME) values (3, 'Ms. Bar');</pre>

    <p>and execute it by issuing:</p>
    <pre class="console"><span>&gt;</span> gradle flywayMigrate -i</pre>

    <p>We now get:</p>
    <pre class="console">Current version of schema "PUBLIC": 1
Migrating schema "PUBLIC" to version 2 - Add people
Successfully applied 1 migration to schema "PUBLIC" (execution time 00:00.090s).</pre>

    <h2>Summary</h2>

    <p>In this brief tutorial we saw how to</p>
    <ul>
        <li>integrate the Flyway Gradle plugin into a project</li>
        <li>configure it so it can talk to our database</li>
        <li>write our first couple of migrations</li>
    </ul>
    <p>These migrations were then successfully found and executed.</p>
</div>
