CREATE TABLE test.agency (
     id bigserial NOT NULL,
     name varchar(50) NOT NULL,
     created_at timestamp(0) NOT NULL DEFAULT timezone('CDT'::text, now()),
     CONSTRAINT agency_pk PRIMARY KEY (id)
);

CREATE TABLE test.profession (
     id bigserial NOT NULL,
     name varchar(50) NOT NULL,
     created_at timestamp(0) NOT NULL DEFAULT timezone('CDT'::text, now()),
     CONSTRAINT profession_pk PRIMARY KEY (id)
);

CREATE TABLE test.ethnicity (
    id bigserial NOT NULL,
    name varchar(50) NOT NULL,
    created_at timestamp(0) NOT NULL DEFAULT timezone('CDT'::text, now()),
    CONSTRAINT ethnicity_pk PRIMARY KEY (id)
);

CREATE TABLE test.gender (
                             id bigserial NOT NULL,
                             name varchar(50) NOT NULL,
                             created_at timestamp(0) NOT NULL DEFAULT timezone('CDT'::text, now()),
                             CONSTRAINT gender_pk PRIMARY KEY (id)
);