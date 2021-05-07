CREATE DATABASE covidstat
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default


CREATE TABLE public.city
(
    city_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
    city_name character varying(80) NOT NULL,
    population integer NOT NULL,
    confirmed integer NOT NULL,
    deaths integer NOT NULL,
    CONSTRAINT city_pkey PRIMARY KEY (city_id)
)

CREATE TABLE public.country
(
    country_id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    country_name character varying COLLATE  NOT NULL,
    population integer NOT NULL,
    confirmed integer NOT NULL,
    deaths integer NOT NULL,
    city integer,
    CONSTRAINT country_pkey PRIMARY KEY (country_id),
    CONSTRAINT fk_city_country FOREIGN KEY (city)
        REFERENCES public.city (city_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
)