
    create table admin (
        row_version integer not null,
        id uuid not null,
        user_id uuid not null unique,
        primary key (id)
    );

    create table app_state (
        current_year integer not null,
        id integer not null,
        row_version integer not null,
        update_time timestamp(6) with time zone not null,
        enrollment_stage varchar(255) not null check (enrollment_stage in ('COURSES','GROUPS','CORRECTION','CLOSED')),
        term varchar(255) not null check (term in ('AUTUMN','SPRING','SUMMER')),
        primary key (id)
    );

    create table building (
        latitude numeric(10,8) not null,
        longitude numeric(11,8) not null,
        row_version integer not null,
        id uuid not null,
        name varchar(100) not null unique,
        address varchar(200) not null unique,
        primary key (id)
    );

    create table course (
        course_year integer not null,
        max_students integer,
        num_enrolled integer not null,
        row_version integer not null,
        id uuid not null,
        subject_id uuid not null,
        primary key (id),
        unique (subject_id, course_year)
    );

    create table course_class (
        class_number integer not null check ((class_number>=1) and (class_number<=7)),
        row_version integer not null,
        building_id uuid,
        group_id uuid not null,
        id uuid not null,
        teacher_id uuid,
        location varchar(100),
        title varchar(100) not null,
        weeks_list varchar(100) not null,
        type varchar(255) not null check (type in ('PRACTICE','SEMINAR','LECTURE','CONSULTATION','CREDIT','EXAM')),
        week_day varchar(255) not null check (week_day in ('MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','SUNDAY')),
        primary key (id)
    );

    create table course_group (
        group_number integer not null check (group_number>=1),
        max_students integer not null,
        num_enrolled integer not null,
        row_version integer not null,
        course_id uuid not null,
        id uuid not null,
        primary key (id),
        unique (course_id, group_number)
    );

    create table department (
        row_version integer not null,
        faculty_id uuid not null,
        id uuid not null,
        name varchar(100) not null,
        description varchar(1000),
        primary key (id),
        unique (faculty_id, name)
    );

    create table enrollment_request (
        row_version integer not null,
        created_at timestamp(6) with time zone not null,
        course_id uuid not null,
        id uuid not null,
        student_id uuid not null,
        reason varchar(3000) not null,
        status varchar(255) not null check (status in ('PENDING','ACCEPTED','DECLINED')),
        primary key (id)
    );

    create table faculty (
        row_version integer not null,
        id uuid not null,
        name varchar(100) not null unique,
        description varchar(1000),
        primary key (id)
    );

    create table speciality (
        row_version integer not null,
        department_id uuid,
        id uuid not null,
        name varchar(100) not null unique,
        description varchar(1000),
        primary key (id)
    );

    create table student (
        row_version integer not null,
        study_year integer not null,
        id uuid not null,
        specialty_id uuid not null,
        user_id uuid not null unique,
        record_book_number varchar(50) not null,
        status varchar(255) not null check (status in ('STUDYING','EXPELLED','GRADUATED')),
        primary key (id)
    );

    create table student_enrollment (
        enrollment_year integer not null,
        row_version integer not null,
        created_at timestamp(6) with time zone not null,
        course_id uuid not null,
        group_id uuid,
        id uuid not null,
        student_id uuid not null,
        status varchar(255) not null check (status in ('ENROLLED','FORCE_ENROLLED','WITHDRAWN')),
        primary key (id),
        unique (student_id, course_id)
    );

    create table subject (
        credits numeric(3,1) not null,
        hours_per_week integer not null,
        row_version integer not null,
        id uuid not null,
        department_name varchar(500),
        faculty_name varchar(500),
        annotation varchar(5000),
        education_level varchar(255) not null check (education_level in ('BATCHELOR','MASTER')),
        name varchar(255) not null,
        term varchar(255) not null check (term in ('AUTUMN','SPRING','SUMMER')),
        primary key (id)
    );

    create table subject_recommendation (
        row_version integer not null,
        id uuid not null,
        specialty_id uuid not null,
        subject_id uuid not null,
        recommendation varchar(255) not null check (recommendation in ('MANDATORY','PROF_ORIENTED')),
        primary key (id)
    );

    create table teacher (
        row_version integer not null,
        id uuid not null,
        first_name varchar(100) not null,
        last_name varchar(100) not null,
        patronymic varchar(100) not null,
        email varchar(200) not null unique,
        primary key (id)
    );

    create table term_info (
        end_date date not null,
        len_weeks integer not null,
        start_date date not null,
        study_year integer not null,
        id uuid not null,
        term varchar(255) not null check (term in ('AUTUMN','SPRING','SUMMER')),
        primary key (id)
    );

    create table user_account (
        row_version integer not null,
        id uuid not null,
        password_hash varchar(60) not null,
        first_name varchar(100) not null,
        last_name varchar(100) not null,
        patronymic varchar(100) not null,
        email varchar(200) not null unique,
        role varchar(255) not null check (role in ('STUDENT','ADMIN')),
        primary key (id)
    );

    create table withdrawal_request (
        row_version integer not null,
        created_at timestamp(6) with time zone not null,
        id uuid not null,
        student_enrollment_id uuid not null,
        reason varchar(3000) not null,
        status varchar(255) not null check (status in ('PENDING','ACCEPTED','DECLINED')),
        primary key (id)
    );

    alter table if exists admin 
       add constraint FKc79oc19295ce127ywo23nb734 
       foreign key (user_id) 
       references user_account;

    alter table if exists course 
       add constraint FKm1expnaas0onmafqpktmjixnx 
       foreign key (subject_id) 
       references subject;

    alter table if exists course_class 
       add constraint FK8l5ff7rsvwc220jib51yij8b5 
       foreign key (building_id) 
       references building;

    alter table if exists course_class 
       add constraint FKnt38lrmcvily99cgkjumvsaio 
       foreign key (group_id) 
       references course_group;

    alter table if exists course_class 
       add constraint FK24kqhbnk17thtdrmtvi8qarb3 
       foreign key (teacher_id) 
       references teacher;

    alter table if exists course_group 
       add constraint FKkt5730n2360qbi88t2wkdpsyp 
       foreign key (course_id) 
       references course;

    alter table if exists department 
       add constraint FKj2xhv1clx0m0axk2y53wm4hgl 
       foreign key (faculty_id) 
       references faculty;

    alter table if exists enrollment_request 
       add constraint FK8gom33b87aflhh5es8851k9qe 
       foreign key (course_id) 
       references course;

    alter table if exists enrollment_request 
       add constraint FKabbu2g1u4hbp70xs5tcxgrp5o 
       foreign key (student_id) 
       references student;

    alter table if exists speciality 
       add constraint FK2eyownqehfoodivhopcdg9q63 
       foreign key (department_id) 
       references department;

    alter table if exists student 
       add constraint FK1tetb4xcek4ktfcniio9yew0y 
       foreign key (specialty_id) 
       references speciality;

    alter table if exists student 
       add constraint FKall7qatgcsiy2fgkm0hrt2v9j 
       foreign key (user_id) 
       references user_account;

    alter table if exists student_enrollment 
       add constraint FKf3itoiah71qp5qfx8eq2agemq 
       foreign key (course_id) 
       references course;

    alter table if exists student_enrollment 
       add constraint FK4osougixjsvo56e17h2ux1gk9 
       foreign key (group_id) 
       references course_group;

    alter table if exists student_enrollment 
       add constraint FKqhd4hrst2uv5ouhwdaq53656o 
       foreign key (student_id) 
       references student;

    alter table if exists subject_recommendation 
       add constraint FKtorv7rspyo48buu5jww3sf2i6 
       foreign key (specialty_id) 
       references speciality;

    alter table if exists subject_recommendation 
       add constraint FKdv6n0ngibj45p7b62h95cf2q2 
       foreign key (subject_id) 
       references subject;

    alter table if exists withdrawal_request 
       add constraint FKqd60du8spccklt7lv1v23c5d3 
       foreign key (student_enrollment_id) 
       references student_enrollment;
